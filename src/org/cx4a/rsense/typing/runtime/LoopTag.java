package org.cx4a.rsense.typing.runtime;

import org.cx4a.rsense.typing.vertex.Vertex;

public class LoopTag {
    private Vertex returnVertex;
    private Vertex yieldVertex;

    public LoopTag(Vertex returnVertex, Vertex yieldVertex) {
        this.returnVertex = returnVertex;
        this.yieldVertex = yieldVertex;
    }

    public Vertex getReturnVertex() {
        return returnVertex;
    }

    public Vertex getYieldVertex() {
        return yieldVertex;
    }
}
