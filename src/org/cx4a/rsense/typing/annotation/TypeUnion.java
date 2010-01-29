package org.cx4a.rsense.typing.annotation;

import java.util.ArrayList;

public class TypeUnion extends ArrayList<TypeExpression> implements TypeExpression {
    public Type getType() {
        return Type.UNION;
    }
}
