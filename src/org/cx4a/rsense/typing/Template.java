package org.cx4a.rsense.typing;

import org.cx4a.rsense.ruby.Frame;
import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.runtime.Method;
import org.cx4a.rsense.typing.runtime.TypeVarMap;
import org.cx4a.rsense.typing.vertex.Vertex;
import org.cx4a.rsense.typing.annotation.TypeVariable;

public class Template {
    private Method method;
    private TemplateAttribute attr;
    private TypeVarMap affectedMap;
    private Vertex returnVertex;
    private Frame frame;
    
    public Template(Method method, Frame frame, TemplateAttribute attr) {
        this.method = method;
        this.attr = attr;
        this.frame = frame;
        returnVertex = new Vertex();
    }

    public Method getMethod() {
        return method;
    }

    public TemplateAttribute getAttribute() {
        return attr;
    }

    public TypeVarMap getAffectedMap() {
        return affectedMap;
    }

    public void setAffectedVar(TypeVariable var, IRubyObject value) {
        if (affectedMap == null) {
            affectedMap = new TypeVarMap();
        }
        affectedMap.put(var, value);
    }

    public IRubyObject getAffectedVar(TypeVariable var) {
        return affectedMap == null ? null : affectedMap.get(var);
    }

    public Vertex getReturnVertex() {
        return returnVertex;
    }

    public Frame getFrame() {
        return frame;
    }
}
