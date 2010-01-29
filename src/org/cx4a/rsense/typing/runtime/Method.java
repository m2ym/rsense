package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;
import org.jruby.lexer.yacc.ISourcePosition;

import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.Visibility;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.annotation.MethodType;

public class Method extends DynamicMethod {
    private Map<TemplateAttribute, Template> templates;
    private List<MethodType> annotations;

    public Method(RubyModule cbase, Node bodyNode, Node argsNode, Visibility visibility, ISourcePosition position) {
        super(cbase, bodyNode, argsNode, visibility, position);
        templates = new HashMap<TemplateAttribute, Template>();
    }

    public Template getTemplate(TemplateAttribute key) {
        return templates.get(key);
    }

    public void addTemplate(TemplateAttribute key, Template template) {
        templates.put(key, template);
    }

    public List<MethodType> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<MethodType> annotations) {
        this.annotations = annotations;
    }
}
