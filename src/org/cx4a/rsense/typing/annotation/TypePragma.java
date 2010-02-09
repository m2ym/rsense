package org.cx4a.rsense.typing.annotation;

public class TypePragma implements TypeExpression {
    private Type type;

    public TypePragma(Type type) {
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        switch (type) {
        case NOBODY_PRAGMA:
            return "!nobody";
        }
        return "unknown-pragma";
    }
}
