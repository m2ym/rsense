package org.cx4a.rsense;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;
import java.util.Properties;

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
import org.cx4a.rsense.util.StringUtil;
import org.cx4a.rsense.util.HereDocReader;
import org.cx4a.rsense.util.SourceLocation;

public class Main {
    private static class TestStats {
        public int count = 0;
        public int success = 0;
        public int failure = 0;
        public int error = 0;
    }

    private static class ProgressMonitor extends Thread implements Project.EventListener {
        private boolean stop;
        private PrintStream out;
        private int interval;
        private Project.EventListener.Event event;

        public ProgressMonitor(PrintStream out, int interval) {
            this.out = out;
            this.interval = interval;
        }

        public void run() {
            if (interval > 0) {
                while (isAlive()) {
                    print();
                    try {
                        Thread.sleep(interval);
                    } catch (InterruptedException e) {}
                }
            }
        }

        public void attach(Project project) {
            event = null;
            if (interval >= 0) {
                project.addEventListener(this);
                if (!isAlive())
                    start();
            }
        }

        public void detach(Project project) {
            event = null;
            if (interval >= 0)
                project.removeEventListener(this);
        }

        public void update(Project.EventListener.Event event) {
            this.event = event;
            if (interval == 0)
                print();
        }

        private void print() {
            if (event != null) {
                switch (event.type) {
                case DEFINE:
                    out.printf("progress: defining method %s...\n", event.name);
                    break;
                case CLASS:
                    out.printf("progress: defining class %s...\n", event.name);
                    break;
                case MODULE:
                    out.printf("progress: defining module %s...\n", event.name);
                    break;
                }
            }
        }
    }

    private Properties properties;
    private File currentDir;
    private InputStream in;
    private PrintStream out;
    private Reader inReader;
    private CodeAssist codeAssist;
    private TestStats testStats;
    private ProgressMonitor progressMonitor;
    
    public static void main(String[] args) throws Exception {
        new Main().run(args);
    }

    public void run(String[] args) throws Exception {
        in = System.in;
        out = System.out;
        inReader = new InputStreamReader(in);

        properties = new Properties();
        properties.load(this.getClass().getResourceAsStream("rsense.properties"));

        if (args.length == 0 || args[0].equals("help")) {
            usage();
            return;
        } else if (args[0].equals("version")) {
            version();
            return;
        }

        String command = args[0];
        Options options = parseOptions(args, 1);

        Logger.getInstance().setLevel(options.getLogLevel());
        init(options);
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
        testResult(options);
    }

    private void init(Options options) {
        codeAssist = new CodeAssist(options);

        Integer interval = options.getProgress();
        progressMonitor = new ProgressMonitor(out, interval != null ? interval * 1000 : -1);
        progressMonitor.setDaemon(true);
    }

    private void start(String command, Options options) {
        command(command, options);
    }

    private void usage() {
        out.print("RSense: Ruby development tools\n"
                  + "\n"
                  + "Usage: java -jar rsense.jar org.cx4a.rsense.Main command option...\n"
                  + "\n"
                  + "command:\n"
                  + "  code-completion        - Code completion at specified position.\n"
                  + "      --file=            - File to analyze\n"
                  + "      --location=        - Location where you want to complete (pos, line:col, str)\n"
                  + "      --prefix=          - Specify prefix string to complete\n"
                  + "\n"
                  + "  type-inference         - Infer type at specified position.\n"
                  + "      --file=            - File to analyze\n"
                  + "      --location=        - Location where you want to infer (pos, line:col, str)\n"
                  + "\n"
                  + "  find-definition        - Infer type at specified position.\n"
                  + "      --file=            - File to analyze\n"
                  + "      --location=        - Location where you want to find (pos, line:col, str)\n"
                  + "\n"
                  + "  where                  - Print which class/module/method cursor at.\n"
                  + "      --file=            - File to analyze\n"
                  + "      --line=            - Line number to find\n"
                  + "\n"
                  + "  load                   - Load file without any outputs.\n"
                  + "      --file=            - File to analyze\n"
                  + "\n"
                  + "  script                 - Run rsense script from file or stdin.\n"
                  + "      --prompt=          - Prompt string in interactive shell mode\n"
                  + "      --no-prompt        - Do not show prompt\n"
                  + "\n"
                  + "  clear                  - Clear current environment.\n"
                  + "\n"
                  + "  gc                     - Execute garbage collection.\n"
                  + "\n"
                  + "  list-project           - List loaded projects.\n"
                  + "\n"
                  + "  open-project <dir>     - Open project in <dir>.\n"
                  + "\n"
                  + "  close-project <name>   - Close project named <name>.\n"
                  + "\n"
                  + "  environment            - Print environment.\n"
                  + "\n"
                  + "  help                   - Print this help.\n"
                  + "\n"
                  + "  version                - Print version information.\n"
                  + "\n"
                  + "script-command:\n"
                  + "  exit\n"
                  + "  quit                   - Exit script.\n"
                  + "\n"
                  + "common-options:\n"
                  + "  --home=                - Specify RSense home directory\n"
                  + "  --debug                - Print debug messages (shorthand of --log-evel=debug)\n"
                  + "  --log=                 - Log file to output (default stderr)\n"
                  + "  --log-level=           - Log level (fixme, error, warn, message, info, debug)\n"
                  + "  --progress             - Report progress immediately\n"
                  + "  --progress=            - Report progress per seconds\n\n"
                  + "  --format=              - Output format (plain, emacs)\n"
                  + "  --verbose              - Verbose output\n"
                  + "  --time                 - Print timing of each command\n"
                  + "  --encoding=            - Input encoding\n"
                  + "  --load-path=           - Load path string (: or ; separated)\n"
                  + "  --gem-path=            - Gem path string (: or ; separated)\n"
                  + "  --config=              - Config file\n"
                  + "  --project=             - Specify project name\n"
                  + "  --detect-project       - Detect project from --file option\n"
                  + "  --detect-project=      - Detect project from specified location\n"
                  + "\n"
                  + "test-options:\n"
                  + "  --test=                - Specify fixture name\n"
                  + "  --test-color           - Print test result with colors\n"
                  + "  --should-contain=      - Success if data contains expected data\n"
                  + "  --should-not-contain=  - Success if data doesn't contains expected data\n"
                  + "  --should-be=           - Success if data equals to expected data\n"
                  + "  --should-be-empty      - Success if data is empty\n"
                  + "\n"
                  + "debug-options:\n"
                  + "  --print-ast            - Print parsed AST\n"
            );
    }

    private void version() {
        out.println(versionString());
    }

    private String versionString() {
        return "RSense " + properties.getProperty("rsense.version");
    }

    private Options parseOptions(String[] args, int offset) {
        Options options = new Options();
        for (int i = offset; i < args.length; i++) {
            String arg = args[i];
            if (arg.startsWith("--")) {
                String[] lr = arg.substring(2).split("=");
                if (lr.length >= 1) {
                    options.addOption(lr[0], lr.length >= 2 ? lr[1] : null);
                }
            } else {
                options.addRestArg(arg);
            }
        }

        String config = options.getConfig();
        if (config != null) {
            File configFile = new File(config);
            if (configFile.isFile()) {
                options.loadConfig(configFile);
            }
        }

        return options;
    }

    private void script(Options options) {
        if (options.getRestArgs().isEmpty()) {
            runScript(in, options, false);
        } else {
            try {
                for (String filename : options.getRestArgs()) {
                    File file;
                    if (currentDir == null || !(file = new File(currentDir, filename)).exists()) {
                        // Load from current directory if possible
                        file = new File(filename);
                    }

                    File oldCurrentDir = currentDir;
                    currentDir = file.getParentFile();
                    InputStream in = new FileInputStream(file);
                    try {
                        runScript(in, options, true);
                    } finally {
                        in.close();
                        currentDir = oldCurrentDir;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void runScript(InputStream in, Options options, boolean noprompt) {
        String prompt = options.getPrompt();
        if (prompt == null) {
            prompt = options.defaultPrompt();
        }

        Reader oldInReader = inReader;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, options.getEncoding()));
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
                
                String[] argv = StringUtil.shellwords(line);
                if (argv.length > 0) {
                    String command = argv[0];
                    if (command.equals("exit") || command.equals("quit")) {
                        return;
                    }

                    Options opts = parseOptions(argv, 1);
                    opts.inherit(options);
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
        long start = System.currentTimeMillis();
        Logger.info("command: %s", command);
        if (options.isTest() && !options.isKeepEnv()) {
            codeAssist.clear();
        }
        if (command.equals("code-completion")) {
            commandCodeCompletion(options);
        } else if (command.equals("type-inference")) {
            commandTypeInference(options);
        } else if (command.equals("find-definition")) {
            commandFindDefinition(options);
        } else if (command.equals("where")) {
            commandWhere(options);
        } else if (command.equals("load")) {
            commandLoad(options);
        } else if (command.equals("script")) {
            script(options);
        } else if (command.equals("clear")) {
            commandClear(options);
        } else if (command.equals("gc")) {
            commandGC(options);
        } else if (command.equals("list-project")) {
            commandListProject(options);
        } else if (command.equals("open-project")) {
            commandOpenProject(options);
        } else if (command.equals("close-project")) {
            commandCloseProject(options);
        } else if (command.equals("environment")) {
            commandEnvironment(options);
        } else if (command.equals("help")) {
            commandHelp(options);
        } else if (command.equals("version")) {
            commandVersion(options);
        } else if (command.length() == 0) {
        } else {
            commandUnknown(command, options);
        }
        if (options.isTime()) {
            Logger.message("%s: %dms", command, (System.currentTimeMillis() - start));
        }
    }

    private void commandCodeCompletion(Options options) {
        CodeCompletionResult result;
        Project project = codeAssist.getProject(options);
        try {
            progressMonitor.attach(project);
            if (options.isFileStdin()) {
                result = codeAssist.codeCompletion(project,
                                                   new File("(stdin)"),
                                                   options.getHereDocReader(inReader),
                                                   options.getLocation());
            } else {
                result = codeAssist.codeCompletion(project,
                                                   options.getFile(),
                                                   options.getEncoding(),
                                                   options.getLocation());
            }

            if (options.isPrintAST()) {
                Logger.debug("AST:\n%s", result.getAST());
            }

            String prefix = options.getPrefix();
            if (options.isTest()) {
                Set<String> data = new HashSet<String>();
                for (CodeCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                    data.add(completion.getCompletion());
                }
                test(options, data);
            } else {
                if (options.isEmacsFormat()) {
                    out.print("(");
                    out.print("(completion");
                    for (CodeCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                        if (prefix == null || completion.getCompletion().startsWith(prefix)) {
                            out.printf(" (\"%s\" \"%s\" \"%s\" \"%s\")",
                                       completion.getCompletion(),
                                       completion.getQualifiedName(),
                                       completion.getBaseName(),
                                       completion.getKind());
                        }
                    }
                    out.println(")");
                    codeAssistError(result, options);
                    out.println(")");
                } else {
                    for (CodeCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                        if (prefix == null || completion.getCompletion().startsWith(prefix)) {
                            out.printf("completion: %s %s %s %s\n",
                                       completion.getCompletion(),
                                       completion.getQualifiedName(),
                                       completion.getBaseName(),
                                       completion.getKind());
                        }
                    }
                    codeAssistError(result, options);
                }
            }
        } catch (Exception e) {
            if (options.isTest()) {
                testError(options);
            }
            commandException(e, options);
        } finally {
            progressMonitor.detach(project);
        }
    }

    private void commandTypeInference(Options options) {
        TypeInferenceResult result;
        Project project = codeAssist.getProject(options);
        try {
            progressMonitor.attach(project);
            if (options.isFileStdin()) {
                result = codeAssist.typeInference(project,
                                                  new File("(stdin)"),
                                                  options.getHereDocReader(inReader),
                                                  options.getLocation());
            } else {
                result = codeAssist.typeInference(project,
                                                  options.getFile(),
                                                  options.getEncoding(),
                                                  options.getLocation());
            }

            if (options.isPrintAST()) {
                Logger.debug("AST:\n%s", result.getAST());
            }
            
            if (options.isTest()) {
                Set<String> data = new HashSet<String>();
                for (IRubyObject klass : result.getTypeSet()) {
                    data.add(klass.toString());
                }
                test(options, data);
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
            if (options.isTest()) {
                testError(options);
            }
            commandException(e, options);
        } finally {
            progressMonitor.detach(project);
        }
    }

    private void commandFindDefinition(Options options) {
        FindDefinitionResult result;
        Project project = codeAssist.getProject(options);
        try {
            progressMonitor.attach(project);
            if (options.isFileStdin()) {
                result = codeAssist.findDefinition(project,
                                                   new File("(stdin)"),
                                                   options.getHereDocReader(inReader),
                                                   options.getLocation());
            } else {
                result = codeAssist.findDefinition(project,
                                                   options.getFile(),
                                                   options.getEncoding(),
                                                   options.getLocation());
            }

            if (options.isPrintAST()) {
                Logger.debug("AST:\n%s", result.getAST());
            }
            
            if (options.isTest()) {
                Set<String> data = new HashSet<String>();
                for (SourceLocation location : result.getLocations()) {
                    data.add(location.toString());
                }
                test(options, data);
            } else {
                if (options.isEmacsFormat()) {
                    out.print("(");
                    out.print("(location");
                    for (SourceLocation location : result.getLocations()) {
                        if (location.getFile() != null)
                            out.printf(" (%s . %d)", emacsStringLiteral(location.getFile()), location.getLine());
                    }
                    out.println(")");
                    codeAssistError(result, options);
                    out.println(")");
                } else {
                    for (SourceLocation location : result.getLocations()) {
                        out.printf("location: %d %s\n", location.getLine(), location.getFile());
                    }
                    codeAssistError(result, options);
                }
            }
        } catch (Exception e) {
            if (options.isTest())
                testError(options);
            commandException(e, options);
        } finally {
            progressMonitor.detach(project);
        }
    }

    private void commandWhere(Options options) {
        WhereResult result;
        Project project = codeAssist.getProject(options);
        try {
            progressMonitor.attach(project);
            if (options.isFileStdin()) {
                result = codeAssist.where(project,
                                          new File("(stdin)"),
                                          options.getHereDocReader(inReader),
                                          options.getLine());
            } else {
                result = codeAssist.where(project,
                                          options.getFile(),
                                          options.getEncoding(),
                                          options.getLine());
            }

            if (options.isPrintAST()) {
                Logger.debug("AST:\n%s", result.getAST());
            }

            if (options.isTest()) {
                Set<String> data = new HashSet<String>();
                if (result.getName() != null)
                    data.add(result.getName());
                test(options, data);
            } else {
                if (options.isEmacsFormat()) {
                    out.print("(");
                    if (result.getName() != null)
                        out.printf("(name . \"%s\")", result.getName());
                    codeAssistError(result, options);
                    out.println(")");
                } else {
                    if (result.getName() != null) {
                        out.print("name: ");
                        out.println(result.getName());
                    }
                    codeAssistError(result, options);
                }
            }
        } catch (Exception e) {
            if (options.isTest())
                testError(options);
            commandException(e, options);
        } finally {
            progressMonitor.detach(project);
        }
    }

    private void commandLoad(Options options) {
        LoadResult result;
        Project project = codeAssist.getProject(options);
        try {
            progressMonitor.attach(project);
            if (options.isFileStdin()) {
                result = codeAssist.load(project,
                                         new File("(stdin)"),
                                         options.getHereDocReader(inReader));
            } else {
                result = codeAssist.load(project,
                                         options.getFile(),
                                         options.getEncoding());
            }

            if (options.isPrintAST()) {
                Logger.debug("AST:\n%s", result.getAST());
            }
            
            if (options.isEmacsFormat()) {
                out.print("(");
                codeAssistError(result, options);
                out.println(")");
            } else {
                codeAssistError(result, options);
            }
        } catch (Exception e) {
            commandException(e, options);
        } finally {
            progressMonitor.detach(project);
        }
    }

    private void commandClear(Options options) {
        codeAssist.clear();
    }

    private void commandGC(Options options) {
        System.gc();
    }

    private void commandListProject(Options options) {
        boolean first = true;
        boolean verbose = options.isVerbose();
        if (options.isEmacsFormat()) {
            out.print("(");
        }
        for (Map.Entry<String, Project> entry : codeAssist.getProjects().entrySet()) {
            String name = entry.getKey();
            Project project = entry.getValue();
            
            if (verbose) {
                if (!first) {
                    out.println();
                }
                first = false;

                out.printf("%s:\n", name);
                out.printf("  path: %s\n", project.getPath());
                out.println("  load-path:");
                for (File dir : project.getLoadPath()) {
                    out.println("    - " + dir.toString());
                }
                out.println("  gem-path:");
                for (File dir : project.getGemPath()) {
                    out.println("    - " + dir.toString());
                }
            } else {
                if (options.isEmacsFormat()) {
                    out.print("\"" + name + "\" ");
                } else {
                    out.println(name);
                }
            }
        }
        if (options.isEmacsFormat()) {
            out.println(")");
        }
    }

    private void commandOpenProject(Options options) {
        for (String name : options.getRestArgs()) {
            codeAssist.openProject(name, options);
        }
    }

    private void commandCloseProject(Options options) {
        for (String name : options.getRestArgs()) {
            codeAssist.closeProject(name);
        }
    }

    private void commandEnvironment(Options options) {
        out.println("version: " + versionString());
        out.println("home: " + options.getRsenseHome());
        out.println("debug: " + (options.isDebug() ? "yes" : "no"));
        out.println("log: " + (options.getLog() != null ? options.getLog() : ""));
        out.println("load-path:");
        for (String path : options.getLoadPath()) {
            out.println("  - " + path);
        }
        out.println("gem-path:");
        for (String path : options.getLoadPath()) {
            out.println("  - " + path);
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
            out.println("\"" + versionString() + "\"");
        } else {
            version();
        }
    }

    private void commandUnknown(String command, Options options) {
        if (options.isEmacsFormat()) {
            out.printf("((error . \"unknown command: %s\"))\n", command);
        } else {
            out.printf("unknown command: %s\n", command);
        }
    }

    private void commandException(Exception e, Options options) {
        if (options.isEmacsFormat()) {
            out.println("((error . \"unexpected error\"))");
        } else if (e instanceof Options.InvalidOptionException) {
            out.println(e.getMessage());
        } else {
            out.println("unexpected error:");
            e.printStackTrace(out);
        }
    }

    private void test(Options options, Collection<String> data) {
        if (options.isShouldBeGiven()) {
            Set<String> shouldBe = options.getShouldBe();
            if (shouldBe.equals(data)) {
                testSuccess(options);
            } else {
                String expected = shouldBe.isEmpty() ? "empty" : shouldBe.toString();
                testFailure(options, "%s should be %s", data, expected);
            }
        } else {
            Set<String> shouldContain = options.getShouldContain();
            Set<String> shouldNotContain = options.getShouldNotContain();
            for (String str : shouldContain) {
                if (!data.contains(str)) {
                    testFailure(options, "%s should be in %s", str, shouldContain);
                    return;
                }
            }
            for (String str : shouldNotContain) {
                if (data.contains(str)) {
                    testFailure(options, "%s should not be in %s", str, shouldNotContain);
                    return;
                }
            }
            testSuccess(options);
        }
    }

    private void testSuccess(Options options) {
        testSuccess(options, null);
    }
    
    private void testSuccess(Options options, String format, Object... args) {
        if (testStats == null) {
            testStats = new TestStats();
        }
        testStats.count++;
        testStats.success++;
        if (options.isTestColor()) {
            out.printf("\33[34m%s\33[0m... [\33[1;32mOK\33[0m]", options.getTest());
        } else {
            out.printf("%s... [OK]", options.getTest());
        }
        if (format != null) {
            out.print("\n  ");
            out.printf(format, args);
        }
        out.println();
    }

    private void testFailure(Options options) {
        testFailure(options, null);
    }
    
    private void testFailure(Options options, String format, Object... args) {
        if (testStats == null) {
            testStats = new TestStats();
        }
        testStats.count++;
        testStats.failure++;
        if (options.isTestColor()) {
            out.printf("\33[34m%s\33[0m... [\33[1;31mBAD\33[0m]", options.getTest());
        } else {
            out.printf("%s... [BAD]", options.getTest());
        }
        if (format != null) {
            out.print("\n  ");
            out.printf(format, args);
        }
        out.println();
    }

    private void testError(Options options) {
        if (testStats == null) {
            testStats = new TestStats();
        }
        testStats.count++;
        testStats.error++;
        if (options.isTestColor()) {
            out.printf("\33[34m%s\33[0m... [\33[1;31mERROR\33[0m]", options.getTest());
        } else {
            out.printf("%s... [BAD]", options.getTest());
        }
        out.println();
    }

    private void testResult(Options options) {
        if (testStats != null) {
            String ok, bad, error;
            if (options.isTestColor()) {
                ok = String.format("\33[32;1m%s\33[0m", testStats.success);
                if (testStats.failure > 0) {
                    bad = String.format("\33[31;1m%s\33[0m", testStats.failure);
                } else {
                    bad = String.valueOf(testStats.failure);
                }
                if (testStats.error > 0) {
                    error = String.format("\33[31;1m%s\33[0m", testStats.error);
                } else {
                    error = String.valueOf(testStats.error);
                }
            } else {
                ok = String.valueOf(testStats.success);
                bad = String.valueOf(testStats.failure);
                error = String.valueOf(testStats.error);
            }
            out.printf("test: count=%d, success=%s, failure=%s, error=%s\n", testStats.count, ok, bad, error);
        }
    }

    private void codeAssistError(CodeAssistResult result, Options options) {
        boolean emacsFormat = options.isEmacsFormat();
        if (emacsFormat) {
            out.print(" (error");
        }
        for (CodeAssistError error : result.getErrors()) {
            if (emacsFormat) {
                out.print(" ");
                out.print(error.getShortError());
            } else {
                out.printf("error: ", error.getShortError());
                out.println(error.getCause());
            }
        }
        if (emacsFormat) {
            out.println(")");
        }
    }

    private String emacsStringLiteral(String s) {
        return "\"" + s.replace("\\", "\\\\").replaceAll("\"", "\\\"") + "\"";
    }
}
