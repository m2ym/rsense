package org.cx4a.rsense.typing.runtime;

import java.util.Map;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class MonomorphicObject extends PolymorphicObject {
    private PolymorphicObject entity;
    
    // keep initial state of monomorphic
    private TypeVarMap fixedTypeVarMap;
    private int hashCode = 0;

    public MonomorphicObject(PolymorphicObject entity, TypeVarMap tvmap) {
        super(entity.getRuntime(), entity.getMetaClass(), tvmap);
        this.entity = entity;

        fixedTypeVarMap = new TypeVarMap();
        TypeVarMap etvmap = entity.getTypeVarMap();
        for (Map.Entry<TypeVariable, Vertex> entry : tvmap.entrySet()) {
            entry.getValue().addEdge(etvmap.get(entry.getKey()));
            
            IRubyObject value = entry.getValue().singleType();
            if (value != null) {
                Vertex v = new Vertex();
                v.addType(value);
                fixedTypeVarMap.put(entry.getKey(), v);
            }
        }
    }

    @Override
    public PolymorphicObject clone() {
        return new MonomorphicObject(entity, getTypeVarMap().clone());
    }

    @Override
    public int hashCode() {
        if (hashCode != 0)
            return hashCode;

        for (Vertex v : fixedTypeVarMap.values())
            hashCode = (hashCode ^ v.singleType().getMetaClass().hashCode()) * 13;
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof MonomorphicObject)) {
            return false;
        }

        MonomorphicObject o = (MonomorphicObject) other;
        for (TypeVariable var : fixedTypeVarMap.keySet()) {
            IRubyObject a = fixedTypeVarMap.get(var).singleType();
            IRubyObject b = o.fixedTypeVarMap.get(var) != null ? o.fixedTypeVarMap.get(var).singleType() : null;
            if (a == null
                || b == null
                || !a.getMetaClass().equals(b.getMetaClass())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return "<mobj:" + getMetaClass().toString() + getTypeVarMap() + ">";
    }
}
