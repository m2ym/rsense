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
import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

import org.jruby.ast.Node;
import org.jruby.ast.NodeType;
import org.jruby.ast.types.INameNode;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.MetaClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.CallVertex;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.SpecialMethod;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.CodeCompletionResult.CompletionCandidate;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.NodeDiff;
import org.cx4a.rsense.util.SourceLocation;

public class CodeAssist {
    public static final String TYPE_INFERENCE_METHOD_NAME = "__rsense_type_inference__";
    public static final String FIND_DEFINITION_METHOD_NAME_PREFIX = "__rsense_find_definition__";
    public static final String PROJECT_CONFIG_NAME = ".project.rsense";

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
                    if (Character.isHighSurrogate(c) || c == '\r') {
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
        public boolean main;
        public String feature;
        public int loadPathLevel;

        public void clear() {
            this.project = null;
            this.typeSet = null;
            this.main = false;
            this.feature = null;
            this.loadPathLevel = 0;
        }
    }

    private static class NodeDiffForTypeInference extends NodeDiff {
        @Override
        protected boolean isSameNode(Node a, Node b) {
            if (!super.isSameNode(a, b)) {
                return false;
            }

            if (a.getNodeType() == NodeType.CALLNODE) {
                String name = ((INameNode) a).getName();
                if (name.equals(TYPE_INFERENCE_METHOD_NAME)
                    || name.startsWith(FIND_DEFINITION_METHOD_NAME_PREFIX)) {
                    // scratch!
                    return false;
                }
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

    private class WhereEventListener implements Project.EventListener {
        private int line;
        private int closest;
        private String name;

        public void prepare(int line) {
            this.line = line;
        }

        public String getName() {
            return name;
        }

        public void update(Event event) {
            if (context.main
                && (event.type == EventType.DEFINE
                    || event.type == EventType.CLASS
                    || event.type == EventType.MODULE)
                && event.name != null
                && event.node != null) {
                SourceLocation loc = SourceLocation.of(event.node);
                if (loc != null
                    && line >= loc.getLine()
                    && line - closest > line - loc.getLine()) {
                    closest = loc.getLine();
                    name = event.name;
                }
            }
        }
    }

    private class FindDefinitionEventListener implements Project.EventListener {
        private int counter = 0;
        private String prefix;
        private Set<SourceLocation> locations = new HashSet<SourceLocation>();

        public String setup() {
            locations.clear();

            // Make unique prefix to distinguish older find-definition command results.
            return prefix = FIND_DEFINITION_METHOD_NAME_PREFIX + counter++;
        }

        public Collection<SourceLocation> getLocations() {
            return locations;
        }

        public void update(Event event) {
            CallVertex vertex;
            if (context.main
                && event.type == EventType.METHOD_MISSING
                && (vertex = (CallVertex) event.vertex) != null
                && vertex.getName().startsWith(prefix)
                && vertex.getReceiverVertex() != null) {
                String realName = vertex.getName().substring(prefix.length());
                for (IRubyObject receiver : vertex.getReceiverVertex().getTypeSet()) {
                    RubyClass receiverType = receiver.getMetaClass();
                    if (receiverType != null) {
                        SourceLocation location = null;

                        // Try to find method
                        // TODO callSuper
                        Method method = (Method) receiverType.searchMethod(realName);
                        if (method != null) {
                            if (method.getLocation() != null)
                                locations.add(method.getLocation());
                        } else {
                            // Try to find constant
                            RubyModule klass = null;
                            if (receiverType instanceof MetaClass) {
                                MetaClass metaClass = (MetaClass) receiverType;
                                if (metaClass.getAttached() instanceof RubyModule)
                                    klass = (RubyModule) metaClass.getAttached();
                            } else
                                klass = context.project.getGraph().getRuntime().getContext().getCurrentScope().getModule();
                            if (klass != null) {
                                IRubyObject constant = klass.getConstant(realName);
                                if (constant instanceof VertexHolder)
                                    location = SourceLocation.of(((VertexHolder) constant).getVertex().getNode());
                                else if (constant instanceof RubyModule)
                                    location = ((RubyModule) constant).getLocation();
                            }
                        }

                        if (location != null)
                            locations.add(location);
                    }
                }
                vertex.cutout();
            }
        }
    }

    private org.jruby.Ruby rubyRuntime;
    private final Options options;
    private final Context context;
    private Map<String, Project> projects;
    private Project sandbox;
    private FindDefinitionEventListener definitionFinder;
    private WhereEventListener whereListener;

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
        this.context = new Context();
        this.options = options;
        clear();
    }

    public void openProject(String path, Options options) {
        File dir = new File(path);
        if (dir.isDirectory()) {
            openProject(newProject(dir, options));
        } else {
            Logger.message("failed to open project: %s", path);
        }
    }

    public void openProject(Project project) {
        projects.put(project.getName(), project);
        Logger.message("project opened: %s", project.getName());
    }

    public void closeProject(String name) {
        Project project = projects.remove(name);
        if (project != null) {
            Logger.message("project closed: %s", project.getName());
        }
    }

    public Map<String, Project> getProjects() {
        return projects;
    }

    public Project getProject(Options options) {
        Project project = projects.get(options.getProject());
        if (project == null && options.isDetectProject()) {
            File file = options.getDetectProject();
            if (file == null && !options.isFileStdin()) {
                file = options.getFile();
            }
            if (file != null) {
                File parent = file;
                while ((parent = parent.getParentFile()) != null) {
                    for (String[] list : new String[][] {new String[] {PROJECT_CONFIG_NAME},
                                                         new String[] {"Rakefile.rb"},
                                                         new String[] {"Rakefile"},
                                                         new String[] {"setup.rb"},
                                                         new String[] {"Makefile", "lib"}}) {
                        boolean found = true;
                        for (String name : list) {
                            if (!new File(parent, name).exists()) {
                                found = false;
                                break;
                            }
                        }
                        if (found) {
                            project = getProjectByPath(parent);
                            if (project == null) {
                                project = newProject(parent, options);
                            }
                            return project;
                        }
                    }
                }
            }
        }

        return project != null ? project : sandbox;
    }

    public Project getProjectByPath(File path) {
        for (Project project : projects.values()) {
            if (project.getPath().equals(path)) {
                return project;
            }
        }
        return null;
    }

    private Project newProject(File path, Options options) {
        File config = new File(path, PROJECT_CONFIG_NAME);
        if (config.isFile()) {
            options.loadConfig(config);
        } else {
            // Guess config
            options.addOption("load-path", "lib");
        }

        String name = options.getName();
        if (name == null) {
            name = path.getName();
        }
        Project project = new Project(name, path);
        project.setLoadPath(options.getLoadPath());
        project.setGemPath(options.getGemPath());
        openProject(project);

        return project;
    }

    public LoadResult load(Project project, File file, String encoding) {
        return load(project, file, encoding, true);
    }
    
    private LoadResult load(Project project, File file, String encoding, boolean prepare) {
        if (project.isLoaded(file.getPath())) {
            return LoadResult.alreadyLoaded();
        }
        project.setLoaded(file.getPath());
        
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
        boolean oldMain = context.main;
        try {
            if (prepare)
                prepare(project);
            else
                context.main = false;
            Node ast = parseFileContents(file, readAll(reader));
            project.getGraph().load(ast);

            LoadResult result = new LoadResult();
            result.setAST(ast);
            return result;
        } catch (IOException e) {
            return LoadResult.failWithException("Cannot load file", e);
        } finally {
            context.main = oldMain;
        }
    }

    public LoadResult require(Project project, String feature, String encoding) {
        return require(project, feature, encoding, 0);
    }

    private LoadResult require(Project project, String feature, String encoding, int loadPathLevel) {
        if (project.isLoaded(feature)) {
            return LoadResult.alreadyLoaded();
        }
        project.setLoaded(feature);
        Logger.info("feature required: %s", feature);

        if (File.pathSeparator.equals(";")) { // Windows?
            feature = feature.replace('/', '\\');
        }
        
        List<File> loadPath = project.getLoadPath();
        int loadPathLen = loadPath.size();
        for (int i = loadPathLevel; i < loadPathLen; i++) {
            File pathElement = loadPath.get(i);
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

        List<File> gemPath = project.getGemPath();
        int gemPathLen = gemPath.size();
        String sep = File.separator;
        for (int i = 0; i < gemPathLen; i++) {
            File pathElement = gemPath.get(i);
            int oldLoadPathLevel = context.loadPathLevel;
            context.loadPathLevel = i + loadPathLen;
            String oldFeature = context.feature;
            context.feature = feature;
            try {
                File gemsDir = new File(pathElement, "gems");
                String[] gems = gemsDir.list();
                if (gems != null) {
                    for (String gem : gems) {
                        File file = new File(gemsDir + sep + gem + sep + "lib" + sep + feature + ".rb");
                        if (file.exists()) {
                            return load(project, file, encoding, false);
                        }
                    }
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
            Node ast = parseFileContents(file, readAndInjectCode(reader, loc, TYPE_INFERENCE_METHOD_NAME, "(?:\\.|::)", "."));
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
            Node ast = parseFileContents(file, readAndInjectCode(reader, loc, TYPE_INFERENCE_METHOD_NAME, "(?:\\.|::)", "."));
            project.getGraph().load(ast);

            CodeCompletionResult result = new CodeCompletionResult();
            result.setAST(ast);

            List<CompletionCandidate> candidates = new ArrayList<CompletionCandidate>();
            for (IRubyObject receiver : context.typeSet) {
                RubyClass rubyClass = receiver.getMetaClass();
                for (String name : rubyClass.getMethods(true)) {
                    DynamicMethod method = rubyClass.searchMethod(name);
                    candidates.add(new CompletionCandidate(name,
                                                           method.toString(),
                                                           method.getModule().getMethodPath(null),
                                                           CompletionCandidate.Kind.METHOD));
                }
                if (receiver instanceof RubyModule) {
                    RubyModule module = ((RubyModule) receiver);
                    for (String name : module.getConstants(true)) {
                        RubyModule directModule = module.getConstantModule(name);
                        IRubyObject constant = directModule.getConstant(name);
                        String baseName = directModule.toString();
                        String qname = baseName + "::" + name;
                        CompletionCandidate.Kind kind
                            = (constant instanceof RubyClass)
                            ? CompletionCandidate.Kind.CLASS
                            : (constant instanceof RubyModule)
                            ? CompletionCandidate.Kind.MODULE
                            : CompletionCandidate.Kind.CONSTANT;
                        candidates.add(new CompletionCandidate(name, qname, baseName, kind));
                    }
                }
            }

            result.setCandidates(candidates);
            return result;
        } catch (IOException e) {
            return CodeCompletionResult.failWithException("Cannot read file", e);
        }
    }

    public WhereResult where(Project project, File file, String encoding, int line) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return where(project, file, new InputStreamReader(in, encoding), line);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return WhereResult.failWithException("Cannot open file", e);
        } 
    }

    public WhereResult where(Project project, File file, Reader reader, int line) {
        try {
            prepare(project);
            Node ast = parseFileContents(file, readAll(reader));

            whereListener.prepare(line);
            project.addEventListener(whereListener);
            try {
                project.getGraph().load(ast);
            } finally {
                project.removeEventListener(whereListener);
            }

            WhereResult result = new WhereResult();
            result.setAST(ast);
            result.setName(whereListener.getName());
            
            return result;
        } catch (IOException e) {
            return WhereResult.failWithException("Cannot read file", e);
        }
    }

    public FindDefinitionResult findDefinition(Project project, File file, String encoding, Location loc) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return findDefinition(project, file, new InputStreamReader(in, encoding), loc);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return FindDefinitionResult.failWithException("Cannot open file", e);
        } 
    }

    public FindDefinitionResult findDefinition(Project project, File file, Reader reader, Location loc) {
        try {
            prepare(project);
            Node ast = parseFileContents(file, readAndInjectCode(reader, loc, definitionFinder.setup(), "(?:\\.|::|\\s)(\\w+?[!?]?)", null));

            project.addEventListener(definitionFinder);
            try {
                project.getGraph().load(ast);
            } finally {
                project.removeEventListener(definitionFinder);
            }

            FindDefinitionResult result = new FindDefinitionResult();
            result.setAST(ast);

            result.setLocations(definitionFinder.getLocations());

            return result;
        } catch (IOException e) {
            return FindDefinitionResult.failWithException("Cannot read file", e);
        }
    }

    public void clear() {
        this.rubyRuntime = org.jruby.Ruby.newInstance(); // for parse
        this.context.clear();
        this.projects = new HashMap<String, Project>();
        this.sandbox = new Project("(sandbox)", new File("."));
        this.sandbox.setLoadPath(options.getLoadPath());
        this.sandbox.setGemPath(options.getGemPath());
        this.definitionFinder = new FindDefinitionEventListener();
        this.whereListener = new WhereEventListener();
        openProject(this.sandbox);
    }

    private void prepare(Project project) {
        context.project = project;
        context.typeSet = new TypeSet();
        context.main = true;

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

    private String readAndInjectCode(Reader _reader, Location loc, String injection, String prefixPattern, String defaultPrefix) throws IOException {
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

                            int pstart = Math.max(0, index - 128);
                            String pbuf = new String(buf, pstart, index - pstart);
                            Matcher matcher = Pattern.compile(".*" + prefixPattern, Pattern.DOTALL).matcher(pbuf);
                            boolean match = matcher.matches();
                            if (match) {
                                if (matcher.groupCount() > 0) {
                                    int end = index - (pbuf.length() - matcher.start(1));
                                    buffer.append(buf, 0, end);
                                    buffer.append(injection);
                                    buffer.append(buf, end, index - end);
                                } else {
                                    buffer.append(buf, 0, index);
                                    buffer.append(injection);
                                }
                            } else {
                                buffer.append(buf, 0, index);
                                if (defaultPrefix != null)
                                    buffer.append(defaultPrefix);
                                buffer.append(injection);
                            }

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
