package org.cx4a.rsense;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

import org.jruby.Ruby;
import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.HereDocReader;

public class Main {
    private InputStream in;
    private PrintStream out;
    private Reader inReader;
    private Project sandbox;
    private Config config;
    private CodeAssist codeAssist;
    
    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    public void run(String[] args) throws Exception {
        in = System.in;
        out = System.out;
        inReader = new InputStreamReader(in);

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
        if (options.isDebug()) {
            Logger.getInstance().setLevel(Logger.Level.DEBUG);
        }
        if (options.getLog() != null) {
            PrintStream log = new PrintStream(new FileOutputStream(options.getLog(), true));
            try {
                Logger.getInstance().setOut(log);
                start(command, options);
            } finally {
                log.close();
            }
        } else {
            start(command, options);
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

    private void start(String command, Options options) {
        if (command.equals("script")) {
            script(options);
        } else {
            command(command, options);
        }
    }

    private void usage() {
        out.println("RSense: Ruby development tools\n"
                    + "\n"
                    + "Usage: java -jar rsense.jar org.cx4a.rsense.Main command option...\n"
                    + "\n"
                    + "command:\n"
                    + "  script                 - Run rsense script from file or stdin.\n"
                    + "      --prompt=          - Prompt string in interactive shell mode.\n"
                    + "\n"
                    + "  code-completion        - Code completion at specified position.\n"
                    + "      --file=            - File to analyze\n"
                    + "      --location=        - Location where you want to complete (pos, line:col, str)\n"
                    + "\n"
                    + "  infer-type             - Infer type at specified position.\n"
                    + "      --file=            - File to analyze\n"
                    + "      --location=        - Location where you want to complete (pos, line:col, str)\n"
                    + "\n"
                    + "  help                   - Print this help.\n"
                    + "\n"
                    + "  version                - Print version information.\n"
                    + "\n"
                    + "common-options:\n"
                    + "  --debug                - Print debug messages\n"
                    + "  --log=                 - Log file to output (default stderr)\n"
                    + "  --format=              - Output format (plain, emacs)\n"
                    + "  --encoding=            - Input encoding\n"
            );
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
            } else {
                options.addRestArg(arg);
            }
        }
        return options;
    }

    private void script(Options options) {
        if (options.getRestArgs().isEmpty()) {
            runScript(in, options, false);
        } else {
            try {
                for (String file : options.getRestArgs()) {
                    InputStream in = new FileInputStream(file);
                    try {
                        runScript(in, options, true);
                    } finally {
                        in.close();
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void runScript(InputStream in, Options options, boolean noprompt) {
        String format = options.getFormat();
        String encoding = options.getEncoding();
        String prompt = options.getPrompt();
        if (format == null) {
            format = options.defaultFormat();
        }
        if (encoding == null) {
            encoding = options.defaultEncoding();
        }
        if (prompt == null) {
            prompt = options.defaultPrompt();
        }

        Reader oldInReader = inReader;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, encoding));
            inReader = reader;
            String line;
            String endMark = options.getEndMark();
            if (endMark != null && endMark.length() == 0) {
                endMark = Options.defaultEndMark();
            }
            while (true) {
                if (!noprompt) {
                    out.print(prompt);
                }
                line = reader.readLine();
                if (line == null) {
                    break;
                } else if (line.matches("^\\s*#.*")) {
                    // comment
                    continue;
                }
                
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
        } finally {
            this.inReader = oldInReader;
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
            commandUnknown(command, options);
        }
    }

    private void commandCodeCompletion(Options options) {
        try {
            CodeCompletionResult result;
            if (options.isFileStdin()) {
                result = codeAssist.codeCompletion(sandbox,
                                                   options.getHereDocReader(inReader),
                                                   options.getLocation());
            } else {
                result = codeAssist.codeCompletion(sandbox,
                                                   options.getFile(),
                                                   options.getEncoding(),
                                                   options.getLocation());
            }
            if (options.isTest()) {
                List<String> shouldContain = options.getShouldContain();
                Set<String> data = new HashSet<String>();
                for (CodeCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                    data.add(completion.getCompletion());
                }
                for (String name : shouldContain) {
                    if (data.contains(name)) {
                        out.printf("SUCCESS: %s\n", options.getTest());
                    } else {
                        out.printf("FAILURE: %s (%s is not in %s)\n", options.getTest(), name, data);
                    }
                }
            } else {
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
            }
        } catch (Exception e) {
            commandException(e, options);
        }
    }

    private void commandTypeInference(Options options) {
        try {
            TypeInferenceResult result;
            if (options.isFileStdin()) {
                result = codeAssist.typeInference(sandbox,
                                                  options.getHereDocReader(inReader),
                                                  options.getLocation());
            } else {
                result = codeAssist.typeInference(sandbox,
                                                  options.getFile(),
                                                  options.getEncoding(),
                                                  options.getLocation());
            }
            if (options.isTest()) {
                List<String> shouldContain = options.getShouldContain();
                Set<String> data = new HashSet<String>();
                for (IRubyObject klass : result.getTypeSet()) {
                    data.add(klass.toString());
                }
                for (String name : shouldContain) {
                    if (data.contains(name)) {
                        out.printf("SUCCESS: %s\n", options.getTest());
                    } else {
                        out.printf("FAILURE: %s (%s is not in %s)\n", options.getTest(), name, data);
                    }
                }
            } else {
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

    private void commandUnknown(String command, Options options) {
        if (options.isEmacsFormat()) {
            out.printf("((error . \"Unknown command: %s\"))\n", command);
        } else {
            out.printf("Unknown command: %s\n", command);
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
