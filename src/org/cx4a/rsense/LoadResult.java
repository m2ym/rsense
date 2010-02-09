package org.cx4a.rsense;

public class LoadResult extends CodeAssistResult {
    public static LoadResult failWithNotFound() {
        LoadResult result = new LoadResult();
        result.addError(new CodeAssistError("File not found"));
        return result;
    }
    
    public static LoadResult failWithException(String message, Throwable cause) {
        LoadResult result = new LoadResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }

    public static LoadResult alreadyLoaded() {
        return new LoadResult();
    }
}
