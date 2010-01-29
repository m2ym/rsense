package org.cx4a.rsense.typing.annotation;

import java.util.List;

public class MethodType implements TypeAnnotation {
    private String name;
    private List<TypeVariable> types;
    private List<TypeConstraint> consList;
    private Signature sig;

    public MethodType(String name, List<TypeVariable> types, List<TypeConstraint> consList, Signature sig) {
        this.name = name;
        this.types = types;
        this.consList = consList;
        this.sig = sig;
    }

    public String getName() {
        return name;
    }

    public List<TypeVariable> getTypes() {
        return types;
    }

    public List<TypeConstraint> getConstraints() {
        return consList;
    }

    public Signature getSignature() {
        return sig;
    }

    public static class Block {
        private Signature sig;

        public Block(Signature sig) {
            this.sig = sig;
        }

        public Signature getSignature() {
            return sig;
        }
    }

    public static class Signature {
        private TypeExpression argType;
        private Block block;
        private TypeExpression returnType;

        public Signature(TypeExpression argType, Block block, TypeExpression returnType) {
            this.argType = argType;
            this.block = block;
            this.returnType = returnType;
        }

        public TypeExpression getArgType() {
            return argType;
        }

        public Block getBlock() {
            return block;
        }

        public TypeExpression getReturnType() {
            return returnType;
        }

        @Override
        public String toString() {
            return "<Signature " + argType + " " + block + " " + returnType + ">";
        }
    }

    @Override
    public String toString() {
        return "<MethodType " + name + " " + sig + ">";
    }
}
