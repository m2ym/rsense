package org.cx4a.rsense.ruby;

import org.jruby.ast.Node;

import org.cx4a.rsense.util.SourceLocation;

public class DynamicMethod {
    protected RubyModule cbase;
    protected Visibility visibility;
    protected SourceLocation location;

    public DynamicMethod(RubyModule cbase, Visibility visibility, SourceLocation location) {
        this.cbase = cbase;
        this.visibility = visibility;
        this.location = location;
    }

    public RubyModule getModule() {
        return cbase;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public SourceLocation getLocation() {
        return location;
    }
}
