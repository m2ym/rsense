package org.cx4a.rsense;

import java.util.List;

import org.jruby.ast.Node;

public class CodeCompletionResult extends CodeAssistResult {
    public static class CompletionCandidate {
        private String completion;
        private String qualifiedName;

        public CompletionCandidate(String completion, String qualifiedName) {
            this.completion = completion;
            this.qualifiedName = qualifiedName;
        }

        public String getCompletion() {
            return completion;
        }

        public String getQualifiedName() {
            return qualifiedName;
        }

        @Override
        public String toString() {
            return completion;
        }
    }

    private List<CompletionCandidate> candidates;
    
    public CodeCompletionResult() {
        super();
    }

    public void setCandidates(List<CompletionCandidate> candidates) {
        this.candidates = candidates;
    }

    public List<CompletionCandidate> getCandidates() {
        return candidates;
    }

    public static CodeCompletionResult failWithException(String message, Throwable cause) {
        CodeCompletionResult result = new CodeCompletionResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
