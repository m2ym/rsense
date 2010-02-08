package org.cx4a.rsense.typing.annotation;

public interface TypeExpression {
    public enum Type { RELATIVE_IDENTITY, SCOPED_IDENTITY, ABSOLUTE_IDENTITY,
            UNION, VARIABLE, ANY, OPTIONAL,
            TUPLE, SPLAT, APPLICATION, SUBTYPE_CONS,
            NOBODY_PRAGMA,
            }

    public Type getType();
}
