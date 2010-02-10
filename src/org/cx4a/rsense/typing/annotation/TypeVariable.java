package org.cx4a.rsense.typing.annotation;

public class TypeVariable implements TypeExpression {
    private String name;

    public TypeVariable(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return Type.VARIABLE;
    }

    @Override
    public String toString() {
        return "@" + name;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (!(other instanceof TypeVariable)) {
            return false;
        }

        return name.equals(((TypeVariable) other).name);
    }

    public static TypeVariable valueOf(String name) {
        return new TypeVariable(name);
    }
}
