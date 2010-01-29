package org.cx4a.rsense.typing;

import java.util.Set;
import java.util.HashSet;

import org.cx4a.rsense.typing.vertex.Vertex;

public class Propagation {
    private Graph graph;
    private Set<Vertex> visited;

    public Propagation(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public boolean isVisited(Vertex vertex) {
        return visited != null ? visited.contains(vertex) : false;
    }

    public void addVisited(Vertex vertex) {
        if (visited == null) {
            // lazy allocation
            visited = new HashSet<Vertex>();
        }
        visited.add(vertex);
    }

    public boolean checkVisited(Vertex vertex) {
        if (isVisited(vertex)) {
            return true;
        } else {
            addVisited(vertex);
            return false;
        }
    }
}
