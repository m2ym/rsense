package org.cx4a.rsense.typing.runtime;

import java.util.Arrays;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;

import org.jruby.ast.Node;
import org.jruby.ast.StrNode;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.SymbolNode;

public class Hash extends Array {
    public static class Key {
        private Object realKey;
        private TypeSet typeSet;

        public Key(Object realKey, TypeSet typeSet) {
            this.realKey = realKey;
            this.typeSet = typeSet;
        }

        public Object getRealKey() {
            return realKey;
        }

        public TypeSet getTypeSet() {
            return typeSet;
        }
    }
    
    protected Key[] keys;

    public Hash(Ruby runtime, Vertex[] elements) {
        this(runtime, runtime.getHash(), elements);
    }

    public Hash(Ruby runtime, RubyClass metaClass, Vertex[] elements) {
        this(runtime, runtime.getHash(), elements, new TypeVarMap());
    }

    public Hash(Ruby runtime, RubyClass metaClass, Vertex[] elements, TypeVarMap tvmap) {
        super(runtime, metaClass, elements, tvmap);
        if (elements != null) {
            keys = new Key[elements.length / 2];
            for (int i = 0, j = 0; i < elements.length; i += 2, j++) {
                keys[j] = createVertexKey(elements[i]);
            }
        }
    }

    public Vertex get(Object key) {
        if (elements != null) {
            for (int i = 1, j = 0; i < elements.length; i += 2, j++) {
                if (key != null && key.equals(keys[j].getRealKey())) {
                    return elements[i];
                }
            }
        }
        return null;
    }

    public Vertex getKeyTypeVarVertex() {
        return getTypeVarMap().get(TypeVariable.valueOf("k"));
    }

    public void setKeyTypeVarVertex(Vertex vertex) {
        getTypeVarMap().put(TypeVariable.valueOf("k"), vertex);
        for (Key key : keys) {
            vertex.addTypes(key.getTypeSet());
        }
    }

    public Vertex getValueTypeVarVertex() {
        return getTypeVarMap().get(TypeVariable.valueOf("v"));
    }

    public void setValueTypeVarVertex(Vertex vertex) {
        getTypeVarMap().put(TypeVariable.valueOf("v"), vertex);
        if (elements != null) {
            for (int i = 1; i < elements.length; i += 2) {
                vertex.update(elements[i]);
            }
        }
    }

    @Override
    public PolymorphicObject clone() {
        return new Hash(runtime, metaClass, elements, getTypeVarMap().clone());
    }

    @Override
    public String toString() {
        if (isModified()) {
            // no longer static hash
            return super.toString();
        }

        StringBuffer sb = new StringBuffer();
        String delim = "";
        sb.append('{');
        if (elements != null) {
            for (int i = 0; i < elements.length; i++) {
                sb.append(delim);
                sb.append(keys[i].getRealKey());
                sb.append(" => ");
                sb.append(elements[i]);
                delim = ", ";
            }
        }
        sb.append('}');
        return sb.toString();
    }

    public static Object getRealKey(Node node) {
        Object realKey = null;
        if (node instanceof StrNode) {
            realKey = ((StrNode) node).getValue();
        } else if (node instanceof FixnumNode) {
            realKey = ((FixnumNode) node).getValue();
        } else if (node instanceof SymbolNode) {
            realKey = ":" + ((SymbolNode) node).getName();
        }
        return realKey;
    }

    public static Key createVertexKey(Vertex vertex) {
        return new Key(getRealKey(vertex.getNode()), vertex.getTypeSet());
    }
}
