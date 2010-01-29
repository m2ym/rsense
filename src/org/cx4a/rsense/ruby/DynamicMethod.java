package org.cx4a.rsense.ruby;

import org.jruby.ast.Node;
import org.jruby.lexer.yacc.ISourcePosition;

public class DynamicMethod {
    protected RubyModule cbase;
    protected Node bodyNode, argsNode;
    protected Visibility visibility;
    protected ISourcePosition position;

    public DynamicMethod(RubyModule cbase, Node bodyNode, Node argsNode, Visibility visibility, ISourcePosition position) {
        this.cbase = cbase;
        this.bodyNode = bodyNode;
        this.argsNode = argsNode;
        this.visibility = visibility;
        this.position = position;
    }

    public RubyModule getModule() {
        return cbase;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public Node getArgsNode() {
        return argsNode;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public ISourcePosition getPosition() {
        return position;
    }
}
