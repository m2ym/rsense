package org.cx4a.rsense.typing.vertex;

import java.util.List;
import java.util.ArrayList;

import org.jruby.ast.Node;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.StrNode;
import org.jruby.ast.SymbolNode;
import org.jruby.ast.types.INameNode;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.Propagation;

public class Vertex {
    protected Node node;
    protected TypeSet typeSet;
    protected List<Vertex> edges;

    public Vertex() {
        this(null);
    }

    public Vertex(Node node) {
        this(node, new TypeSet());
    }

    public Vertex(Node node, TypeSet typeSet) {
        this.node = node;
        this.typeSet = typeSet;
        edges = new ArrayList<Vertex>();
    }

    public Node getNode() {
        return node;
    }

    public TypeSet getTypeSet() {
        return typeSet;
    }

    public List<Vertex> getEdges() {
        return edges;
    }

    public boolean isEmpty() {
        return typeSet.isEmpty();
    }

    public void addType(IRubyObject type) {
        typeSet.add(type);
    }

    public void addTypeSet(TypeSet typeSet) {
        this.typeSet.addAll(typeSet);
    }
    
    public void copyTypeSet(Vertex other) {
        typeSet.addAll(other.getTypeSet());
    }

    public IRubyObject singleType() {
        if (typeSet.size() != 1) {
            return null;
        }
        return typeSet.iterator().next();
    }

    public void addEdge(Vertex dest) {
        edges.add(dest);
    }

    public void removeEdge(Vertex dest) {
        edges.remove(dest);
    }

    public boolean accept(Propagation propagation, Vertex src) {
        return propagation.getGraph().propagateVertex(propagation, this, src);
    }

    public boolean isFree() {
        return node == null;
    }

    @Override
    public String toString() {
        return typeSet.toString();
    }

    public static String getName(Vertex vertex) {
        Node node = vertex.getNode();
        if (node instanceof INameNode) {
            return ((INameNode) node).getName();
        } else {
            return null;
        }
    }

    public static Integer getFixnum(Vertex vertex) {
        Node node = vertex.getNode();
        if (node instanceof FixnumNode) {
            return Integer.valueOf((int) ((FixnumNode) node).getValue());
        } else {
            return null;
        }
    }

    public static String getString(Vertex vertex) {
        Node node = vertex.getNode();
        if (node instanceof StrNode) {
            return ((StrNode) node).getValue().toString();
        } else {
            return null;
        }
    }

    public static String getSymbol(Vertex vertex) {
        Node node = vertex.getNode();
        if (node instanceof SymbolNode) {
            return ((SymbolNode) node).getName();
        } else {
            return null;
        }
    }

    public static String getStringOrSymbol(Vertex vertex) {
        String value = getString(vertex);
        return value != null ? value : getSymbol(vertex);
    }
}
