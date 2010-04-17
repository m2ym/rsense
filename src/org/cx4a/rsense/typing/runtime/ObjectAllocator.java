package org.cx4a.rsense.typing.runtime;

import java.util.Map;
import java.util.HashMap;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.annotation.ClassType;

public class ObjectAllocator implements Ruby.ObjectAllocator {
    private Map<RubyClass, IRubyObject> instances;

    public ObjectAllocator() {
        instances = new HashMap<RubyClass, IRubyObject>();
    }

    public IRubyObject allocate(Ruby runtime, RubyClass klass) {
        ClassType classType = RuntimeHelper.getClassAnnotation(klass);
        if (classType != null && classType.isPolymorphic()) {
            return newPolymorphicInstance(runtime, klass, classType);
        } else {
            return newMonomorphicInstance(runtime, klass);
        }
    }

    public IRubyObject newPolymorphicInstance(Ruby runtime, RubyClass klass, ClassType type) {
        return new PolymorphicObject(runtime, klass);
    }

    public IRubyObject newMonomorphicInstance(Ruby runtime, RubyClass klass) {
        IRubyObject instance = instances.get(klass);
        if (instance == null) {
            instance = new RubyObject(runtime, klass);
            instances.put(klass, instance);
        }
        return instance;
    }
}
