package org.cx4a.rsense.typing.annotation;

public class TypeSplat implements TypeExpression {
    private TypeExpression expr;

    public TypeSplat(TypeExpression expr) {
        this.expr = expr;
    }

    public TypeExpression getExpression() {
        return expr;
    }

    public Type getType() {
        return Type.SPLAT;
    }

    @Override
    public String toString() {
        return expr != null ? "*" + expr.toString() : "*";
    }
}
