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
        TypeVariable[] keys = keySet().toArray(new TypeVariable[0]);
        Vertex[] values = values().toArray(new Vertex[0]);

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
                Vertex vertex = new Vertex();
                vertex.addType(v);
                result[j].put(keys[i], vertex);
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
}
