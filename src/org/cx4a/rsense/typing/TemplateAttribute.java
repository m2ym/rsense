package org.cx4a.rsense.typing;

import java.util.Iterator;
import java.util.Arrays;

import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.PolymorphicObject;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class TemplateAttribute {
    private IRubyObject receiver;
    private IRubyObject[] args;
    private Block block;
    private int hashCode = 0;

    // For to keep original state of receiver and args
    // See AnnotationResolver
    private IRubyObject mutableReceiver;
    private IRubyObject[] mutableArgs;

    public TemplateAttribute(IRubyObject[] args) {
        this.args = args;
    }

    public IRubyObject getReceiver() {
        return receiver;
    }

    public void setReceiver(IRubyObject receiver) {
        this.receiver = receiver;
    }

    public IRubyObject getArg(int i) {
        return args[i];
    }

    public void setArg(int i, IRubyObject arg) {
        args[i] = arg;
    }

    public IRubyObject[] getArgs() {
        return args;
    }

    public Block getBlock() {
        return block;
    }

    public void setBlock(Block block) {
        this.block = block;
    }

    public IRubyObject getMutableReceiver() {
        return getMutableReceiver(true);
    }
    
    public IRubyObject getMutableReceiver(boolean create) {
        if (mutableReceiver == null && create) {
            mutableReceiver = cloneObject(receiver);
        }
        return mutableReceiver;
    }

    public IRubyObject[] getMutableArgs() {
        return getMutableArgs(true);
    }

    public IRubyObject[] getMutableArgs(boolean create) {
        if (mutableArgs == null && create) {
            mutableArgs = new IRubyObject[args.length];
            for (int i = 0; i < args.length; i++) {
                mutableArgs[i] = cloneObject(args[i]);
            }
        }
        return mutableArgs;
    }

    public IRubyObject getMutableArg(int i) {
        return getMutableArg(i, true);
    }

    public IRubyObject getMutableArg(int i, boolean create) {
        IRubyObject[] args = getMutableArgs(create);
        return args != null ? args[i] : null;
    }

    public TemplateAttribute clone() {
        IRubyObject[] newargs = new IRubyObject[args.length];
        System.arraycopy(args, 0, newargs, 0, args.length);
        TemplateAttribute clone = new TemplateAttribute(newargs);
        clone.receiver = receiver;
        clone.block = block;
        return clone;
    }

    private IRubyObject cloneObject(IRubyObject object) {
        if (object instanceof PolymorphicObject) {
            return ((PolymorphicObject) object).clone();
        } else {
            // immutable
            return object;
        }
    }

    @Override
    public int hashCode() {
        if (hashCode != 0)
            return hashCode;
        
        if (receiver != null)
            hashCode = (hashCode ^ receiver.hashCode()) * 13;
        for (IRubyObject arg : args) {
            if (arg != null)
                hashCode = (hashCode ^ arg.hashCode()) * 13;
        }
        if (block != null)
            hashCode ^= (hashCode ^ block.hashCode()) * 13;
        return hashCode;
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

        if (hashCode() != other.hashCode()
            || ((receiver == null) ^ (o.receiver == null))
            || (receiver != null && !receiver.equals(o.receiver))
            || args.length != o.args.length
            || (block != null
                ? !block.equals(o.block)
                : o.block != null
                ? !o.block.equals(block)
                : false)) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (((args[i] == null) ^ (o.args[i] == null))
                || (args[i] != null && !args[i].equals(o.args[i]))) {
                return false;
            }
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "<TemplAttr " + receiver.toString() + " " + Arrays.asList(args).toString() + (block != null ? (" " + block.toString() + ">") : ">");
    }
}
