package org.cx4a.rsense.typing.annotation;

import java.util.List;

public class TypeTuple implements TypeExpression {
    private List<TypeExpression> elements;

    public TypeTuple(List<TypeExpression> elements) {
        this.elements = elements;
    }

    public List<TypeExpression> getList() {
        return elements;
    }

    public Type getType() {
        return Type.TUPLE;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String delim = "";
        sb.append("(");
        if (elements != null) {
            for (TypeExpression expr : elements) {
                sb.append(delim);
                delim = ", ";
                sb.append(expr.toString());
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
