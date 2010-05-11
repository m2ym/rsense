package org.cx4a.rsense;

import java.util.Collection;
import java.util.Collections;

import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.util.SourceLocation;

public class FindDefinitionResult extends CodeAssistResult {
    private Collection<SourceLocation> locations = Collections.<SourceLocation>emptyList();

    public void setLocations(Collection<SourceLocation> locations) {
        this.locations = locations;
    }

    public Collection<SourceLocation> getLocations() {
        return locations;
    }

    public static FindDefinitionResult failWithException(String message, Throwable cause) {
        FindDefinitionResult result = new FindDefinitionResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
