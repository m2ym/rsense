package org.cx4a.rsense.ruby;

public class DynamicScope extends LocalScope {
    private Scope scope;
    private int snapshot;

    public DynamicScope(RubyModule cref, Scope scope) {
        super(cref);
        this.scope = scope;
        this.snapshot = scope.hashCode();
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

    @Override
    public int hashCode() {
        return snapshot;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof DynamicScope)) {
            return false;
        }

        DynamicScope o = (DynamicScope) other;
        return snapshot == o.snapshot;
    }

    @Override
    public String toString() {
        return "<DynScope: " + scope.toString() + ">";
    }
}
