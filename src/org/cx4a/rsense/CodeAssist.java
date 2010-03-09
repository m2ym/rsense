package org.cx4a.rsense;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

import org.jruby.ast.Node;
import org.jruby.ast.NodeType;
import org.jruby.ast.types.INameNode;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.runtime.SpecialMethod;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.NodeDiff;

public class CodeAssist {
    public static final String TYPE_INFERENCE_METHOD_NAME = "__rsense_type_inference__";

    public static class Location {
        // 3 ways to specify location

        private int offset;
        private int line, col;
        private char[] mark;

        private Location(int offset, int line, int col, String mark) {
            this.offset = offset;
            this.line = line;
            this.col = col;
            this.mark = mark != null ? mark.toCharArray() : null;
        }

        public int getOffset() {
            return offset;
        }

        public int getLine() {
            return line;
        }

        public int getColumn() {
            return col;
        }

        public char[] getMark() {
            return mark;
        }

        public int findOffset(int offset, int line, char[] buf, int length) {
            if (this.offset != -1) {
                return this.offset;
            } else if (this.line != -1) {
                int col = 0;
                int count = 0;
                for (int i = 0; i < length; i++) {
                    char c = buf[i];
                    if (Character.isHighSurrogate(c)) {
                    } else if (c == '\n') {
                        line++;
                        col = 0;
                        count++;
                    } else {
                        col++;
                        count++;
                        if (line == this.line && col == this.col) {
                            return offset + count;
                        }
                    }
                }
                return -1;
            } else if (this.mark != null) {
                int count = 0;
                for (int i = 0; i <= length - mark.length; i++) {
                    int j = 0;
                    if (Character.isHighSurrogate(buf[i]) || buf[i] == '\r') {
                    } else {
                        count++;
                    }
                    for (; j < mark.length && buf[i + j] == mark[j]; j++);
                    if (j == mark.length) {
                        return offset + count - 1;
                    }
                }
                return -1;
            }
            return -1;
        }

        public int getSkip() {
            return mark != null ? mark.length : 0;
        }

        public static Location offsetLocation(int offset) {
            return new Location(offset, -1, -1, null);
        }

        public static Location logicalLocation(int line, int col) {
            return new Location(-1, line, col, null);
        }

        public static Location markLocation(String mark) {
            return new Location(-1, -1, -1, mark);
        }
    }

    private static class Context {
        public Project project;
        public TypeSet typeSet;
        public String feature;
        public int loadPathLevel = 0;
    }

    private static class NodeDiffForTypeInference extends NodeDiff {
        @Override
        protected boolean isSameNode(Node a, Node b) {
            if (!super.isSameNode(a, b)) {
                return false;
            }

            if (a.getNodeType() == NodeType.CALLNODE
                && ((INameNode) a).getName().equals(TYPE_INFERENCE_METHOD_NAME)) {
                // scratch!
                return false;
            }
            return true;
        }

        @Override
        protected Node getNextNode(Iterator<Node> ite, boolean newNode) {
            while (ite.hasNext()) {
                Node node = ite.next();
                if (isStatementNode(node)) {
                    return node;
                }
                if (newNode) {
                    diff.add(node);
                }
            }
            return null;
        }
    }

    private org.jruby.Ruby rubyRuntime;
    private final Options options;
    private final Context context;
    private Map<File, Project> projects;
    private Project sandbox;

    private SpecialMethod typeInferenceMethod = new SpecialMethod() {
            public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                for (IRubyObject receiver : receivers) {
                    context.typeSet.add(receiver);
                }
                result.setResultTypeSet(receivers);
                result.setNeverCallAgain(true); // cutout vertex
            }
        };

    private SpecialMethod requireMethod = new SpecialMethod() {
            public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                if (args != null && args.length > 0) {
                    String feature = Vertex.getString(args[0]);
                    if (feature != null) {
                        require(context.project, feature, "UTF-8");
                    }
                }
            }
        };

    private SpecialMethod requireNextMethod = new SpecialMethod() {
            public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                if (context.feature != null) {
                    require(context.project, context.feature, "UTF-8", context.loadPathLevel + 1);
                }
            }
        };

    public CodeAssist(Options options) {
        rubyRuntime = org.jruby.Ruby.newInstance(); // for parse
        this.context = new Context();
        this.options = options;
        clear();
    }

    public Project getProject(Options options) {
        Project project = projects.get(options.getProject());
        if (!options.isFileStdin()) {
            File file = options.getFile();
            if (project == null && options.isDetectProject()) {
                File parent;
                while ((parent = file.getParentFile()) != null) {
                    File config = new File(parent, ".rsense");
                    if (config.exists()) {
                        // found config file
                        project = newProjectFromConfig(config, options);
                        break;
                    }
                }
            }
        }

        return project != null ? project : sandbox;
    }

    private Project newProjectFromConfig(File config, Options options) {
        File path = config.getParentFile();
        Project project = new Project(path.getName(), path);
        project.setLoadPath(options.getLoadPath());
        return project;
    }

    public LoadResult load(Project project, File file, String encoding) {
        return load(project, file, encoding, true);
    }
    
    private LoadResult load(Project project, File file, String encoding, boolean prepare) {
        if (project.isLoaded(file)) {
            return LoadResult.alreadyLoaded();
        }
        project.setLoaded(file);
        
        try {
            InputStream in = new FileInputStream(file);
            try {
                return load(project, file, new InputStreamReader(in, encoding), prepare);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return LoadResult.failWithException("Cannot open file", e);
        } 
    }

    public LoadResult load(Project project, File file, Reader reader) {
        return load(project, file, reader, true);
    }
    
    private LoadResult load(Project project, File file, Reader reader, boolean prepare) {
        try {
            if (prepare) {
                prepare(project);
            }
            Node ast = parseFileContents(file, readAll(reader));
            project.getGraph().load(ast);

            LoadResult result = new LoadResult();
            result.setAST(ast);
            return result;
        } catch (IOException e) {
            return LoadResult.failWithException("Cannot load file", e);
        }
    }

    public LoadResult require(Project project, String feature, String encoding) {
        return require(project, feature, encoding, 0);
    }

    private LoadResult require(Project project, String feature, String encoding, int loadPathLevel) {
        List<String> loadPath = project.getLoadPath();
        for (int i = loadPathLevel; i < loadPath.size(); i++) {
            String pathElement = loadPath.get(i);
            int oldLoadPathLevel = context.loadPathLevel;
            context.loadPathLevel = i;
            String oldFeature = context.feature;
            context.feature = feature;
            try {
                File file = new File(pathElement, feature + ".rb");
                if (file.exists()) {
                    return load(project, file, encoding, false);
                }
            } finally {
                context.feature = oldFeature;
                context.loadPathLevel = oldLoadPathLevel;
            }
        }
        Logger.warn("cannot require: %s", feature);
        return LoadResult.failWithNotFound();
    }

    public TypeInferenceResult typeInference(Project project, File file, String encoding, Location loc) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return typeInference(project, file, new InputStreamReader(in, encoding), loc);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return TypeInferenceResult.failWithException("Cannot open file", e);
        } 
    }

    public TypeInferenceResult typeInference(Project project, File file, Reader reader, Location loc) {
        try {
            prepare(project);
            Node ast = parseFileContents(file, readAndInjectCode(reader, loc, TYPE_INFERENCE_METHOD_NAME, new String[] {".", "::"}, "."));
            project.getGraph().load(ast);

            TypeInferenceResult result = new TypeInferenceResult();
            result.setAST(ast);
            TypeSet ts = new TypeSet();
            for (IRubyObject receiver : context.typeSet) {
                ts.add(receiver.getMetaClass());
            }
            result.setTypeSet(ts);
            return result;
        } catch (IOException e) {
            return TypeInferenceResult.failWithException("Cannot read file", e);
        }
    }

    public CodeCompletionResult codeCompletion(Project project, File file, String encoding, Location loc) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return codeCompletion(project, file, new InputStreamReader(in, encoding), loc);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return CodeCompletionResult.failWithException("Cannot open file", e);
        } 
    }

    public CodeCompletionResult codeCompletion(Project project, File file, Reader reader, Location loc) {
        try {
            prepare(project);
            Node ast = parseFileContents(file, readAndInjectCode(reader, loc, TYPE_INFERENCE_METHOD_NAME, new String[] {".", "::"}, "."));
            project.getGraph().load(ast);

            CodeCompletionResult result = new CodeCompletionResult();
            result.setAST(ast);

            List<CodeCompletionResult.CompletionCandidate> candidates = new ArrayList<CodeCompletionResult.CompletionCandidate>();
            for (IRubyObject receiver : context.typeSet) {
                RubyClass rubyClass = receiver.getMetaClass();
                for (String name : rubyClass.getMethods(true)) {
                    DynamicMethod method = rubyClass.searchMethod(name);
                    candidates.add(new CodeCompletionResult.CompletionCandidate(name, method.toString()));
                }
                if (receiver instanceof RubyModule) {
                    RubyModule module = ((RubyModule) receiver);
                    for (String name : module.getConstants(true)) {
                        String qname = module.getConstantModule(name).toString() + "::" + name;
                        candidates.add(new CodeCompletionResult.CompletionCandidate(name, qname));
                    }
                }
            }

            result.setCandidates(candidates);
            return result;
        } catch (IOException e) {
            return CodeCompletionResult.failWithException("Cannot read file", e);
        }
    }

    public void clear() {
        this.projects = new HashMap<File, Project>();
        this.sandbox = new Project("(sandbox)", null);
        this.sandbox.setLoadPath(options.getLoadPath());
    }

    private void prepare(Project project) {
        context.project = project;
        context.typeSet = new TypeSet();

        Graph graph = project.getGraph();
        graph.addSpecialMethod(TYPE_INFERENCE_METHOD_NAME, typeInferenceMethod);
        graph.addSpecialMethod("require", requireMethod);
        graph.addSpecialMethod("require_next", requireNextMethod);
        graph.setNodeDiff(new NodeDiffForTypeInference());

        require(project, "_builtin", "UTF-8");
    }

    private String readAll(Reader reader) throws IOException {
        return readAndInjectCode(reader, null, null, null, null);
    }

    private String readAndInjectCode(Reader _reader, Location loc, String injection, String[] prefixes, String defaultPrefix) throws IOException {
        LineNumberReader reader = new LineNumberReader(_reader);
        int line = reader.getLineNumber() + 1;
        int offset = -1;
        char[] buf = new char[4096];
        int read;
        int len = 0;
        StringBuilder buffer = new StringBuilder();
        while ((read = reader.read(buf)) != -1) {
            int index = 0;
            if (loc != null) {
                if (offset == -1) {
                    offset = loc.findOffset(len, line, buf, read);
                }
                for (int i = 0; i < read; i++) {
                    if (Character.isHighSurrogate(buf[i]) || buf[i] == '\r') {
                    } else {
                        len++;
                        if (len == offset) {
                            index = i + 1;
                            buffer.append(buf, 0, index);
                            boolean found = false;
                            for (String p : prefixes) {
                                if (buffer.substring(Math.max(0, buffer.length() - p.length()), buffer.length()).equals(p)) {
                                    found = true;
                                    break;
                                }
                            }
                            if (!found) {
                                buffer.append(defaultPrefix);
                            }
                            buffer.append(injection);
                            index += loc.getSkip();
                            break;
                        }
                    }
                }
            }
            buffer.append(buf, index, read - index);
        }
        return buffer.toString();
    }

    public Node parseFileContents(File file, String string) {
        ByteArrayInputStream in = new ByteArrayInputStream(string.getBytes());
        return rubyRuntime.parseFromMain(in, file.getPath());
    }
}
