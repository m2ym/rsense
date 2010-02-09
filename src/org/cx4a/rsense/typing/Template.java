package org.cx4a.rsense.typing;

import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.Scope;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.runtime.VertexHolder;
import org.cx4a.rsense.typing.runtime.RuntimeHelper;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class Template {
    private Method method;
    private TemplateAttribute attr;
    private Vertex returnVertex;
    private Frame frame;
    private Scope scope;

    // To remember/reproduce side effects of polymorphic objects
    private TypeVarMap recvtvmap;
    private TypeVarMap[] argtvmaps;
    
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

    public void rememberSideEffect(IRubyObject receiver, IRubyObject[] args) {
        recvtvmap = RuntimeHelper.getTypeVarMap(receiver);
        argtvmaps = new TypeVarMap[args.length];
        for (int i = 0; i < args.length; i++) {
            argtvmaps[i] = RuntimeHelper.getTypeVarMap(args[i]);
        }
    }

    public void reproduceSideEffect(IRubyObject receiver, IRubyObject[] args) {
        TypeVarMap map;

        map = RuntimeHelper.getTypeVarMap(receiver);
        if (map != null && recvtvmap != null) {
            map.putAll(recvtvmap);
        }
        for (int i = 0; i < args.length; i++) {
            map = RuntimeHelper.getTypeVarMap(args[i]);
            if (map != null && argtvmaps[i] != null) {
                map.putAll(argtvmaps[i]);
            }
        }
    }
}
