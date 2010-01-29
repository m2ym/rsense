package org.cx4a.rsense.typing;

import java.util.Arrays;

import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.runtime.TypeVarMap;

public class TemplateAttribute {
    private IRubyObject receiver;
    private TypeVarMap typeVarMap;
    private IRubyObject[] args;
    private Block block;

    public TemplateAttribute(IRubyObject[] args) {
        this.args = args;
    }

    public IRubyObject getReceiver() {
        return receiver;
    }

    public TypeVarMap getTypeVarMap() {
        return typeVarMap;
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

    public void setTypeVarMap(TypeVarMap typeVarMap) {
        this.typeVarMap = typeVarMap;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code ^= receiver.getMetaClass().hashCode();
        if (typeVarMap != null) {
            code ^= typeVarMap.hashCode();
        }
        for (IRubyObject arg : args) {
            code ^= arg.hashCode();
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
            || (typeVarMap != null && !typeVarMap.equals(o.typeVarMap))
            || args.length != o.args.length) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (!args[i].equals(o.args[i])) {
                return false;
            }
        }

        return true;
    }

    @Override
    public String toString() {
        return Arrays.asList(args).toString();
    }
}
