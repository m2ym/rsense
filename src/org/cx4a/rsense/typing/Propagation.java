package org.cx4a.rsense.typing;

import java.util.Set;
import java.util.HashSet;

import org.cx4a.rsense.typing.vertex.Vertex;

public class Propagation {
    public final static int MAX_DEPTH = 10;
    
    private Graph graph;
    private Set<Object> visited;
    private int depth;
    private int refCount;

    public Propagation(Graph graph) {
        this.graph = graph;
        this.depth = 0;
        this.refCount = 0;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isVisited(Vertex vertex) {
        return visited != null ? visited.contains(getVisitTag(vertex)) : false;
    }

    public void addVisited(Vertex vertex) {
        if (visited == null) {
            // lazy allocation
            visited = new HashSet<Object>();
        }
        visited.add(getVisitTag(vertex));
    }

    public boolean checkVisited(Vertex vertex) {
        if (isVisited(vertex)) {
            return true;
        } else {
            addVisited(vertex);
            return false;
        }
    }

    public boolean visit(Vertex vertex, Vertex src) {
        // Cut out deper branches.
        // This makes results inprecise but also performace improved.
        if (depth >= MAX_DEPTH) {
            return false;
        }

        try {
            depth++;
            return vertex.accept(this, src);
        } finally {
            depth--;
        }
    }

    public void retain() {
        refCount++;
    }

    public boolean release() {
        return --refCount == 0;
    }

    private Object getVisitTag(Vertex vertex) {
        return vertex.getNode() != null ? vertex.getNode() : vertex;
    }
}
