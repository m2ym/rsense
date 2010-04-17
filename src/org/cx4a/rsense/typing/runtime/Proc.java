package org.cx4a.rsense.typing.runtime;

import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.TypeVarVertex;
import org.cx4a.rsense.typing.vertex.YieldVertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.util.NodeUtil;
import org.cx4a.rsense.util.Logger;

public class Proc extends RubyObject implements Block {
    private Node varNode;
    private Node bodyNode;
    private Frame frame;
    private Scope scope;
    private Set<YieldVertex> yields;
    private int varNodeHashCode, bodyNodeHashCode;
    
    public Proc(Ruby runtime, Node varNode, Node bodyNode, Frame frame, Scope scope) {
        super(runtime, runtime.getProc());
        this.varNode = varNode;
        this.bodyNode = bodyNode;
        this.frame = frame;
        this.scope = scope;
        this.varNodeHashCode = 0;
        this.bodyNodeHashCode = 0;
    }

    public Node getVarNode() {
        return varNode;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public int getVarNodeHashCode() {
        if (varNodeHashCode == 0) {
            varNodeHashCode = NodeUtil.nodeHashCode(varNode);
        }
        return varNodeHashCode;
    }

    public int getBodyNodeHashCode() {
        if (bodyNodeHashCode == 0) {
            bodyNodeHashCode = NodeUtil.nodeHashCode(bodyNode);
        }
        return bodyNodeHashCode;
    }

    public Frame getFrame() {
        return frame;
    }

    public Scope getScope() {
        return scope;
    }

    public Set<YieldVertex> getYields() {
        return yields != null ? yields : Collections.<YieldVertex>emptySet();
    }

    public boolean recordYield(YieldVertex vertex) {
        if (yields == null) {
            yields = new HashSet<YieldVertex>();
        }
        return yields.add(vertex);
    }

    public boolean isApplied(YieldVertex vertex) {
        return yields != null && yields.contains(vertex);
    }

    @Override
    public int hashCode() {
        return getVarNodeHashCode() ^ getBodyNodeHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof Proc)) {
            return false;
        }

        Proc o = (Proc) other;
        return hashCode() == o.hashCode()
            && frame.equals(o.frame)
            && scope.equals(o.scope);
    }
}
