package org.cx4a.rsense.ruby;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.Map;
import java.util.HashMap;

import org.cx4a.rsense.util.SourceLocation;

public class RubyClass extends RubyModule {
    protected RubyClass superClass;

    public static RubyClass newClass(Ruby runtime, String baseName, RubyClass superClass) {
        return newClass(runtime, baseName, superClass, null);
    }

    public static RubyClass newClassWithLocation(Ruby runtime, String baseName, RubyClass superClass, SourceLocation location) {
        return newClassWithLocation(runtime, baseName, superClass, null, location);
    }
    
    public static RubyClass newClass(Ruby runtime, String baseName, RubyClass superClass, RubyModule parent) {
        return newClassWithLocation(runtime, baseName, superClass, parent, null);
    }
    
    public static RubyClass newClassWithLocation(Ruby runtime, String baseName, RubyClass superClass, RubyModule parent, SourceLocation location) {
        if (superClass == null) {
            superClass = runtime.getObject();
        }
        RubyClass klass = new RubyClass(runtime, superClass, parent, location);
        klass.setBaseName(baseName);
        klass.makeMetaClass(superClass.getMetaClass());
        klass.setLocation(location);
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
        this(runtime, runtime.getClassClass(), superClass, parent, null);
    }

    protected RubyClass(Ruby runtime, RubyClass superClass, RubyModule parent, SourceLocation location) {
        this(runtime, runtime.getClassClass(), superClass, parent, location);
    }

    protected RubyClass(Ruby runtime, RubyClass metaClass, RubyClass superClass, RubyModule parent, SourceLocation location) {
        super(runtime, metaClass, parent, location);
        this.superClass = superClass;
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

    @Override
    public IRubyObject getConstant(String name) {
        IRubyObject constant = super.getConstant(name);
        if (constant == null && superClass != null && this != getRuntime().getObject()) {
            constant = superClass.getConstant(name);
        }
        return constant;
    }

    @Override
    public Set<String> getConstants(boolean inheritedToo) {
        Set<String> result = super.getConstants(inheritedToo);
        RubyClass sclass = superClass;
        while (sclass != null) {
            result.addAll(sclass.getConstants(inheritedToo));
            sclass = sclass.getSuperClass();
        }
        return result;
    }

    @Override
    public RubyModule getConstantModule(String name) {
        RubyModule module = super.getConstantModule(name);
        RubyClass sclass = superClass;
        while (module == null && sclass != null) {
            module = sclass.getConstantModule(name);
            sclass = sclass.getSuperClass();
        }
        return module;
    }

    @Override
    public DynamicMethod searchMethod(String name) {
        DynamicMethod method = getMethod(name);
        if (method == null && superClass != null) {
            method = superClass.searchMethod(name);
        }
        if (method == null) {
            method = super.searchMethod(name);
        }
        return method;
    }

    @Override
    public Set<String> getMethods(boolean inheritedToo) {
        Set<String> result = super.getMethods(inheritedToo);
        RubyClass sclass = superClass;
        while (sclass != null) {
            result.addAll(sclass.getPublicMethods(inheritedToo));
            sclass = sclass.getSuperClass();
        }
        return result;
    }

    @Override
    public List<RubyModule> getIncludes(boolean inheritedToo) {
        List<RubyModule> result = super.getIncludes(inheritedToo);
        if (inheritedToo) {
            result = new ArrayList<RubyModule>(result);
            RubyClass sclass = superClass;
            while (sclass != null) {
                result.addAll(sclass.getIncludes(inheritedToo));
                sclass = sclass.getSuperClass();
            }
        }
        return result;
    }
}
