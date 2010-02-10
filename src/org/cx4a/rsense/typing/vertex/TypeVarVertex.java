package org.cx4a.rsense.typing.vertex;

import org.cx4a.rsense.typing.runtime.PolymorphicObject;
import org.cx4a.rsense.typing.Propagation;

public class TypeVarVertex extends Vertex {
    private PolymorphicObject object;

    public TypeVarVertex(PolymorphicObject object) {
        super();
        this.object = object;
    }

    public PolymorphicObject getObject() {
        return object;
    }

    @Override
    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateTypeVarVertex(propagation, this, src);
    }
}
