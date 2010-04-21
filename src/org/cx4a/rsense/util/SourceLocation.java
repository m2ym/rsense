package org.cx4a.rsense.util;

import org.jruby.ast.Node;
import org.jruby.lexer.yacc.ISourcePosition;

import org.cx4a.rsense.typing.vertex.Vertex;

public class SourceLocation {
    private String file;
    private int line;
    
    public SourceLocation(String file, int line) {
        this.file = file;
        this.line = line;
    }

    public String getFile() {
        return file;
    }

    public int getLine() {
        return line;
    }

    @Override
    public int hashCode() {
        return line ^ (file != null ? file.hashCode() : 0);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        else if (!(other instanceof SourceLocation))
            return false;

        SourceLocation o = (SourceLocation) other;
        return line == o.line
            && ((file == null && o.file == null)
                || (file != null && file.equals(o.file)));
    }

    @Override
    public String toString() {
        return file + ":" + line;
    }

    public static SourceLocation of(Node node) {
        ISourcePosition pos = node.getPosition();
        if (pos != null && pos != ISourcePosition.INVALID_POSITION) {
            return new SourceLocation(pos.getFile(), pos.getStartLine() + 1);
        } else {
            return null;
        }
    }
    
    public static SourceLocation of(Vertex vertex) {
        if (vertex != null && vertex.getNode() != null) {
            return of(vertex.getNode());
        } else {
            return null;
        }
    }
}
