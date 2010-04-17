package org.cx4a.rsense.typing.runtime;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.TypeVarVertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class Array extends PolymorphicObject {
    protected Vertex[] elements;

    public Array(Ruby runtime, Vertex[] elements) {
        this(runtime, runtime.getArray(), elements);
    }

    public Array(Ruby runtime, RubyClass metaClass, Vertex[] elements) {
        super(runtime, metaClass);
        this.elements = elements;
    }

    public Array(Ruby runtime, RubyClass metaClass, Vertex[] elements, TypeVarMap tvmap) {
        super(runtime, metaClass, tvmap);
        this.elements = elements;
    }

    public Vertex getTypeVarVertex() {
        return getTypeVarMap().get(TypeVariable.valueOf("t"));
    }

    public void setTypeVarVertex(Vertex vertex) {
        getTypeVarMap().put(TypeVariable.valueOf("t"), vertex);
        if (elements != null) {
            for (Vertex e : elements) {
                vertex.update(e);
            }
        }
    }

    public Vertex getElement(int i) {
        if (elements == null) {
            return null;
        }
        if (i < 0) {
            i += elements.length;
        }
        if (0 <= i && i < elements.length) {
            return elements[i];
        }
        return null;
    }

    public Vertex[] getElements() {
        return elements;
    }

    public int length() {
        return elements != null ? elements.length : 0;
    }

    @Override
    public PolymorphicObject clone() {
        return new Array(runtime, metaClass, elements, getTypeVarMap().clone());
    }

    @Override
    public String toString() {
        if (isModified()) {
            // no longer static array
            return super.toString();
        }

        StringBuffer sb = new StringBuffer();
        String delim = "";
        sb.append('(');
        if (elements != null) {
            for (Vertex e : elements) {
                sb.append(delim);
                sb.append(e.toString());
                delim = ", ";
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
