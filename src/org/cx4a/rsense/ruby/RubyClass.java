package org.cx4a.rsense.ruby;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

public class RubyClass extends RubyModule {
    protected RubyClass superClass;
    protected Map<String, IRubyObject> classVars;

    public static RubyClass newClass(Ruby runtime, String baseName, RubyClass superClass) {
        return newClass(runtime, baseName, superClass, null);
    }

    public static RubyClass newClass(Ruby runtime, String baseName, RubyClass superClass, RubyModule parent) {
        if (superClass == null) {
            superClass = runtime.getObject();
        }
        RubyClass klass = new RubyClass(runtime, superClass, parent);
        klass.setBaseName(baseName);
        klass.makeMetaClass(superClass.getMetaClass());
        return klass;
    }

    public static RubyClass newBootClass(Ruby runtime, String baseName, RubyClass superClass) {
        RubyClass klass = new RubyClass(runtime, superClass);
        klass.setBaseName(baseName);
        return klass;
    }

    protected RubyClass(Ruby runtime, RubyClass superClass) {
        this(runtime, superClass, null);
    }
    
    protected RubyClass(Ruby runtime, RubyClass superClass, RubyModule parent) {
        this(runtime, runtime.getClassClass(), superClass, parent);
    }

    protected RubyClass(Ruby runtime, RubyClass metaClass, RubyClass superClass, RubyModule parent) {
        super(runtime, metaClass, parent);
        this.superClass = superClass;
        this.classVars = new HashMap<String, IRubyObject>();
    }

    public RubyClass getRealClass() {
        return this;
    }
    
    public RubyClass getSuperClass() {
        return superClass;
    }

    public void setSuperClass(RubyClass superClass) {
        this.superClass = superClass;
    }

    public boolean isSingleton() {
        return false;
    }

    public void addSubclass(RubyClass subclass) {
        // FIXME
    }

    public IRubyObject getClassVar(String name) {
        return classVars.get(name);
    }

    public void setClassVar(String name, IRubyObject value) {
        classVars.put(name, value);
    }

    @Override
    public IRubyObject getConstant(String name) {
        IRubyObject constant = super.getConstant(name);
        if (constant == null && superClass != null && this != getRuntime().getObject()) {
            constant = superClass.getConstant(name);
        }
        return constant;
    }

    @Override
    public DynamicMethod searchMethod(String name) {
        DynamicMethod method = super.searchMethod(name);
        if (method == null && superClass != null) {
            method = superClass.searchMethod(name);
        }
        return method;
    }

    @Override
    public Set<String> getMethods(boolean inheritedToo) {
        Set<String> result = super.getMethods(inheritedToo);
        RubyClass sclass = superClass;
        while (sclass != null) {
            result.addAll(sclass.getMethods(inheritedToo));
            sclass = sclass.getSuperClass();
        }
        return result;
    }
}
