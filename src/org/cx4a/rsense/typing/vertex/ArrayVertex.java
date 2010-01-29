package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.ArrayNode;

import org.cx4a.rsense.typing.Propagation;

public class ArrayVertex extends Vertex {
    private Vertex[] elementVertices;

    public ArrayVertex(ArrayNode node, Vertex[] elementVertices) {
        super(node);
        this.elementVertices = elementVertices;
        if (elementVertices != null) {
            for (Vertex v : elementVertices) {
                v.addEdge(this);
            }
        }
    }

    public Vertex[] getElementVertices() {
        return elementVertices;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateArrayVertex(propagation, this, src);
    }
}
