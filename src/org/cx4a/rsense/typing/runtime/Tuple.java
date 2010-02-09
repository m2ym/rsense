package org.cx4a.rsense.typing.runtime;

import java.util.Iterator;
import java.util.Arrays;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.typing.annotation.ClassType;

public class Tuple extends PolymorphicObject {
    protected IRubyObject[] elements;

    public Tuple(Ruby runtime) {
        this(runtime, null);
    }

    public Tuple(Ruby runtime, IRubyObject[] elements) {
        this(runtime, elements, true);
    }

    protected Tuple(Ruby runtime, IRubyObject[] elements, boolean updateTypeVarMap) {
        this(runtime, runtime.getArray(), elements, updateTypeVarMap);
    }

    protected Tuple(Ruby runtime, RubyClass metaClass, IRubyObject[] elements, boolean updateTypeVarMap) {
        super(runtime, metaClass);
        this.elements = elements;
        if (updateTypeVarMap) {
            updateTypeVarMap();
        }
    }
    
    public IRubyObject get(int index) {
        return elements[index];
    }

    public IRubyObject safeGet(int index) {
        if (elements != null) {
            if (index < 0 && (elements.length + index) >= 0) {
                return elements[elements.length + index];
            } else if (0 <= index && index < elements.length) {
                return elements[index];
            }
        }
        return runtime.getNil();
    }

    public int getLength() {
        return elements == null ? 0 : elements.length;
    }

    public IRubyObject[] getElements() {
        return elements;
    }

    public Tuple subTuple(int index, int length) {
        return newTuple(getRuntime(), elements, index, length);
    }

    protected void updateTypeVarMap() {
        if (elements != null) {
            TypeVariable var = new TypeVariable("t");
            VertexHolder holder = new VertexHolder(getRuntime(), new Vertex());
            holder.getVertex().getTypeSet().addAll(Arrays.asList(elements));
            TypeVarMap typeVarMap = getTypeVarMap();
            typeVarMap.put(var, holder);
            typeVarMap.setModified(false);
        }
    }

    @Override
    public String toString() {
        if (getTypeVarMap().isModified()) {
            // no longer Tuple
            return super.toString();
        }

        StringBuffer sb = new StringBuffer();
        String delim = "";
        sb.append('(');
        if (elements != null) {
            for (IRubyObject e : elements) {
                sb.append(delim);
                sb.append(e.toString());
                delim = ", ";
            }
        }
        sb.append(')');
        return sb.toString();
    }

    public static Tuple newTuple(Ruby runtime, IRubyObject[] elements, int index, int length) {
        IRubyObject[] subseq = new IRubyObject[length];
        System.arraycopy(elements, index, subseq, 0, length);
        return new Tuple(runtime, subseq);
    }

    public static int calculateProductSize(TypeSet[] args) {
        int size = 1;
        for (TypeSet arg : args) {
            size *= arg.size();
        }
        return size;
    }

    public static Tuple[] generateTuples(Ruby runtime, Vertex[] args) {
        return generateTuples(runtime, args, 0, args.length);
    }

    public static Tuple[] generateTuples(Ruby runtime, Vertex[] args, int offset, int length) {
        TypeSet[] newArgs = new TypeSet[args.length];
        for (int i = 0; i < newArgs.length; i++) {
            newArgs[i] = args[i].getTypeSet();
        }
        return generateTuples(runtime, newArgs, offset, length);
    }
    
    public static Tuple[] generateTuples(Ruby runtime, TypeSet[] args) {
        return generateTuples(runtime, args, 0, args.length);
    }
    

    public static Tuple[] generateTuples(Ruby runtime, TypeSet[] args, int offset, int length) {
        int size = calculateProductSize(args);
        if (size == 0) {
            return new Tuple[0];
        }
        Tuple[] result = new Tuple[size];
        for (int i = 0; i < size; i++) {
            result[i] = new Tuple(runtime, new IRubyObject[args.length], false);
        }
        return generateTuples(runtime, args, offset, length, result);
    }

    public static Tuple[] generateTuples(Ruby runtime, TypeSet[] args, int offset, int length, Tuple[] result) {
        int size = result.length;
        int unit = size;

        for (int i = offset; i < offset + length; i++) {
            TypeSet c = args[i];
            Iterator<IRubyObject> ite = c.iterator();
            int k = 0, n = c.size();
            int newUnit = unit / n;
            IRubyObject v = ite.next();
            for (int j = 0; j < size; j++) {
                result[j].getElements()[i - offset] = v;
                if (++k == newUnit) {
                    k = 0;
                    if (!ite.hasNext()) {
                        ite = c.iterator();
                    }
                    v = ite.next();
                }
            }
            unit = newUnit;
        }

        for (Tuple tuple : result) {
            tuple.updateTypeVarMap();
        }

        return result;
    }
}
