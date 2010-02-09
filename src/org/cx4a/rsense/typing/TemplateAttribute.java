package org.cx4a.rsense.typing;

import java.util.Iterator;
import java.util.Arrays;

import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class TemplateAttribute {
    private IRubyObject receiver;
    private IRubyObject[] args;
    private Block block;
    private TypeVarMap receiverTypeVarMap;
    private TypeVarMap[] argTypeVarMaps;

    public TemplateAttribute(IRubyObject[] args) {
        this.args = args;
        this.argTypeVarMaps = new TypeVarMap[args.length];
    }

    public IRubyObject getReceiver() {
        return receiver;
    }

    public IRubyObject getArg(int i) {
        return args[i];
    }

    public IRubyObject[] getArgs() {
        return args;
    }

    public Block getBlock() {
        return block;
    }

    public void setReceiver(IRubyObject receiver) {
        this.receiver = receiver;
    }

    public void setReceiverTypeVarMap(TypeVarMap receiverTypeVarMap) {
        this.receiverTypeVarMap = receiverTypeVarMap;
    }

    public void setArgTypeVarMap(int index, TypeVarMap argTypeVarMap) {
        this.argTypeVarMaps[index] = argTypeVarMap;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public TemplateAttribute clone() {
        TemplateAttribute clone = new TemplateAttribute(args);
        clone.receiver = receiver;
        clone.receiverTypeVarMap = receiverTypeVarMap;
        System.arraycopy(argTypeVarMaps, 0, clone.argTypeVarMaps, 0, argTypeVarMaps.length);
        clone.block = block;
        return clone;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code ^= receiver.getMetaClass().hashCode();
        for (IRubyObject arg : args) {
            code ^= arg.getMetaClass().hashCode();
        }
        code ^= tvmapHashCode(receiverTypeVarMap);
        for (int i = 0; i < argTypeVarMaps.length; i++) {
            code ^= tvmapHashCode(argTypeVarMaps[i]);
        }
        return code;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TemplateAttribute)) {
            return false;
        }

        TemplateAttribute o = (TemplateAttribute) other;

        if (!receiver.getMetaClass().equals(o.receiver.getMetaClass())
            || !tvmapEquals(receiverTypeVarMap, o.receiverTypeVarMap)
            || args.length != o.args.length
            || block != o.block) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (!tvmapEquals(argTypeVarMaps[i], o.argTypeVarMaps[i])
                || !args[i].getMetaClass().equals(o.args[i].getMetaClass())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return Arrays.asList(args).toString();
    }

    private int tvmapHashCode(TypeVarMap tvmap) {
        // FIXME ugly
        if (tvmap == null) {
            return 0;
        }

        int code = 0;
        for (IRubyObject object : tvmap.values()) {
            for (IRubyObject val : ((VertexHolder) object).getVertex().getTypeSet()) {
                code ^= val.getMetaClass().hashCode();
            }
        }
        return code;
    }

    private boolean tvmapEquals(TypeVarMap a, TypeVarMap b) {
        // FIXME ugly
        if ((a == null) ^ (b == null)) {
            return false;
        } else if (a == null) {
            return true;
        }
        for (TypeVariable var : a.keySet()) {
            VertexHolder x = (VertexHolder) a.get(var);
            VertexHolder y = (VertexHolder) b.get(var);
            if ((x == null) ^ (y == null)) {
                return false;
            } else if (x != null) {
                TypeSet m = x.getVertex().getTypeSet();
                TypeSet n = y.getVertex().getTypeSet();
                if (m.size() != n.size()) {
                    return false;
                }

                Iterator<IRubyObject> i = m.iterator();
                Iterator<IRubyObject> j = n.iterator();
                while (i.hasNext() && j.hasNext()) {
                    if (!i.next().getMetaClass().equals(j.next().getMetaClass())) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
