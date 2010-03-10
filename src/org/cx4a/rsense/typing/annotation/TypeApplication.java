package org.cx4a.rsense.typing.annotation;

import java.util.List;

public class TypeApplication implements TypeExpression {
    private TypeIdentity ident;
    private List<TypeExpression> types;

    public TypeApplication(TypeIdentity ident, List<TypeExpression> types) {
        this.ident = ident;
        this.types = types;
    }

    public TypeIdentity getIdentity() {
        return ident;
    }

    public List<TypeExpression> getTypes() {
        return types;
    }

    public Type getType() {
        return Type.APPLICATION;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(ident.toString());
        sb.append('<');
        for (TypeExpression type : types) {
            sb.append(type.toString());
        }
        sb.append('>');
        return sb.toString();
    }
}
