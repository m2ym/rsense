package org.cx4a.rsense;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

import org.jruby.ast.Node;

public class CodeAssistResult {
    private Node ast;
    private List<CodeAssistError> errors;

    public CodeAssistResult() {
    }

    public void setAST(Node ast) {
        this.ast = ast;
    }

    public Node getAST() {
        return ast;
    }

    public void addError(CodeAssistError error) {
        if (errors == null) {
            errors = new ArrayList<CodeAssistError>();
        }
        errors.add(error);
    }

    public List<CodeAssistError> getErrors() {
        return errors == null ? Collections.<CodeAssistError>emptyList() : errors;
    }

    public void setErrors(List<CodeAssistError> errors) {
        this.errors = errors;
    }

    public boolean hasError() {
        return errors != null;
    }
}
