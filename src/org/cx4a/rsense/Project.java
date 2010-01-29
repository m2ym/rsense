package org.cx4a.rsense;

import java.io.File;

import java.util.Map;
import java.util.HashMap;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.typing.Graph;

public class Project {
    private String name;
    private File path;
    private Ruby runtime;
    private Graph graph;
    private Map<String, Node> fileASTMap;

    public Project(String name, File path) {
        this.name = name;
        this.path = path;
        this.runtime = new Ruby();
        this.graph = new Graph(runtime);
        this.fileASTMap = new HashMap<String, Node>();
    }

    public String getName() {
        return name;
    }

    public File getPath() {
        return path;
    }

    public Ruby getRuntime() {
        return runtime;
    }

    public Graph getGraph() {
        return graph;
    }

    public void storeFileAST(String name, Node ast) {
        fileASTMap.put(name, ast);
    }

    public Node getFileAST(String name) {
        return fileASTMap.get(name);
    }
}
