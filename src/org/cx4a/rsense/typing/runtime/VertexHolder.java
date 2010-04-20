package org.cx4a.rsense.typing.runtime;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.IRubyObject;
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

    @Override
    public int hashCode() {
        int code = 0;
        for (IRubyObject type : vertex.getTypeSet()) {
            code ^= type.hashCode();
            code *= 13;
        }
        return code;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof VertexHolder)) {
            return false;
        }

        VertexHolder o = (VertexHolder) object;
        return vertex.getTypeSet().equals(o.vertex.getTypeSet());
    }
}
