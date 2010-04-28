package org.cx4a.rsense.typing.runtime;

import java.lang.ref.SoftReference;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.DynamicMethod;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.Graph;
import org.cx4a.rsense.typing.Template;
import org.cx4a.rsense.typing.TemplateAttribute;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.MethodType;

public interface Method extends DynamicMethod {
    public String getName();
    public Node getBodyNode();
    public Node getArgsNode();
    public void shareTemplates(Method with);
    public Map<TemplateAttribute, SoftReference<Template>> getTemplates();
    public Template getTemplate(TemplateAttribute key);
    public void addTemplate(TemplateAttribute key, Template template);
    public boolean isTemplatesShared();
    public List<MethodType> getAnnotations();
    public void setAnnotations(List<MethodType> annotations);
    public Vertex call(Graph graph, Template template, IRubyObject receiver, IRubyObject[] args, Vertex[] argVertices, Block block);
}
