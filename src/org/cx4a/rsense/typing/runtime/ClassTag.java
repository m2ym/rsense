package org.cx4a.rsense.typing.runtime;

import org.jruby.ast.Node;

import org.cx4a.rsense.typing.annotation.ClassType;

public class ClassTag {
    private Node bodyNode;
    private ClassType type;

    public ClassTag(Node node) {
        this(node, null);
    }
    
    public ClassTag(Node bodyNode, ClassType type) {
        this.bodyNode = bodyNode;
        this.type = type;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public ClassType getType() {
        return type;
    }
}
