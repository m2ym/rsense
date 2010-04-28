package org.cx4a.rsense.ruby;

import java.util.Map;
import java.util.HashMap;

public class Ruby {
    public static interface ObjectAllocator {
        public IRubyObject allocate(Ruby runtime, RubyClass klass);
    }

    public static class DefaultObjectAllocator implements ObjectAllocator {
        public IRubyObject allocate(Ruby runtime, RubyClass klass) {
            return new RubyObject(runtime, klass);
        }
    }

    private Context context;
    private ObjectAllocator allocator;
    private RubyModule kernelModule;
    private RubyClass objectClass, moduleClass, classClass,
        numericClass, integerClass, fixnumClass, bignumClass,
        floatClass, stringClass, symbolClass,
        booleanClass, trueClass, falseClass, nilClass,
        arrayClass, hashClass, rangeClass,
        regexpClass, matchDataClass,
        exceptionClass, fatalClass,
        procClass;
    private IRubyObject nilObject, trueObject, falseObject;
    private IRubyObject topSelf;
    private Map<String, IRubyObject> globalVars;

    public Ruby() {
        allocator = new DefaultObjectAllocator();

        objectClass = RubyClass.newBootClass(this, "Object", null);
        moduleClass = RubyClass.newBootClass(this, "Module", objectClass);
        classClass = RubyClass.newBootClass(this, "Class", moduleClass);

        objectClass.setMetaClass(classClass);
        moduleClass.setMetaClass(classClass);
        classClass.setMetaClass(classClass);

        RubyClass metaClass;
        metaClass = objectClass.makeMetaClass(classClass);
        metaClass = moduleClass.makeMetaClass(metaClass);
        metaClass = classClass.makeMetaClass(metaClass);

        kernelModule = RubyModule.newModule(this, "Kernel", null);
        objectClass.includeModule(kernelModule);
        numericClass = RubyClass.newClass(this, "Numeric", objectClass);
        integerClass = RubyClass.newClass(this, "Integer", numericClass);
        fixnumClass = RubyClass.newClass(this, "Fixnum", integerClass);
        bignumClass = RubyClass.newClass(this, "Bignum", integerClass);
        floatClass = RubyClass.newClass(this, "Float", numericClass);
        stringClass = RubyClass.newClass(this, "String", objectClass);
        symbolClass = RubyClass.newClass(this, "Symbol", objectClass);
        nilClass = RubyClass.newClass(this, "NilClass", objectClass);
        booleanClass = RubyClass.newClass(this, "Boolean", objectClass);
        trueClass = RubyClass.newClass(this, "TrueClass", booleanClass);
        falseClass = RubyClass.newClass(this, "FalseClass", booleanClass);
        arrayClass = RubyClass.newClass(this, "Array", objectClass);
        hashClass = RubyClass.newClass(this, "Hash", objectClass);
        rangeClass = RubyClass.newClass(this, "Range", objectClass);
        regexpClass = RubyClass.newClass(this, "Regexp", objectClass);
        matchDataClass = RubyClass.newClass(this, "MatchData", objectClass);
        exceptionClass = RubyClass.newClass(this, "Exception", objectClass);
        procClass = RubyClass.newClass(this, "Proc", objectClass);
        fatalClass = RubyClass.newClass(this, "fatal", exceptionClass);

        objectClass.setConstant("Kernel", kernelModule);
        objectClass.setConstant("Object", objectClass);
        objectClass.setConstant("Module", moduleClass);
        objectClass.setConstant("Class", classClass);
        objectClass.setConstant("Numeric", numericClass);
        objectClass.setConstant("Integer", integerClass);
        objectClass.setConstant("Fixnum", fixnumClass);
        objectClass.setConstant("Bignum", bignumClass);
        objectClass.setConstant("Float", floatClass);
        objectClass.setConstant("String", stringClass);
        objectClass.setConstant("Symbol", symbolClass);
        objectClass.setConstant("NilClass", nilClass);
        objectClass.setConstant("Boolean", booleanClass);
        objectClass.setConstant("TrueClass", trueClass);
        objectClass.setConstant("FalseClass", falseClass);
        objectClass.setConstant("Array", arrayClass);
        objectClass.setConstant("Hash", hashClass);
        objectClass.setConstant("Range", rangeClass);
        objectClass.setConstant("Regexp", regexpClass);
        objectClass.setConstant("MatchData", matchDataClass);
        objectClass.setConstant("Exception", exceptionClass);
        objectClass.setConstant("Proc", procClass);
        objectClass.setConstant("fatal", fatalClass);

        nilObject = new SpecialObject(this, nilClass, "nil");
        trueObject = new SpecialObject(this, trueClass, "true");
        falseObject = new SpecialObject(this, falseClass, "false");

        topSelf = new RubyObject(this, objectClass);

        globalVars = new HashMap<String, IRubyObject>();
        context = new Context(this);
    }

    public Context getContext() {
        return context;
    }

    public void setObjectAllocator(ObjectAllocator allocator) {
        this.allocator = allocator;
    }

    public IRubyObject getGlobalVar(String name) {
        return globalVars.get(name);
    }

    public void setGlobalVar(String name, IRubyObject value) {
        globalVars.put(name, value);
    }
    
    public IRubyObject newInstance(RubyClass klass) {
        return allocator.allocate(this, klass);
    }
    
    public boolean isInstanceOf(IRubyObject object, RubyModule klass) {
        return object.getMetaClass() == klass;
    }

    public boolean isKindOf(IRubyObject object, RubyModule klass) {
        // FIXME speedup
        RubyClass oclass = object.getMetaClass();
        while (oclass != null) {
            if (oclass == klass) {
                return true;
            }
            oclass = oclass.getSuperClass();
        }
        if (object.getMetaClass().getIncludes(true).contains(klass)) {
            return true;
        }
        return false;
    }

    public RubyModule getKernel() {
        return kernelModule;
    }

    public RubyClass getObject() {
        return objectClass;
    }

    public RubyClass getModule() {
        return moduleClass;
    }

    public RubyClass getClassClass() {
        return classClass;
    }

    public RubyClass getNumeric() {
        return numericClass;
    }

    public RubyClass getInteger() {
        return integerClass;
    }

    public RubyClass getFixnum() {
        return fixnumClass;
    }

    public RubyClass getBignum() {
        return bignumClass;
    }

    public RubyClass getFloat() {
        return floatClass;
    }

    public RubyClass getString() {
        return stringClass;
    }

    public RubyClass getSymbol() {
        return symbolClass;
    }

    public RubyClass getArray() {
        return arrayClass;
    }

    public RubyClass getHash() {
        return hashClass;
    }

    public RubyClass getRange() {
        return rangeClass;
    }

    public RubyClass getRegexp() {
        return regexpClass;
    }

    public RubyClass getMatchData() {
        return matchDataClass;
    }

    public RubyClass getNilClass() {
        return nilClass;
    }

    public RubyClass getBoolean() {
        return booleanClass;
    }

    public RubyClass getTrueClass() {
        return trueClass;
    }

    public RubyClass getFalseClass() {
        return falseClass;
    }

    public RubyClass getProc() {
        return procClass;
    }

    public IRubyObject getNil() {
        return nilObject;
    }

    public IRubyObject getTrue() {
        return trueObject;
    }

    public IRubyObject getFalse() {
        return falseObject;
    }

    public IRubyObject getTopSelf() {
        return topSelf;
    }
}
