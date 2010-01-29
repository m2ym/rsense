package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.MultipleAsgnNode;
import org.jruby.ast.NodeType;

import org.cx4a.rsense.typing.Propagation;

public class MultipleAsgnVertex extends Vertex {
    private Vertex valueVertex;

    public MultipleAsgnVertex(MultipleAsgnNode node, Vertex valueVertex) {
        super(node);
        this.valueVertex = valueVertex;
    }

    public Vertex getValueVertex() {
        return valueVertex;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateMultipleAsgnVertex(propagation, this, src);
    }
}
