package org.cx4a.rsense.typing.annotation;

public interface TypeExpression {
    public enum Type { RELATIVE_IDENTITY, UNION, VARIABLE, ANY, OPTIONAL, TUPLE, SPLAT, APPLICATION, SUBTYPE_CONS, }

    public Type getType();
}
