package org.cx4a.rsense.ruby;

import org.jruby.ast.Node;

public class Block {
    private Node varNode;
    private Node bodyNode;
    private Frame frame;
    private Scope scope;

    public Block(Node varNode, Node bodyNode, Frame frame, Scope scope) {
        this.varNode = varNode;
        this.bodyNode = bodyNode;
        this.frame = frame;
        this.scope = scope;
    }

    public Node getVarNode() {
        return varNode;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public Frame getFrame() {
        return frame;
    }

    public Scope getScope() {
        return scope;
    }
}
