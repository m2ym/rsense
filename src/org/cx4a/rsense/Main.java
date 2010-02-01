package org.cx4a.rsense;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.util.Logger;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 4) {
            System.out.println("Usage: command file encoding offset");
            return;
        }

        Logger.setDebug(true);

        String command = args[0];
        File file = new File(args[1]);
        String encoding = args[2];
        int offset = Integer.parseInt(args[3]);

        String rsenseHome = System.getProperty("rsense.home");
        if (rsenseHome == null) {
            rsenseHome = ".";
        }

        Project sandbox = new Project("<sandbox>", null);
        Config config = new Config(rsenseHome);
        CodeAssist codeAssist = new CodeAssist(config);
        codeAssist.load(sandbox, new File(config.rsenseHome + "/stubs/1.8/base_types.rb"), "UTF-8");

        if (command.equals("infer-type")) {
            InferTypeResult result = codeAssist.inferType(sandbox, file, encoding, offset);
            for (IRubyObject klass : result.getTypeSet()) {
                System.out.println(klass);
            }
        } else if (command.equals("suggest-completion")) {
            SuggestCompletionResult result = codeAssist.suggestCompletion(sandbox, file, encoding, offset);
            for (SuggestCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                System.out.println(completion);
            }
        }
    }
}
