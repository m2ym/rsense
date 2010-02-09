package org.cx4a.rsense.typing.annotation;

import java.util.List;

public class ClassType implements TypeAnnotation {
    private String name;
    private List<TypeVariable> types;
    private List<TypeConstraint> constraints;
    private List<TypePragma> pragmas;
    
    public ClassType(String name, List<TypeVariable> types, List<TypeConstraint> constraints, List<TypePragma> pragmas) {
        this.name = name;
        this.types = types;
        this.constraints = constraints;
        this.pragmas = pragmas;
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

    public List<TypePragma> getPragmas() {
        return pragmas;
    }

    public boolean containsType(TypeVariable type) {
        return types != null && types.indexOf(type) != -1;
    }

    public boolean isNoBody() {
        if (pragmas != null) {
            for (TypePragma pragma : pragmas) {
                if (pragma.getType() == TypeExpression.Type.NOBODY_PRAGMA) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isPolymorphic() {
        return types != null;
    }

    @Override
    public String toString() {
        return "<ClassType " + name + " " + types + " " + constraints + " " + pragmas + ">";
    }
}
