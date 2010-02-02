package org.cx4a.rsense.ruby;

public interface Scope {
    public RubyModule getModule();
    public IRubyObject getValue(String name);
    public void setValue(String name, IRubyObject value);
}
