package org.cx4a.rsense.typing;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.jruby.ast.YieldNode;

import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.ruby.Block;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.RuntimeHelper;
import org.cx4a.rsense.typing.runtime.MonomorphicObject;
import org.cx4a.rsense.typing.runtime.Proc;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.vertex.YieldVertex;
import org.cx4a.rsense.typing.vertex.TypeVarVertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;
import org.cx4a.rsense.util.Logger;

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

    public void reproduceSideEffect(Graph graph, IRubyObject receiver, IRubyObject[] args, Block block) {
        reproduceSideEffect(graph, attr.getMutableReceiver(false), receiver);
        for (int i = 0; i < attr.getArgs().length && i < args.length; i++) {
            reproduceSideEffect(graph, attr.getMutableArg(i, false), args[i]);
        }
        reproduceYield(graph, receiver, args, block);
    }

    private void reproduceSideEffect(Graph graph, IRubyObject from, IRubyObject to) {
        if (from instanceof MonomorphicObject && to instanceof MonomorphicObject) {
            MonomorphicObject a = (MonomorphicObject) from;
            MonomorphicObject b = (MonomorphicObject) to;
            for (Map.Entry<TypeVariable, Vertex> entry : a.getTypeVarMap().entrySet()) {
                Vertex src = entry.getValue();
                Vertex dest = b.getTypeVarMap().get(entry.getKey());
                if (dest != null) {
                    graph.propagateEdge(src, dest);
                }
            }
        }
    }

    private void reproduceYield(Graph graph, IRubyObject receiver, IRubyObject[] args, Block block) {
        Proc templateProc = (Proc) attr.getBlock();
        if (templateProc != null && block != null) {
            // Yield records may change during loop
            for (YieldVertex vertex : new ArrayList<YieldVertex>(templateProc.getYields())) {
                RuntimeHelper.yield(graph, new YieldVertex(vertex.getNode(), this, block, vertex.getArgsVertex(), vertex.getExpandArguments()));
            }
        }
    }
}
