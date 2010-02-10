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

    public TemplateAttribute clone() {
        IRubyObject[] newargs = new IRubyObject[args.length];
        System.arraycopy(args, 0, newargs, 0, args.length);
        TemplateAttribute clone = new TemplateAttribute(newargs);
        clone.receiver = receiver;
        clone.block = block;
        return clone;
    }

    @Override
    public int hashCode() {
        int code = 0;
        code ^= objectHashCode(receiver);
        for (IRubyObject arg : args) {
            code ^= objectHashCode(arg);
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

        if (!objectEquals(receiver, o.receiver)
            || args.length != o.args.length
            || block != o.block) {
            return false;
        }

        for (int i = 0; i < args.length; i++) {
            if (!objectEquals(args[i], o.args[i])) {
                return false;
            }
        }
        
        return true;
    }

    private int objectHashCode(IRubyObject object) {
        if (object instanceof PolymorphicObject) {
            return object.hashCode();
        } else {
            return object.getMetaClass().hashCode();
        }
    }

    private boolean objectEquals(IRubyObject a, IRubyObject b) {
        if (a instanceof PolymorphicObject) {
            return a.equals(b);
        } else if (b instanceof PolymorphicObject) {
            return b.equals(a);
        } else {
            return a.getMetaClass().equals(b.getMetaClass());
        }
    }

    @Override
    public String toString() {
        return "<TemplAttr " + receiver.toString() + " " + Arrays.asList(args).toString() + ">";
    }
}
