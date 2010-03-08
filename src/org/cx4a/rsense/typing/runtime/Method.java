package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;
import org.jruby.lexer.yacc.ISourcePosition;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.MetaClass;
import org.cx4a.rsense.ruby.Visibility;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.MethodType;

public abstract class Method extends DynamicMethod {
    private String name;
    private Map<TemplateAttribute, Template> templates;
    private boolean templatesShared;
    private List<MethodType> annotations;

    public Method(RubyModule cbase, String name, Visibility visibility, ISourcePosition position) {
        super(cbase, visibility, position);
        this.name = name;
        templates = new HashMap<TemplateAttribute, Template>();
    }

    public String getName() {
        return name;
    }

    public Map<TemplateAttribute, Template> getTemplates() {
        return templates;
    }

    public void shareTemplates(Map<TemplateAttribute, Template> templates) {
        this.templates = templates;
        this.templatesShared = true;
    }
    
    public Template getTemplate(TemplateAttribute key) {
        return templates.get(key);
    }

    public void addTemplate(TemplateAttribute key, Template template) {
        templates.put(key, template);
    }

    public boolean isTemplatesShared() {
        return templatesShared;
    }

    public List<MethodType> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<MethodType> annotations) {
        this.annotations = annotations;
    }

    public abstract Vertex call(Graph graph, Template template, IRubyObject receiver, IRubyObject[] args, Vertex[] argVertices, Block block);

    @Override
    public String toString() {
        return getModule().toMethodPathString() + name;
    }
}
