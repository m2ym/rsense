package org.cx4a.rsense.typing.annotation;

public class TypeIdentity implements TypeExpression {
    private Type type;
    private String name;

    private TypeIdentity(Type type) {
        this.type = type;
    }

    private TypeIdentity(Type type, String name) {
        this.type = type;
        this.name = name;
    }
    
    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public static TypeIdentity newRelativeIdentity(String name) {
        return new TypeIdentity(Type.RELATIVE_IDENTITY, name);
    }

    @Override
    public String toString() {
        switch (type) {
        case RELATIVE_IDENTITY:
            return "$" + name;
        default:
            return "?" + name;
        }
    }
}
