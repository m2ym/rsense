package org.cx4a.rsense.util;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

import org.jruby.ast.Node;

public class NodeDiff {
    public NodeDiff() {
    }

    public List<Node> diff(Node newNode, Node oldNode) {
        if (isSameStatementSequence(newNode, oldNode)) {
            return collectStructualNodes(newNode);
        } else {
            return null;
        }
    }

    public boolean noDiff(Node newNode, Node oldNode) {
        return diff(newNode, oldNode) != null;
    }

    protected List<Node> collectStructualNodes(Node node) {
        List<Node> result = new ArrayList<Node>();
        if (node != null) {
            for (Node child : node.childNodes()) {
                if (isStructuralNode(node)) {
                    result.add(child);
                }
            }
        }
        return result;
    }

    protected boolean isSameNode(Node a, Node b) {
        return a.getNodeType() == b.getNodeType();
    }

    protected boolean isSameStatementSequence(Node a, Node b) {
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
            Node x = getNextStatementNode(i);
            Node y = getNextStatementNode(j);
            if (x == null && y == null) {
                return true;
            } else if (x == null || y == null || !isSameStatementSequence(x, y)) {
                return false;
            }
        }
    }

    protected Node getNextStatementNode(Iterator<Node> ite) {
        while (ite.hasNext()) {
            Node node = ite.next();
            if (isStatementNode(node)) {
                return node;
            }
        }
        return null;
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
