package org.cx4a.rsense.util;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.jruby.ast.Node;
import org.jruby.ast.types.INameNode;

public class NodeDiff {
    protected List<Node> diff;
    
    public NodeDiff() {
    }

    public List<Node> diff(Node newNode, Node oldNode) {
        diff = new ArrayList<Node>();
        if (isSameSequence(newNode, oldNode)) {
            return diff;
        } else {
            return null;
        }
    }

    public boolean noDiff(Node newNode, Node oldNode) {
        return diff(newNode, oldNode) != null;
    }

    protected boolean isSameNode(Node a, Node b) {
        return a.getClass() == b.getClass()
            && ((a instanceof INameNode)
                ? ((INameNode) a).getName().equals(((INameNode) b).getName())
                : true);
    }

    protected boolean isSameSequence(Node a, Node b) {
        if (a == null && b == null) {
            return true;
        } else if (a == null || b == null || !isSameNode(a, b)) {
            return false;
        }

        List<Node> m = a.childNodes();
        List<Node> n = b.childNodes();
        if (m == null || n == null) {
            return false;
        }

        Iterator<Node> i = m.iterator();
        Iterator<Node> j = n.iterator();
        while (true) {
            Node x = getNextNode(i, true);
            Node y = getNextNode(j, false);
            if (x == null && y == null) {
                return true;
            } else if (x == null || y == null || !isSameSequence(x, y)) {
                return false;
            }
        }
    }

    protected Node getNextNode(Iterator<Node> ite, boolean newNode) {
        return ite.hasNext() ? ite.next() : null;
    }

    protected boolean isStatementNode(Node node) {
        return !isStructuralNode(node);
    }

    protected boolean isStructuralNode(Node node) {
        switch (node.getNodeType()) {
        case CLASSNODE: case SCLASSNODE: case MODULENODE:
        case DEFNNODE: case DEFSNODE:
            return true;
        default:
            return false;
        }
    }
}
