package org.cx4a.rsense.typing;

import java.util.Collection;
import java.util.HashSet;

import org.cx4a.rsense.ruby.IRubyObject;

public class TypeSet extends HashSet<IRubyObject> {
    private static final long serialVersionUID = 0L;

    public static final TypeSet EMPTY = new TypeSet() {
            private static final long serialVersionUID = 0L;
            @Override
            public boolean add(IRubyObject type) {
                throw new UnsupportedOperationException();
            }
            @Override
            public boolean addAll(Collection<? extends IRubyObject> types) {
                throw new UnsupportedOperationException();
            }
        };

    public TypeSet() {
        super();
    }

    public TypeSet(TypeSet other) {
        super(other);
    }

    public TypeSet(int capacity) {
        super(capacity);
    }

    @Override
    public int hashCode() {
        return hashCode(1);
    }

    public int hashCode(int depth) {
        int code = 0;
        for (IRubyObject type : this) {
            code ^= type.hashCode(depth);
            code *= 13;
        }
        return code;
    }
}
