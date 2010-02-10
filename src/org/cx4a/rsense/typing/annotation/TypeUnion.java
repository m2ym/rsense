package org.cx4a.rsense.typing.annotation;

import java.util.ArrayList;

public class TypeUnion extends ArrayList<TypeExpression> implements TypeExpression {
    private static final long serialVersionUID = 0L;

    public Type getType() {
        return Type.UNION;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String delim = "";
        for (TypeExpression expr : this) {
            sb.append(delim);
            delim = " or ";
            sb.append(expr);
        }
        return sb.toString();
    }
}
