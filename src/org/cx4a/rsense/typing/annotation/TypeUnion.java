package org.cx4a.rsense.typing.annotation;

import java.util.ArrayList;

public class TypeUnion extends ArrayList<TypeExpression> implements TypeExpression {
    private static final long serialVersionUID = 0L;

    public Type getType() {
        return Type.UNION;
    }
}
