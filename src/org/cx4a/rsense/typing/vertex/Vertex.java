package org.cx4a.rsense.typing.vertex;

import java.util.Collection;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;
import java.util.HashSet;
import java.util.Collections;

import org.jruby.ast.Node;
import org.jruby.ast.FixnumNode;
import org.jruby.ast.StrNode;
import org.jruby.ast.SymbolNode;
import org.jruby.ast.types.INameNode;

import org.cx4a.rsense.ruby.IRubyObject;
import org.cx4a.rsense.typing.TypeSet;
import org.cx4a.rsense.typing.Propagation;
import org.cx4a.rsense.typing.runtime.PolymorphicObject;
import org.cx4a.rsense.util.Logger;

public class Vertex {
    public static final int MEGAMORPHIC_THRESHOLD = 5;
    public static final Vertex EMPTY = new Vertex(null, TypeSet.EMPTY) {
            @Override
            public void addEdge(Vertex dest) {}
            @Override
            public void removeEdge(Vertex dest) {}
        };

    static {
        EMPTY.edges = Collections.<Vertex>emptyList();
    }

    protected Node node;
    protected int capacity = MEGAMORPHIC_THRESHOLD + 1;
    protected TypeSet typeSet;
    protected IRubyObject singleType;
    protected boolean megamorphic;
    protected List<Vertex> edges;
    protected boolean changed = true;
    protected long changedTime;

    public Vertex() {}

    public Vertex(int capacity) {
        this.capacity = capacity;
    }

    public Vertex(Node node) {
        this.node = node;
    }

    public Vertex(Node node, int capacity) {
        this.node = node;
        this.capacity = capacity;
    }

    public Vertex(Node node, TypeSet typeSet) {
        this.node = node;
        this.typeSet = typeSet;
    }

    public Node getNode() {
        return node;
    }

    public TypeSet getTypeSet() {
        return typeSet != null ? typeSet : TypeSet.EMPTY;
    }

    public List<Vertex> getEdges() {
        return edges != null ? edges : Collections.<Vertex>emptyList();
    }

    public boolean isEmpty() {
        return typeSet == null || typeSet.isEmpty();
    }

    public boolean addType(IRubyObject type) {
        if (type == null)
            throw new NullPointerException("type is null");

        if (megamorphic)
            return false;

        if (typeSet().size() >= MEGAMORPHIC_THRESHOLD) {
            Logger.info("megamorphic detected");
            megamorphic = true;

            // FIXME generalize
            singleType = type.getRuntime().newInstance(type.getRuntime().getObject());
            typeSet = new TypeSet(1);
            typeSet.add(singleType);

            return false;
        } else {
            if (singleType == null)
                singleType = type;
            if (typeSet().add(type))
                return changed = true;
            else
                return false;
        }
    }

    public boolean addTypes(Collection<IRubyObject> types) {
        boolean added = false;
        for (IRubyObject type : types) {
            if (addType(type))
                added = true;
        }
        return added;
    }

    public boolean update(Vertex other) {
        return addTypes(other.getTypeSet());
    }

    public boolean isChanged() {
        if (changed)
            return true;

        // Check to see if polymorphic objects are changed
        if (typeSet != null && !megamorphic) {
            for (IRubyObject obj : typeSet) {
                if (obj instanceof PolymorphicObject) {
                    PolymorphicObject pobj = (PolymorphicObject) obj;
                    if (changedTime <= pobj.getModifiedTime())
                        return changed = true;
                }
            }
        }

        return false;
    }

    public void markUnchanged() {
        if (changed)
            changedTime = System.currentTimeMillis();
        changed = false;
    }

    public IRubyObject singleType() {
        if (typeSet == null || typeSet.size() != 1)
            return null;
        else if (singleType != null)
            return singleType;
        else
            return singleType = typeSet.iterator().next();
    }

    public void addEdge(Vertex dest) {
        if (edges == null)
            edges = new ArrayList<Vertex>(2);
        edges.add(dest);
    }

    public void removeEdge(Vertex dest) {
        if (edges != null)
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
        return getTypeSet().toString();
    }

    private TypeSet typeSet() {
        if (typeSet == null)
            typeSet = new TypeSet(capacity);
        return typeSet;
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
