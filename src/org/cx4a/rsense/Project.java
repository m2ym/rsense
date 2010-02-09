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
    private Set<File> loaded;

    public Project(String name, File path) {
        this.name = name;
        this.path = path;
        this.runtime = new Ruby();
        this.graph = new Graph(runtime);
        this.loadPath = new ArrayList<String>();
        this.loaded = new HashSet<File>();
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

    public void setLoadPath(String loadPathStr) {
        if (loadPathStr != null) {
            for (String pathElement : loadPathStr.split(File.pathSeparator)) {
                addLoadPath(pathElement);
            }
        }
    }

    public void addLoadPath(String pathElement) {
        this.loadPath.add(pathElement);
    }

    public boolean isLoaded(File file) {
        return loaded.contains(file);
    }

    public void setLoaded(File file) {
        loaded.add(file);
    }
}
