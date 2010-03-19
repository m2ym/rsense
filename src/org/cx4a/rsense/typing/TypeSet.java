package org.cx4a.rsense.typing;

import java.util.Collection;
import java.util.HashSet;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.util.Logger;

public class TypeSet extends HashSet<IRubyObject> {
    private static final long serialVersionUID = 0L;
    public static final int MEGAMORPHIC_THRESHOLD = 5;
    public static final TypeSet EMPTY = new TypeSet();

    private boolean megamorphic;

    public TypeSet() {
        super();
    }

    public TypeSet(TypeSet other) {
        super(other);
    }

    public boolean add(IRubyObject e) {
        if (megamorphic) return false;

        if (size() >= MEGAMORPHIC_THRESHOLD) {
            megamorphic = true;
            // FIXME simple way
            Logger.warn("megamorphic detected");
            clear();
            return super.add(new RubyObject(e.getRuntime(), e.getRuntime().getObject()));
        } else {
            return super.add(e);
        }
    }

    public boolean addAll(Collection<? extends IRubyObject> c) {
        boolean changed = false;
        for (IRubyObject e : c) {
            if (add(e)) {
                changed = true;
            }
        }
        return changed;
    }

    public boolean isMegamorphic() {
        return megamorphic;
    }
}
