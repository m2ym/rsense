package org.cx4a.rsense.typing.runtime;

import java.util.Map;
import java.util.HashMap;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.ruby.RubyClass;
import org.cx4a.rsense.ruby.RubyObject;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.annotation.ClassType;

public class InstanceFactory {
    private Ruby runtime;
    private Map<RubyClass, IRubyObject> instances;

    public InstanceFactory(Ruby runtime) {
        this.runtime = runtime;
        instances = new HashMap<RubyClass, IRubyObject>();
    }

    public IRubyObject newInstance(RubyClass klass) {
        ClassType classType = RuntimeHelper.getClassAnnotation(klass);
        if (classType != null && classType.isPolymorphic()) {
            return newPolymorphicInstance(klass, classType);
        } else {
            return newMonomorphicInstance(klass);
        }
    }

    public IRubyObject newPolymorphicInstance(RubyClass klass, ClassType type) {
        return new PolymorphicObject(runtime, klass);
    }

    public IRubyObject newMonomorphicInstance(RubyClass klass) {
        IRubyObject instance = instances.get(klass);
        if (instance == null) {
            instance = new RubyObject(runtime, klass);
            instances.put(klass, instance);
        }
        return instance;
    }
}
