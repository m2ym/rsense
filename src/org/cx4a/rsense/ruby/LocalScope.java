package org.cx4a.rsense.ruby;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;

public class LocalScope implements Scope {
    private RubyModule cref;
    private Map<String, IRubyObject> localVars;

    public LocalScope(RubyModule cref) {
        this.cref = cref;
        this.localVars = new HashMap<String, IRubyObject>();
    }

    public RubyModule getModule() {
        return cref;
    }

    public IRubyObject getValue(String name) {
        return localVars.get(name);
    }

    public void setValue(String name, IRubyObject value) {
        localVars.put(name, value);
    }

    @Override
    public int hashCode() {
        int code = 0;
        code = cref.hashCode();
        for (Map.Entry<String, IRubyObject> entry : localVars.entrySet()) {
            code ^= entry.getKey().hashCode();
            code <<= 4;
            code ^= entry.getValue().hashCode();
        }
        return code;
    }

    @Override
    public String toString() {
        return "<LocalScope: " + cref.toString() + " " + localVars.toString() + ">";
    }
}
