package org.cx4a.rsense;

public class CodeAssistError {
    private String message;
    private Throwable cause;

    public CodeAssistError(String message) {
        this.message = message;
    }

    public CodeAssistError(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public CodeAssistError(Throwable cause) {
        this.cause = cause;
    }
}
