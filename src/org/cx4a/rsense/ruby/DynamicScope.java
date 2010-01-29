package org.cx4a.rsense.ruby;

public class DynamicScope extends LocalScope {
    private Scope scope;

    public DynamicScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public IRubyObject getValue(String name) {
        IRubyObject value = super.getValue(name);
        return value != null ? value : scope.getValue(name);
    }
    
    @Override
    public void setValue(String name, IRubyObject value) {
        if (scope.getValue(name) != null) {
            scope.setValue(name, value);
        } else {
            super.setValue(name, value);
        }
    }
}
