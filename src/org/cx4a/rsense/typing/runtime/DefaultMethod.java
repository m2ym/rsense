package org.cx4a.rsense.typing.runtime;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;
import org.jruby.ast.ArgsNode;
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

public class DefaultMethod extends Method {
    protected Node bodyNode, argsNode;

    public DefaultMethod(RubyModule cbase, String name, Node bodyNode, Node argsNode, Visibility visibility, ISourcePosition position) {
        super(cbase, name, visibility, position);
        this.bodyNode = bodyNode;
        this.argsNode = argsNode;
    }

    public Node getBodyNode() {
        return bodyNode;
    }

    public Node getArgsNode() {
        return argsNode;
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
}
