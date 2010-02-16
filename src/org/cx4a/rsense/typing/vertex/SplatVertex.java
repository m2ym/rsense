package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.Node;

import org.cx4a.rsense.typing.Propagation;

public class SplatVertex extends Vertex {
    private Vertex valueVertex;

    public SplatVertex(Node node, Vertex valueVertex) {
        super(node);
        this.valueVertex = valueVertex;
        valueVertex.addEdge(this);
    }

    public Vertex getValueVertex() {
        return valueVertex;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateSplatVertex(propagation, this, src);
    }
}
