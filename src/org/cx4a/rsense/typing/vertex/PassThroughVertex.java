package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.Node;

import org.cx4a.rsense.typing.Propagation;

public class PassThroughVertex extends Vertex {
    public PassThroughVertex() {
        super();
    }
    
    public PassThroughVertex(Node node) {
        super(node);
    }
    
    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagatePassThroughVertex(propagation, this, src);
    }
}
