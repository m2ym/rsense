package org.cx4a.rsense;

import java.util.List;

import org.jruby.ast.Node;

public class SuggestCompletionResult extends CodeAssistResult {
    public static class CompletionCandidate {
        private String completion;

        public CompletionCandidate(String completion) {
            this.completion = completion;
        }

        public String getCompletion() {
            return completion;
        }

        @Override
        public String toString() {
            return completion;
        }
    }

    private List<CompletionCandidate> candidates;
    
    public SuggestCompletionResult() {
        super();
    }

    public void setCandidates(List<CompletionCandidate> candidates) {
        this.candidates = candidates;
    }

    public List<CompletionCandidate> getCandidates() {
        return candidates;
    }

    public static SuggestCompletionResult failWithException(String message, Throwable cause) {
        SuggestCompletionResult result = new SuggestCompletionResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
