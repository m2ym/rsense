package org.cx4a.rsense;

import java.util.List;

import org.jruby.ast.Node;
import org.cx4a.rsense.typing.TypeSet;

public class InferTypeResult extends CodeAssistResult {
    private TypeSet typeSet;
    
    public InferTypeResult() {
        super();
    }

    public void setTypeSet(TypeSet typeSet) {
        this.typeSet = typeSet;
    }

    public TypeSet getTypeSet() {
        return typeSet;
    }

    public static InferTypeResult failWithException(String message, Throwable cause) {
        InferTypeResult result = new InferTypeResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
