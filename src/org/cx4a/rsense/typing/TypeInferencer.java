package org.cx4a.rsense.typing;

import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.Ruby;

public class TypeInferencer {
    private Ruby runtime;
    private Graph graph;
    private Map<String, Node> rootNodes;

    public TypeInferencer() {
        this.runtime = new Ruby();
        this.graph = new Graph(runtime);
        this.rootNodes = new HashMap<String, Node>();
    }

    public void infer(String name, Node node) {
        rootNodes.put(name, node);
        graph.createVertex(node);
    }
}
