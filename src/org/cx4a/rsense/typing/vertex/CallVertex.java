package org.cx4a.rsense.typing.vertex;

import org.jruby.RubyClass;
import org.jruby.ast.Node;
import org.jruby.ast.types.INameNode;

import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Propagation;

public class CallVertex extends Vertex {
    private Vertex receiverVertex;
    private Vertex[] argVertices;
    private Block block;

    public CallVertex(Node node, Vertex receiverVertex, Vertex[] argVertices, Block block) {
        super(node);
        this.receiverVertex = receiverVertex;
        this.argVertices = argVertices;
        this.block = block;
        receiverVertex.addEdge(this);
        if (argVertices != null) {
            for (Vertex v : argVertices) {
                v.addEdge(this);
            }
        }
    }

    public String getName() {
        return ((INameNode) node).getName();
    }

    public Vertex getReceiverVertex() {
        return receiverVertex;
    }

    public Vertex[] getArgVertices() {
        return argVertices;
    }

    public Block getBlock() {
        return block;
    }

    public boolean isApplicable() {
        if (receiverVertex == null || receiverVertex.isEmpty()) {
            return false;
        }
        
        boolean applicable = true;
        if (argVertices != null) {
            for (Vertex v : argVertices) {
                if (v.isEmpty()) {
                    applicable = false;
                    break;
                }
            }
        }
        return applicable;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateCallVertex(propagation, this, src);
    }
}
