package org.cx4a.rsense.ruby;

import org.jruby.ast.Node;

import org.cx4a.rsense.util.SourceLocation;

public interface DynamicMethod {
    public RubyModule getModule();
    public Visibility getVisibility();
    public void setVisibility(Visibility visibility);
    public SourceLocation getLocation();
}
