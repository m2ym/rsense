package org.cx4a.rsense.typing.runtime;

import java.lang.ref.SoftReference;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.RubyModule;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.ruby.Visibility;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.MethodType;
import org.cx4a.rsense.util.SourceLocation;

public class AliasMethod implements Method {
    private String newName;
    private Method method;

    public AliasMethod(String newName, Method method) {
        this.newName = newName;
        this.method = method;
    }

    public RubyModule getModule() {
        return method.getModule();
    }
    
    public Visibility getVisibility() {
        return method.getVisibility();
    }
    
    public void setVisibility(Visibility visibility) {
        method.setVisibility(visibility);
    }
    
    public SourceLocation getLocation() {
        return method.getLocation();
    }

    public String getName() {
        return newName;
    }

    public Node getBodyNode() {
        return method.getBodyNode();
    }

    public Node getArgsNode() {
        return method.getArgsNode();
    }

    public void shareTemplates(Method with) {
        method.shareTemplates(with);
    }

    public Map<TemplateAttribute, SoftReference<Template>> getTemplates() {
        return method.getTemplates();
    }
    
    public Template getTemplate(TemplateAttribute key) {
        return method.getTemplate(key);
    }
    
    public void addTemplate(TemplateAttribute key, Template template) {
        method.addTemplate(key, template);
    }
    
    public boolean isTemplatesShared() {
        return method.isTemplatesShared();
    }
    
    public List<MethodType> getAnnotations() {
        return method.getAnnotations();
    }
    
    public void setAnnotations(List<MethodType> annotations) {
        method.setAnnotations(annotations);
    }
    
    public Vertex call(Graph graph, Template template, IRubyObject receiver, IRubyObject[] args, Vertex[] argVertices, Block block) {
        return method.call(graph, template, receiver, args, argVertices, block);
    }

    @Override
    public String toString() {
        return getModule().getMethodPath(getName());
    }
}
