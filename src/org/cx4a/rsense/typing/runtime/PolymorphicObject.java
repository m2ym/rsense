package org.cx4a.rsense.typing.runtime;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyObject;

public class PolymorphicObject extends RubyObject {
    public PolymorphicObject(Ruby runtime) {
        this(runtime, runtime.getObject());
    }

    public PolymorphicObject(Ruby runtime, RubyClass metaClass) {
        super(runtime, metaClass);
        setTag(new TypeVarMap());
    }

    public TypeVarMap getTypeVarMap() {
        return (TypeVarMap) getTag();
    }

    @Override
    public String toString() {
        return "<pobj:" + getMetaClass().toString() + getTag() + ">";
    }
}
