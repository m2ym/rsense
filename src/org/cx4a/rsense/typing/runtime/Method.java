package org.cx4a.rsense.typing.runtime;

import java.lang.ref.SoftReference;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;

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
import org.cx4a.rsense.util.SourceLocation;

public abstract class Method extends DynamicMethod {
    private String name;
    private Map<TemplateAttribute, SoftReference<Template>> templates;
    private boolean templatesShared;
    
    private List<MethodType> annotations;

    public Method(RubyModule cbase, String name, Visibility visibility, SourceLocation location) {
        super(cbase, visibility, location);
        this.name = name;
        templates = new HashMap<TemplateAttribute, SoftReference<Template>>();
    }

    public String getName() {
        return name;
    }

    public void shareTemplates(Method with) {
        this.templates = with.templates;
        this.templatesShared = true;
    }

    public Collection<TemplateAttribute> getTemplateAttributes() {
        return templates.keySet();
    }
    
    public Template getTemplate(TemplateAttribute key) {
        SoftReference<Template> ref = templates.get(key);
        return ref != null ? ref.get() : null;
    }

    public void addTemplate(TemplateAttribute key, Template template) {
        templates.put(key, new SoftReference<Template>(template));
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
