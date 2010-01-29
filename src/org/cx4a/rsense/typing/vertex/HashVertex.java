package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.HashNode;

import org.cx4a.rsense.typing.Propagation;

public class HashVertex extends Vertex {
    private Vertex[] elementVertices;

    public HashVertex(HashNode node, Vertex[] elementVertices) {
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
        return propagation.getGraph().propagateHashVertex(propagation, this, src);
    }
}
