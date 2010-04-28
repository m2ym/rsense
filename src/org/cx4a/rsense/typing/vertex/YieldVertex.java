package org.cx4a.rsense.typing.vertex;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Propagation;
import org.cx4a.rsense.typing.Template;

public class YieldVertex extends Vertex {
    private Template template;
    private Block block;
    private Vertex argsVertex;
    private boolean expandArguments;

    public YieldVertex(Node node, Template template, Block block, Vertex argsVertex, boolean expandArguments) {
        super(node);
        this.template = template;
        this.block = block;
        this.argsVertex = argsVertex;
        this.expandArguments = expandArguments;
        if (argsVertex != null) {
            argsVertex.addEdge(this);
        }
    }

    public Template getTemplate() {
        return template;
    }
    
    public Block getBlock() {
        return block;
    }
    
    public Vertex getArgsVertex() {
        return argsVertex;
    }

    public boolean getExpandArguments() {
        return expandArguments;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateYieldVertex(propagation, this, src);
    }

    @Override
    public int hashCode() {
        return template == null ? block.hashCode() : template.getAttribute().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof YieldVertex)) {
            return false;
        }

        YieldVertex o = (YieldVertex) other;
        return (template == null
                && o.template == null
                && block.equals(o.block))
            || (template != null
                && o.template != null
                && template.getAttribute().equals(o.template.getAttribute()));
    }
}
