package org.cx4a.rsense.typing.annotation;

public class TypeOptional implements TypeExpression {
    private TypeExpression expr;

    public TypeOptional(TypeExpression expr) {
        this.expr = expr;
    }

    public TypeExpression getExpression() {
        return expr;
    }

    public Type getType() {
        return Type.OPTIONAL;
    }

    @Override
    public String toString() {
        return "?" + expr;
    }
}
