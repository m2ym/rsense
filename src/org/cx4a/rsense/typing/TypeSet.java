package org.cx4a.rsense.typing;

import java.util.HashSet;

import org.cx4a.rsense.ruby.IRubyObject;

public class TypeSet extends HashSet<IRubyObject> {
    private static final long serialVersionUID = 0L;

    public static final TypeSet EMPTY = new TypeSet();
}
