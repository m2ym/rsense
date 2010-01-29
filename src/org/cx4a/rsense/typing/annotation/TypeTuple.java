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
}
