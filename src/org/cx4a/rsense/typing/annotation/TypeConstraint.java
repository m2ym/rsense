package org.cx4a.rsense.typing.annotation;

public class TypeConstraint implements TypeExpression {
    private Type type;
    private TypeExpression lhs, rhs;

    public TypeConstraint(Type type, TypeExpression lhs, TypeExpression rhs) {
        this.type = type;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Type getType() {
        return type;
    }

    public TypeExpression lhs() {
        return lhs;
    }

    public TypeExpression rhs() {
        return rhs;
    }

    @Override
    public String toString() {
        return lhs + " <= " + rhs;
    }
}
