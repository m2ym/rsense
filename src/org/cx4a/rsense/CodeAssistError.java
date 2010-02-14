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

    public String getShortError() {
        return message != null ? message : (cause != null ? cause.getMessage() : "unknown error");
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }
}
