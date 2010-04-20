package org.cx4a.rsense;

import java.util.List;

import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.util.SourceLocation;

public class FindDefinitionResult extends CodeAssistResult {
    private List<SourceLocation> locations;

    public void setLocations(List<SourceLocation> locations) {
        this.locations = locations;
    }

    public List<SourceLocation> getLocations() {
        return locations;
    }

    public static FindDefinitionResult failWithException(String message, Throwable cause) {
        FindDefinitionResult result = new FindDefinitionResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
