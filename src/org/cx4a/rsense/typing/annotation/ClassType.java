package org.cx4a.rsense.typing.annotation;

import java.util.List;

public class ClassType implements TypeAnnotation {
    private String name;
    private List<TypeVariable> types;
    private List<TypeConstraint> constraints;
    
    public ClassType(String name, List<TypeVariable> types, List<TypeConstraint> constraints) {
        this.name = name;
        this.types = types;
        this.constraints = constraints;
    }

    public String getName() {
        return name;
    }

    public List<TypeVariable> getTypes() {
        return types;
    }

    public List<TypeConstraint> getConstraints() {
        return constraints;
    }

    public boolean containsType(TypeVariable type) {
        return types != null && types.indexOf(type) != -1;
    }

    public boolean isPolymorphic() {
        return types != null;
    }

    @Override
    public String toString() {
        return "<ClassType " + name + " " + types + " " + constraints + ">";
    }
}
