package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.ToAryNode;

import org.cx4a.rsense.typing.Propagation;

public class ToAryVertex extends Vertex {
    private Vertex valueVertex;

    public ToAryVertex(ToAryNode node, Vertex valueVertex) {
        super(node);
        this.valueVertex = valueVertex;
    }

    public Vertex getValueVertex() {
        return valueVertex;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateToAryVertex(propagation, this, src);
    }
}