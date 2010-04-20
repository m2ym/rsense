package org.cx4a.rsense;

import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.typing.runtime.Method;

public class WhereResult extends CodeAssistResult {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static WhereResult failWithException(String message, Throwable cause) {
        WhereResult result = new WhereResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
