package org.cx4a.rsense.typing.annotation;

public class TypeIdentity implements TypeExpression {
    private Type type;
    private String name;
    private TypeIdentity path;

    private TypeIdentity(Type type) {
        this.type = type;
    }

    private TypeIdentity(Type type, String name) {
        this(type, name, null);
    }

    private TypeIdentity(Type type, TypeIdentity path) {
        this(type, null, path);
    }

    private TypeIdentity(Type type, String name, TypeIdentity path) {
        this.type = type;
        this.name = name;
        this.path = path;
    }
    
    public Type getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public TypeIdentity getPath() {
        return path;
    }

    public static TypeIdentity newRelativeIdentity(String name) {
        return new TypeIdentity(Type.RELATIVE_IDENTITY, name);
    }

    public static TypeIdentity newScopedIdentity(String name, TypeIdentity path) {
        return new TypeIdentity(Type.SCOPED_IDENTITY, name, path);
    }

    public static TypeIdentity newAbsoluteIdentity(TypeIdentity path) {
        return new TypeIdentity(Type.ABSOLUTE_IDENTITY, path);
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
