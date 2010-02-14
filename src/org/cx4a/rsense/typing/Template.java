package org.cx4a.rsense.typing;

import java.util.Map;

import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.RuntimeHelper;
import org.cx4a.rsense.typing.runtime.MonomorphicObject;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class Template {
    private Method method;
    private TemplateAttribute attr;
    private Vertex returnVertex;
    private Frame frame;
    private Scope scope;
    
    public Template(Method method, Frame frame, Scope scope, TemplateAttribute attr) {
        this.method = method;
        this.attr = attr;
        this.frame = frame;
        this.scope = scope;
        this.returnVertex = new Vertex();
    }

    public Method getMethod() {
        return method;
    }

    public TemplateAttribute getAttribute() {
        return attr;
    }

    public Vertex getReturnVertex() {
        return returnVertex;
    }

    public Frame getFrame() {
        return frame;
    }

    public Scope getScope() {
        return scope;
    }

    public void reproduceSideEffect(Graph graph, IRubyObject receiver, IRubyObject[] args) {
        reproduceSideEffect(graph, attr.getReceiver(), receiver);
        for (int i = 0; i < attr.getArgs().length && i < args.length; i++) {
            reproduceSideEffect(graph, attr.getArg(i), args[i]);
        }
    }

    private void reproduceSideEffect(Graph graph, IRubyObject from, IRubyObject to) {
        if (from instanceof MonomorphicObject && to instanceof MonomorphicObject) {
            MonomorphicObject a = (MonomorphicObject) from;
            MonomorphicObject b = (MonomorphicObject) to;
            for (Map.Entry<TypeVariable, Vertex> entry : a.getTypeVarMap().entrySet()) {
                Vertex src = entry.getValue();
                Vertex dest = b.getTypeVarMap().get(entry.getKey());
                if (dest != null) {
                    src.addEdge(dest);
                    dest.copyTypeSet(src);
                    // FIXME
                    //graph.propagateEdges(src);
                }
            }
        }
    }
}
