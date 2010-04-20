package org.cx4a.rsense.ruby;

import java.util.List;

import org.jruby.ast.Node;

public interface IRubyObject {
    public Ruby getRuntime();
    public RubyClass getMetaClass();
    public void setMetaClass(RubyClass metaClass);
    public RubyClass getSingletonClass();
    public RubyClass makeMetaClass(RubyClass superClass);
    public IRubyObject getInstVar(String name);
    public void setInstVar(String name, IRubyObject value);
    public boolean isNil();
    public boolean isInstanceOf(RubyModule klass);
    public boolean isKindOf(RubyModule klass);
    public Object getTag();
    public void setTag(Object tag);
    public int hashCode(int depth);
}
