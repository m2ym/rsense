package org.cx4a.rsense.typing.runtime;

import java.util.Map;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Collections;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class TypeVarMap extends HashMap<TypeVariable, Vertex> {
    private static final long serialVersionUID = 0L;

    public TypeVarMap() {
        super();
    }

    public TypeVarMap(int initialCapacity) {
        super(initialCapacity);
    }

    public TypeVarMap(TypeVarMap other) {
        super(other);
    }

    public TypeVarMap[] generateTuples() {
        int size = 1;
        for (Vertex vertex : values()) {
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

        for (Map.Entry<TypeVariable, Vertex> entry : entrySet()) {
            TypeSet typeSet = entry.getValue().getTypeSet();
            Iterator<IRubyObject> ite = typeSet.iterator();
            int k = 0, n = typeSet.size();
            int newUnit = unit / n;
            IRubyObject v = ite.next();
            for (int j = 0; j < size; j++) {
                Vertex vertex = new Vertex(1);
                vertex.addType(v);
                result[j].put(entry.getKey(), vertex);
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

    public TypeVarMap clone() {
        TypeVarMap clone = new TypeVarMap();
        for (Map.Entry<TypeVariable, Vertex> entry : entrySet()) {
            clone.put(entry.getKey(), new Vertex(null, new TypeSet(entry.getValue().getTypeSet())));
        }
        return clone;
    }

    @Override
    public int hashCode() {
        return hashCode(1);
    }

    public int hashCode(int depth) {
        int code = 0;
        for (Map.Entry<TypeVariable, Vertex> entry : entrySet()) {
            code = (code ^ entry.getKey().hashCode()) * 13;
            code = (code ^ entry.getValue().getTypeSet().hashCode(depth)) * 13;
        }
        return code;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof TypeVarMap))
            return false;

        TypeVarMap o = (TypeVarMap) other;
        if (size() != o.size())
            return false;

        Iterator<Map.Entry<TypeVariable, Vertex>> i = entrySet().iterator();
        Iterator<Map.Entry<TypeVariable, Vertex>> j = o.entrySet().iterator();
        while (i.hasNext() && j.hasNext()) {
            Map.Entry<TypeVariable, Vertex> x = i.next();
            Map.Entry<TypeVariable, Vertex> y = j.next();
            if (!x.getKey().equals(y.getKey())
                || !x.getValue().getTypeSet().equals(y.getValue().getTypeSet()))
                return false;
        }

        return true;
    }
}
