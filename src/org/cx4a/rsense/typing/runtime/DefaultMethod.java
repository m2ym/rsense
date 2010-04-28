package org.cx4a.rsense.typing.runtime;

import java.lang.ref.SoftReference;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;
import org.jruby.ast.ArgsNode;

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

public class DefaultMethod implements Method {
    private RubyModule cbase;
    private String name;
    private Node bodyNode, argsNode;
    private Visibility visibility;
    private SourceLocation location;
    private boolean templatesShared;
    private Map<TemplateAttribute, SoftReference<Template>> templates;
    private List<MethodType> annotations;

    public DefaultMethod(RubyModule cbase, String name, Node bodyNode, Node argsNode, Visibility visibility, SourceLocation location) {
        this.cbase = cbase;
        this.name = name;
        this.bodyNode = bodyNode;
        this.argsNode = argsNode;
        this.visibility = visibility;
        this.location = location;
        this.templates = new HashMap<TemplateAttribute, SoftReference<Template>>();
    }

    public String getName() {
        return name;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public Node getArgsNode() {
        return argsNode;
    }

    public RubyModule getModule() {
        return cbase;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }

    public SourceLocation getLocation() {
        return location;
    }

    public void shareTemplates(Method with) {
        this.templates = with.getTemplates();
        this.templatesShared = true;
    }

    public Map<TemplateAttribute, SoftReference<Template>> getTemplates() {
        return templates;
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

    public Vertex call(Graph graph, Template template, IRubyObject receiver, IRubyObject[] args, Vertex[] argVertices, Block block) {
        // FIXME more efficient way
        RuntimeHelper.argsAssign(graph, (ArgsNode) argsNode, argVertices, block);
        if (bodyNode != null) {
            return graph.createVertex(bodyNode);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return getModule().getMethodPath(getName());
    }
}
