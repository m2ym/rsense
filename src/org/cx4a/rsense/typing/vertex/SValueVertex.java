package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.Node;

import org.cx4a.rsense.typing.Propagation;

public class SValueVertex extends Vertex {
    Vertex vertex;

    public SValueVertex(Node node, Vertex vertex) {
        super(node);
        this.vertex = vertex;
        vertex.addEdge(this);
    }

    public Vertex getValueVertex() {
        return vertex;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateSValueVertex(propagation, this, src);
    }
}
