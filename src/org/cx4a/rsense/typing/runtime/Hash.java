package org.cx4a.rsense.typing.runtime;

import java.util.Arrays;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;

import org.jruby.ast.Node;
import org.jruby.ast.StrNode;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.SymbolNode;

public class Hash extends Tuple {
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
    
    private Key[] keys;

    public Hash(Ruby runtime, Key[] keys, IRubyObject[] values) {
        this(runtime, keys, values, true);
    }

    public Hash(Ruby runtime, Key[] keys, IRubyObject[] values, boolean updateTypeVarMap) {
        super(runtime, runtime.getHash(), values, false);
        this.keys = keys;
        if (updateTypeVarMap) {
            updateTypeVarMap();
        }
    }

    public IRubyObject get(Object key) {
        for (int i = 0; i < elements.length; i++) {
            if (key != null && key.equals(keys[i].getRealKey())) {
                return elements[i];
            }
        }
        return runtime.getNil();
    }

    @Override
    protected void updateTypeVarMap() {
        if (elements != null) {
            TypeVariable keyVar = new TypeVariable("k");
            TypeVariable valVar = new TypeVariable("v");
            VertexHolder keyHolder = new VertexHolder(getRuntime(), new Vertex());
            VertexHolder valHolder = new VertexHolder(getRuntime(), new Vertex());
            for (int i = 0; i < elements.length; i++) {
                keyHolder.getVertex().getTypeSet().addAll(keys[i].getTypeSet());
                valHolder.getVertex().addType(elements[i]);
            }
            TypeVarMap typeVarMap = getTypeVarMap();
            typeVarMap.put(keyVar, keyHolder);
            typeVarMap.put(valVar, valHolder);
            typeVarMap.setModified(false);
        }
    }
    
    @Override
    public String toString() {
        if (getTypeVarMap().isModified()) {
            // no longer Hash
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

    public static Hash[] generateHashes(Ruby runtime, Vertex[] args) {
        assert args.length % 2 == 0;
        Key[] keys = new Key[args.length / 2];
        TypeSet[] values = new TypeSet[args.length / 2];
        for (int i = 0; i < args.length; i += 2) {
            keys[i / 2] = createVertexKey(args[i]);
            values[i / 2] = args[i + 1].getTypeSet();
        }

        int size = calculateProductSize(values);
        if (size == 0) {
            return new Hash[0];
        }
        Hash[] result = new Hash[size];
        for (int i = 0; i < size; i++) {
            result[i] = new Hash(runtime, keys, new IRubyObject[values.length], false);
        }
        generateTuples(runtime, values, 0, values.length, result);
        return result;
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
