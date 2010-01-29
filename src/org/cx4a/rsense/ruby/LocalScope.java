package org.cx4a.rsense.ruby;

import java.util.Map;
import java.util.HashMap;

public class LocalScope implements Scope {
    private Map<String, IRubyObject> localVars;

    public LocalScope() {
        this.localVars = new HashMap<String, IRubyObject>();
    }

    public IRubyObject getValue(String name) {
        return localVars.get(name);
    }

    public void setValue(String name, IRubyObject value) {
        localVars.put(name, value);
    }
}
