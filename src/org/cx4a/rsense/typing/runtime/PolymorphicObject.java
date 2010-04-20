package org.cx4a.rsense.typing.runtime;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class PolymorphicObject extends RubyObject {
    // data polymorphic modified?
    private boolean modified = false;
    private long modifiedTime;

    public PolymorphicObject(Ruby runtime) {
        this(runtime, runtime.getObject());
    }

    public PolymorphicObject(Ruby runtime, RubyClass metaClass) {
        this(runtime, metaClass, new TypeVarMap());
    }

    protected PolymorphicObject(Ruby runtime, RubyClass metaClass, TypeVarMap tvmap) {
        super(runtime, metaClass);
        setTag(tvmap);
    }

    public TypeVarMap getTypeVarMap() {
        return (TypeVarMap) getTag();
    }

    public boolean isModified() {
        return modified;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public void setModified(boolean modified) {
        this.modified = modified;
        this.modifiedTime = System.currentTimeMillis();
    }

    public MonomorphicObject[] generateMonomorphicObjects() {
        TypeVarMap[] tvmaps = getTypeVarMap().generateTuples();
        if (tvmaps.length == 0) {
            return new MonomorphicObject[] { new MonomorphicObject(this, getTypeVarMap()) };
        } else {
            MonomorphicObject[] result = new MonomorphicObject[tvmaps.length];
            for (int i = 0; i < tvmaps.length; i++) {
                result[i] = new MonomorphicObject(this, tvmaps[i]);
            }
            return result;
        }
    }

    public PolymorphicObject clone() {
        return new PolymorphicObject(runtime, metaClass, getTypeVarMap().clone());
    }

    @Override
    public int hashCode() {
        return hashCode(1);
    }

    @Override
    public int hashCode(int depth) {
        if (depth > 2)
            // Approximate hash code for nested polymorphic object
            return getMetaClass().hashCode();
        else
            return getMetaClass().hashCode() ^ getTypeVarMap().hashCode(depth + 1);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;

        if (!(other instanceof PolymorphicObject))
            return false;

        PolymorphicObject o = (PolymorphicObject) other;
        return getMetaClass() == o.getMetaClass()
            && getTypeVarMap().equals(o.getTypeVarMap());
    }

    @Override
    public String toString() {
        return "<pobj:" + getMetaClass().toString() + getTypeVarMap() + ">";
    }
}
