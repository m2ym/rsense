package org.cx4a.rsense;

import java.util.List;
import java.util.Collections;

import org.jruby.ast.Node;

public class CodeCompletionResult extends CodeAssistResult {
    public static class CompletionCandidate {
        public enum Kind {
            CLASS, MODULE, CONSTANT, METHOD,
        };

        private String completion;
        private String qualifiedName;
        private String baseName;
        private Kind kind;

        public CompletionCandidate(String completion, String qualifiedName, String baseName, Kind kind) {
            this.completion = completion;
            this.qualifiedName = qualifiedName;
            this.baseName = baseName;
            this.kind = kind;
        }

        public String getCompletion() {
            return completion;
        }

        public String getQualifiedName() {
            return qualifiedName;
        }

        public String getBaseName() {
            return baseName;
        }

        public Kind getKind() {
            return kind;
        }

        @Override
        public String toString() {
            return completion;
        }
    }

    private List<CompletionCandidate> candidates = Collections.<CompletionCandidate>emptyList();
    
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
