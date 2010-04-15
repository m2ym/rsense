package org.cx4a.rsense;

import java.util.List;

import org.jruby.ast.Node;
import org.cx4a.rsense.typing.TypeSet;

public class TypeInferenceResult extends CodeAssistResult {
    private TypeSet typeSet = TypeSet.EMPTY;
    
    public TypeInferenceResult() {
        super();
    }

    public void setTypeSet(TypeSet typeSet) {
        this.typeSet = typeSet;
    }

    public TypeSet getTypeSet() {
        return typeSet;
    }

    public static TypeInferenceResult failWithException(String message, Throwable cause) {
        TypeInferenceResult result = new TypeInferenceResult();
        result.addError(new CodeAssistError(message, cause));
        return result;
    }
}
