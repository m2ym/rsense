package org.cx4a.rsense.ruby;

import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;

import org.cx4a.rsense.util.Logger;
import org.cx4a.rsense.util.SourceLocation;

public class RubyModule extends RubyObject {
    private String baseName;
    private RubyModule parent;
    private Map<String, DynamicMethod> methods;
    private Map<String, IRubyObject> constants;
    private Map<String, IRubyObject> classVars;
    private List<RubyModule> includes;
    private SourceLocation location;

    public static RubyModule newModule(Ruby runtime, String baseName, RubyModule parent) {
        return newModuleWithLocation(runtime, baseName, parent, null);
    }
    
    public static RubyModule newModuleWithLocation(Ruby runtime, String baseName, RubyModule parent, SourceLocation location) {
        RubyModule module = new RubyModule(runtime, parent);
        module.setBaseName(baseName);
        module.makeMetaClass(runtime.getModule());
        return module;
    }

    protected RubyModule(Ruby runtime) {
        this(runtime, null);
    }
    
    protected RubyModule(Ruby runtime, RubyModule parent) {
        this(runtime, runtime.getModule(), parent);
    }
    
    protected RubyModule(Ruby runtime, RubyClass metaClass, RubyModule parent) {
        this(runtime, runtime.getModule(), parent, null);
    }

    protected RubyModule(Ruby runtime, RubyClass metaClass, RubyModule parent, SourceLocation location) {
        super(runtime, metaClass);
        this.parent = parent;
        this.methods = new HashMap<String, DynamicMethod>();
        this.constants = new HashMap<String, IRubyObject>();
        this.classVars = new HashMap<String, IRubyObject>();
        this.includes = new ArrayList<RubyModule>();
        this.location = location;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public RubyModule getParent() {
        return parent;
    }

    public void setConstant(String name, IRubyObject constant) {
        constants.put(name, constant);
    }

    public IRubyObject getConstant(String name) {
        return getConstant(name, null);
    }

    private IRubyObject getConstant(String name, Set<RubyModule> seen) {
        if (seen != null && seen.contains(this)) {
            return null;
        }

        IRubyObject constant = getConstantUnder(name);
        if (constant == null && parent != null) {
            constant = parent.getConstant(name, seen);
        }
        if (constant == null) {
            if (seen == null) {
                seen = new HashSet<RubyModule>();
            }
            seen.add(this);
            for (RubyModule module : includes) {
                constant = module.getConstant(name, seen);
                if (constant != null) {
                    return constant;
                }
            }
        }
        return constant;
    }

    public IRubyObject getConstantUnder(String name) {
        return constants.get(name);
    }

    public Set<String> getConstants(boolean inheritedToo) {
        Set<String> result = new HashSet<String>(constants.keySet());
        if (inheritedToo) {
            for (RubyModule module : includes) {
                result.addAll(module.getConstants(inheritedToo));
            }
        }
        return result;
    }

    public RubyModule getConstantModule(String name) {
        // FIXME return pairs at getConstants
        if (constants.containsKey(name)) {
            return this;
        } else {
            for (RubyModule module : includes) {
                module = module.getConstantModule(name);
                if (module != null) {
                    return module;
                }
            }
        }
        return null;
    }

    public boolean isConstantDefined(String name) {
        return constants.containsKey(name);
    }

    public IRubyObject getClassVar(String name) {
        return classVars.get(name);
    }

    public void setClassVar(String name, IRubyObject value) {
        classVars.put(name, value);
    }

    public RubyModule defineOrGetClassUnder(String name, RubyClass superClass, SourceLocation location) {
        if (isConstantDefined(name)) {
            IRubyObject object = getConstant(name);
            if (object instanceof RubyModule) {
                return (RubyModule) object;
            } else {
                Logger.error("%s is not class", name);
                return null;
            }
        } else {
            RubyClass klass = RubyClass.newClassWithLocation(getRuntime(), name, superClass, this, location);
            setConstant(name, klass);
            return klass;
        }
    }

    public RubyModule defineOrGetModuleUnder(String name, SourceLocation location) {
        if (isConstantDefined(name)) {
            IRubyObject object = getConstant(name);
            if (object.getClass() == RubyModule.class) {
                return (RubyModule) object;
            } else {
                Logger.error("%s is not module", name);
                return null;
            }
        } else {
            RubyModule module = RubyModule.newModuleWithLocation(getRuntime(), name, this, location);
            setConstant(name, module);
            return module;
        }
    }

    public void addMethod(String name, DynamicMethod method) {
        methods.put(name, method);
    }

    public DynamicMethod getMethod(String name) {
        return methods.get(name);
    }

    public Set<String> getMethods(boolean inheritedToo) {
        Set<String> result = new HashSet<String>(methods.keySet());
        if (inheritedToo) {
            for (RubyModule module : includes) {
                result.addAll(module.getPublicMethods(inheritedToo));
            }
        }
        return result;
    }

    public Set<String> getPublicMethods(boolean inheritedToo) {
        Set<String> result = getVisibleMethods(Visibility.PUBLIC);
        if (inheritedToo) {
            for (RubyModule module : includes) {
                result.addAll(module.getPublicMethods(inheritedToo));
            }
        }
        return result;
    }

    private Set<String> getVisibleMethods(Visibility visibility) {
        Set<String> result = new HashSet<String>();
        for (Map.Entry<String, DynamicMethod> entry : methods.entrySet()) {
            if (entry.getValue().getVisibility() == visibility) {
                result.add(entry.getKey());
            }
        }
        return result;
    }

    public DynamicMethod searchMethod(String name) {
        DynamicMethod method = getMethod(name);
        if (method == null) {
            for (RubyModule module : includes) {
                if ((method = module.searchMethod(name)) != null) {
                    break;
                }
            }
        }
        return method;
    }

    public void includeModule(RubyModule module) {
        includes.add(module);
    }

    public List<RubyModule> getIncludes(boolean inheritedToo) {
        return includes;
    }

    public void setLocation(SourceLocation location) {
        this.location = location;
    }

    public SourceLocation getLocation() {
        return location;
    }

    public String getMethodPath(String name) {
        String path = toString();
        if (name != null)
            path += "#" + name;
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (parent != null && parent != getRuntime().getObject()) {
            sb.append(parent.toString());
            sb.append("::");
        }
        sb.append(baseName != null ? baseName : super.toString());
        return sb.toString();
    }
}
