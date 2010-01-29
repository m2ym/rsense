package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.YieldNode;

import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Propagation;

public class YieldVertex extends Vertex {
    private Block block;
    private Vertex argsVertex;

    public YieldVertex(YieldNode node, Block block, Vertex argsVertex) {
        super(node);
        this.block = block;
        this.argsVertex = argsVertex;
        argsVertex.addEdge(this);
    }

    public Block getBlock() {
        return block;
    }
    
    public Vertex getArgsVertex() {
        return argsVertex;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateYieldVertex(propagation, this, src);
    }
}
