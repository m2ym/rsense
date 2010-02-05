package org.cx4a.rsense;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.runtime.SpecialMethod;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.NodeDiff;

public class CodeAssist {
    public static final String TYPE_INFERENCE_METHOD_NAME = "__rsense_type_inference__";

    private static class Context {
        public Project project;
        public TypeSet typeSet;
    }

    private static class NodeDiffForTypeInference extends NodeDiff {
    }

    private org.jruby.Ruby rubyRuntime;
    private final Config config;
    private final Context context;

    private SpecialMethod typeInferenceMethod = new SpecialMethod() {
            public void call(Ruby runtime, TypeSet receivers, Vertex[] args, Block blcck, Result result) {
                for (IRubyObject receiver : receivers) {
                    context.typeSet.add(receiver.getMetaClass());
                    //context.typeSet.add(receiver);
                }
                result.setResultTypeSet(receivers);
            }
        };

    public CodeAssist(Config config) {
        rubyRuntime = org.jruby.Ruby.newInstance(); // for parse
        this.config = config;
        context = new Context();
    }

    public LoadResult load(Project project, File file, String encoding) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return load(project, new InputStreamReader(in, encoding));
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return LoadResult.failWithException("Cannot open file", e);
        } 
    }

    public LoadResult load(Project project, Reader reader) {
        try {
            prepare(project);
            Node ast = parseString(readAll(reader));
            project.getGraph().load(ast);

            LoadResult result = new LoadResult();
            result.setAST(ast);
            return result;
        } catch (IOException e) {
            return LoadResult.failWithException("Cannot load file", e);
        }
    }

    public TypeInferenceResult typeInference(Project project, File file, String encoding, long offset) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return typeInference(project, new InputStreamReader(in, encoding), offset);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return TypeInferenceResult.failWithException("Cannot open file", e);
        } 
    }

    public TypeInferenceResult typeInference(Project project, Reader reader, long offset) {
        try {
            prepare(project);
            Node ast = parseString(readAndInjectCode(reader, offset, TYPE_INFERENCE_METHOD_NAME, "."));
            project.getGraph().load(ast);

            TypeInferenceResult result = new TypeInferenceResult();
            result.setTypeSet(context.typeSet);
            return result;
        } catch (IOException e) {
            return TypeInferenceResult.failWithException("Cannot read file", e);
        }
    }

    public CodeCompletionResult codeCompletion(Project project, File file, String encoding, long offset) {
        try {
            InputStream in = new FileInputStream(file);
            try {
                return codeCompletion(project, new InputStreamReader(in, encoding), offset);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            return CodeCompletionResult.failWithException("Cannot open file", e);
        } 
    }

    public CodeCompletionResult codeCompletion(Project project, Reader reader, long offset) {
        CodeCompletionResult result = new CodeCompletionResult();
        TypeInferenceResult r = typeInference(project, reader, offset);
        if (r.hasError()) {
            result.setErrors(r.getErrors());
            return result;
        }

        List<CodeCompletionResult.CompletionCandidate> candidates = new ArrayList<CodeCompletionResult.CompletionCandidate>();
        for (IRubyObject klass : r.getTypeSet()) {
            for (String name : ((RubyClass) klass).getMethods(true)) {
                candidates.add(new CodeCompletionResult.CompletionCandidate(name));
            }
        }

        result.setCandidates(candidates);
        return result;
    }

    private void prepare(Project project) {
        context.project = project;
        context.typeSet = new TypeSet();

        Graph graph = project.getGraph();
        graph.addSpecialMethod(TYPE_INFERENCE_METHOD_NAME, typeInferenceMethod);
        graph.setNodeDiff(new NodeDiffForTypeInference());
    }

    private String readAll(Reader reader) throws IOException {
        return readAndInjectCode(reader, -1, null, null);
    }

    private String readAndInjectCode(Reader reader, long offset, String injection, String prefix) throws IOException {
        char[] buf = new char[4096];
        int read;
        int len = 0;
        StringBuilder buffer = new StringBuilder();
        while ((read = reader.read(buf)) != -1) {
            int index = 0;
            if (offset >= 0 && len <= offset) {
                for (int i = 0; i < read; i++) {
                    if (Character.isHighSurrogate(buf[i])) {
                    } else {
                        len++;
                        if (len == offset) {
                            index = i + 1;
                            buffer.append(buf, 0, index);
                            if (!buffer.substring(Math.max(0, buffer.length() - prefix.length()), buffer.length()).equals(prefix)) {
                                buffer.append(prefix);
                            }
                            buffer.append(injection);
                            break;
                        }
                    }
                }
            }
            buffer.append(buf, index, read - index);
        }
        return buffer.toString();
    }

    public Node parseString(String string) {
        ByteArrayInputStream in = new ByteArrayInputStream(string.getBytes());
        return rubyRuntime.parseFromMain(in, "<codeassist>");
    }
}
