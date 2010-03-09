package org.cx4a.rsense;

import java.io.File;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import org.jruby.ast.Node;

import org.cx4a.rsense.ruby.Ruby;
import org.cx4a.rsense.typing.Graph;

public class Project {
    private String name;
    private File path;
    private Ruby runtime;
    private Graph graph;
    private List<String> loadPath;
    private List<String> gemPath;
    private Set<String> loaded;

    public Project(String name, File path) {
        this.name = name;
        this.path = path;
        this.runtime = new Ruby();
        this.graph = new Graph(runtime);
        this.loadPath = new ArrayList<String>();
        this.gemPath = new ArrayList<String>();
        this.loaded = new HashSet<String>();
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

    public List<String> getLoadPath() {
        return loadPath;
    }

    public void setLoadPath(List<String> loadPath) {
        this.loadPath.addAll(loadPath);
    }

    public List<String> getGemPath() {
        return gemPath;
    }

    public void setGemPath(List<String> gemPath) {
        this.gemPath.addAll(gemPath);
    }

    public boolean isLoaded(String name) {
        return loaded.contains(name);
    }

    public void setLoaded(String name) {
        loaded.add(name);
    }
}
