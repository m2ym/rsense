package org.cx4a.rsense;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.jruby.Ruby;
import org.jruby.ast.Node;

import org.cx4a.rsense.typing.TypeInferencer;
import org.cx4a.rsense.util.Logger;

public class Main {
    public static void main(String[] args) {
        try {
            Logger.setDebug(true);

            String rsenseHome = System.getProperty("rsense.home");
            if (rsenseHome == null) {
                rsenseHome = ".";
            }

            Project sandbox = new Project("<sandbox>", null);
            Config config = new Config(rsenseHome);
            CodeAssist codeAssist = new CodeAssist(config);
            codeAssist.load(sandbox, new File(config.rsenseHome + "/stubs/1.8/base_types.rb"), "UTF-8");
            SuggestCompletionResult result = codeAssist.suggestCompletion(sandbox, new File(args[0]), args[1], Integer.parseInt(args[2]));
            for (SuggestCompletionResult.CompletionCandidate completion : result.getCandidates()) {
                System.out.println(completion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
