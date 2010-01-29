package org.cx4a.rsense.typing.runtime;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.typing.vertex.Vertex;

public class VertexHolder extends RubyObject {
    private Vertex vertex;

    public VertexHolder(Ruby runtime, Vertex vertex) {
        super(runtime, runtime.getObject());
        this.vertex = vertex;
    }

    public Vertex getVertex() {
        return vertex;
    }

    @Override
    public String toString() {
        return vertex.toString();
    }
}
