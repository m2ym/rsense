package org.cx4a.rsense.typing.runtime;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class TypeVarMap extends HashMap<TypeVariable, IRubyObject> {
    private static final long serialVersionUID = 0L;

    private boolean modified = false;

    public TypeVarMap() {
        super();
    }

    public TypeVarMap(int initialCapacity) {
        super(initialCapacity);
    }

    public TypeVarMap(TypeVarMap other) {
        super(other);
    }

    public boolean isModified() {
        return modified;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
    }

    public TypeVarMap[] generateTuples() {
        TypeVariable[] keys = keySet().toArray(new TypeVariable[0]);
        Vertex[] values = new Vertex[keys.length];
        for (int i = 0; i < keys.length; i++) {
            values[i] = ((VertexHolder) get(keys[i])).getVertex();
        }

        int size = 1;
        for (Vertex vertex : values) {
            size *= vertex.getTypeSet().size();
        }
        if (size == 0) {
            return new TypeVarMap[0];
        }

        int unit = size;
        TypeVarMap[] result = new TypeVarMap[size];
        for (int i = 0; i < size; i++) {
            result[i] = new TypeVarMap(size());
        }

        for (int i = 0; i < values.length; i++) {
            TypeSet typeSet = values[i].getTypeSet();
            Iterator<IRubyObject> ite = typeSet.iterator();
            int k = 0, n = typeSet.size();
            int newUnit = unit / n;
            IRubyObject v = ite.next();
            for (int j = 0; j < size; j++) {
                result[j].put(keys[i], v);
                if (++k == newUnit) {
                    k = 0;
                    if (!ite.hasNext()) {
                        ite = typeSet.iterator();
                    }
                    v = ite.next();
                }
            }
            unit = newUnit;
        }

        return result;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        for (IRubyObject value : values()) {
            hashCode ^= value.hashCode();
        }
        return hashCode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TypeVarMap)) {
            return false;
        }

        TypeVarMap o = (TypeVarMap) other;

        if (size() != o.size()) {
            return false;
        }

        for (TypeVariable key : keySet()) {
            if (!get(key).equals(o.get(key))) {
                return false;
            }
        }

        return true;
    }
}
