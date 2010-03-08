package org.cx4a.rsense.ruby;

import org.jruby.ast.Node;
import org.jruby.lexer.yacc.ISourcePosition;

public class DynamicMethod {
    protected RubyModule cbase;
    protected Visibility visibility;
    protected ISourcePosition position;

    public DynamicMethod(RubyModule cbase, Visibility visibility, ISourcePosition position) {
        this.cbase = cbase;
        this.visibility = visibility;
        this.position = position;
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

    public ISourcePosition getPosition() {
        return position;
    }
}
