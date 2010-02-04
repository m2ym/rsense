package org.cx4a.rsense;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.jruby.Ruby;
import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.util.Logger;

public class Main {
    private static class Options extends HashMap<String, String> {
        public boolean isFormatGiven() {
            return containsKey("format");
        }

        public boolean isEncodingGiven() {
            return containsKey("encoding");
        }

        public String getFormat() {
            return get("format");
        }

        public void setFormat(String format) {
            put("format", format);
        }

        public boolean isPlainFormat() {
            return "plain".equals(getFormat());
        }

        public boolean isEmacsFormat() {
            return "emacs".equals(getFormat());
        }

        public String getEncoding() {
            return get("encoding");
        }

        public void setEncoding(String encoding) {
            put("encoding", encoding);
        }

        public File getFile() {
            String file = get("file");
            return file == null ? null : new File(file);
        }

        public Integer getOffset() {
            String offset = get("offset");
            return offset == null ? null : Integer.valueOf(offset);
        }

        public String getEndMark() {
            return get("end-mark");
        }

        public static String defaultFormat() {
            return "plain";
        }

        public static String defaultEncoding() {
            return "UTF-8";
        }

        public static String defaultEndMark() {
            return "END";
        }
    }

    private InputStream in;
    private PrintStream out;
    private Project sandbox;
    private Config config;
    private CodeAssist codeAssist;
    
    public static void main(String[] args) throws Exception {
        //Logger.setDebug(true);
        new Main().run(args);
    }

    public void run(String[] args) {
        in = System.in;
        out = System.out;

        if (args.length == 0 || args[0].equals("help")) {
            usage();
            return;
        } else if (args[0].equals("version")) {
            version();
            return;
        }

        init();

        String command = args[0];
        Options options = parseOptions(args, 1);
        if (command.equals("interactive")) {
            interactive(options);
        } else {
            command(command, options);
        }
    }

    private void init() {
        String rsenseHome = System.getProperty("rsense.home");
        if (rsenseHome == null) {
            rsenseHome = ".";
        }

        sandbox = new Project("<sandbox>", null);
        config = new Config(rsenseHome);
        codeAssist = new CodeAssist(config);
        codeAssist.load(sandbox, new File(config.rsenseHome + "/stubs/1.8/builtin.rb"), "UTF-8");
    }

    private void usage() {
        out.println("RSense: Ruby development tools\n"
                    + "\n"
                    + "Usage: java -jar rsense.jar org.cx4a.rsense.Main command option...\n"
                    + "\n"
                    + "command:\n"
                    + "  interactive            - Start interactive mode.\n"
                    + "      --format=          - Output format (plain, emacs)\n"
                    + "      --encoding=        - Input encoding\n"
                    + "\n"
                    + "  code-completion        - Code completion at specified position.\n"
                    + "      --file=            - File to analyze\n"
                    + "      --encoding=        - File encoding\n"
                    + "      --offset=          - Offset where you want to complete\n"
                    + "\n"
                    + "  infer-type             - Infer type at specified position.\n"
                    + "      --file=            - File to analyze\n"
                    + "      --encoding=        - File encoding\n"
                    + "      --offset=          - Offset where you want to complete\n"
                    + "\n"
                    + "  help                   - Print this help."
                    + "\n"
                    + "  version                - Print version information.");
    }

    private void version() {
        out.println(versionString());
    }

    private String versionString() {
        return "RSense 0.0.1";
    }

    private Options parseOptions(String[] args, int offset) {
        Options options = new Options();
        for (int i = offset; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                String[] lr = arg.substring(2).split("=");
                if (lr.length >= 1) {
                    options.put(lr[0], lr.length >= 2 ? lr[1] : "");
                }
            }
        }
        return options;
    }

    private void interactive(Options options) {
        String format = options.getFormat();
        String encoding = options.getEncoding();
        if (format == null) {
            format = options.defaultFormat();
        }
        if (encoding == null) {
            encoding = options.defaultEncoding();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
            String line;
            String endMark = options.getEndMark();
            if (endMark != null && endMark.length() == 0) {
                endMark = Options.defaultEndMark();
            }
            while ((line = reader.readLine()) != null) {
                String[] argv = line.split(" ");
                if (argv.length > 0) {
                    String command = argv[0];
                    if (command.equals("exit") || command.equals("quit")) {
                        return;
                    }

                    Options opts = parseOptions(argv, 1);
                    if (!opts.isFormatGiven() && format != null) {
                        opts.setFormat(format);
                    }
                    if (!opts.isEncodingGiven() && encoding != null) {
                        opts.setEncoding(encoding);
                    }
                    command(command, opts);
                    if (endMark != null) {
                        out.println(endMark);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private void command(String command, Options options) {
        if (command.equals("code-completion")) {
            commandCodeCompletion(options);
        } else if (command.equals("type-inference")) {
            commandTypeInference(options);
        } else if (command.equals("help")) {
            commandHelp(options);
        } else if (command.equals("version")) {
            commandVersion(options);
        } else if (command.length() == 0) {
        } else {
            commandUnknown(options);
        }
    }

    private void commandCodeCompletion(Options options) {
        try {
            CodeCompletionResult result = codeAssist.codeCompletion(sandbox,
                                                                    options.getFile(),
                                                                    options.getEncoding(),
                                                                    options.getOffset());
            if (options.isEmacsFormat()) {
                out.print("(");
                out.print("(completion");
                for (CodeCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                    out.print(" \"");
                    out.print(completion);
                    out.print("\"");
                }
                out.println(")");
                codeAssistError(result, options);
                out.println(")");
            } else {
                for (CodeCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                    out.print("completion: ");
                    out.println(completion);
                }
                codeAssistError(result, options);
            }
        } catch (Exception e) {
            commandException(e, options);
        }
    }

    private void commandTypeInference(Options options) {
        try {
            TypeInferenceResult result = codeAssist.typeInference(sandbox,
                                                                  options.getFile(),
                                                                  options.getEncoding(),
                                                                  options.getOffset());
            if (options.isEmacsFormat()) {
                out.print("(");
                out.print("(type");
                for (IRubyObject klass : result.getTypeSet()) {
                    out.print(" \"");
                    out.print(klass);
                    out.print("\"");
                }
                out.println(")");
                codeAssistError(result, options);
                out.println(")");
            } else {
                for (IRubyObject klass : result.getTypeSet()) {
                    out.print("type: ");
                    out.println(klass);
                }
                codeAssistError(result, options);
            }
        } catch (Exception e) {
            commandException(e, options);
        }
    }

    private void commandHelp(Options options) {
        if (options.isEmacsFormat()) {
            out.print("\"");
        }
        usage();
        if (options.isEmacsFormat()) {
            out.println("\"");
        }
    }

    private void commandVersion(Options options) {
        if (options.isEmacsFormat()) {
            out.print("\"");
        }
        version();
        if (options.isEmacsFormat()) {
            out.println("\"");
        }
    }

    private void commandUnknown(Options options) {
        if (options.isEmacsFormat()) {
            out.println("((error . \"Unknown command\"))");
        } else {
            out.println("Unknown command");
        }
    }

    private void commandException(Exception e, Options options) {
        if (options.isEmacsFormat()) {
            out.println("((error . \"Unexpected error\"))");
        } else {
            out.println("Unexpected error:");
            e.printStackTrace(out);
        }
    }

    private void codeAssistError(CodeAssistResult result, Options options) {
        // FIXME
    }
}
