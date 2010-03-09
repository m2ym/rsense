package org.cx4a.rsense.util;

import org.jruby.ast.Node;

public class NodeUtil {
    private static NodeDiff diff = new NodeDiff();

    private NodeUtil() {}

    public static int nodeHashCode(Node node) {
        int code = 0;
        if (node != null) {
            code ^= node.getClass().hashCode();
            for (Node child : node.childNodes()) {
                code ^= child.getClass().hashCode();
            }
        }
        return code;
    }

    public static boolean nodeEquals(Node a, Node b) {
        if ((a == null) ^ (b == null)) {
            return false;
        } else if (a == null) {
            return true;
        }
        return diff.noDiff(a, b);
    }
}
