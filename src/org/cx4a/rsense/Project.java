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
import org.cx4a.rsense.util.Logger;

public class Project {
    public static interface EventListener extends Graph.EventListener {
    }

    private String name;
    private File path;
    private Ruby runtime;
    private Graph graph;
    private List<File> loadPath;
    private List<File> gemPath;
    private Set<String> loaded;

    public Project(String name, File path) {
        this.name = name;
        this.path = path;
        this.graph = new Graph();
        this.runtime = graph.getRuntime();
        this.loadPath = new ArrayList<File>();
        this.gemPath = new ArrayList<File>();
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

    public List<File> getLoadPath() {
        return loadPath;
    }

    public void setLoadPath(List<String> loadPath) {
        this.loadPath.addAll(getAbsoluteLoadPath(loadPath));
    }

    public List<File> getGemPath() {
        return gemPath;
    }

    public void setGemPath(List<String> gemPath) {
        this.gemPath.addAll(getAbsoluteLoadPath(gemPath));
    }

    public boolean isLoaded(String name) {
        return loaded.contains(name);
    }

    public void setLoaded(String name) {
        loaded.add(name);
    }

    public void addEventListener(EventListener eventListener) {
        graph.addEventListener(eventListener);
    }

    public void removeEventListener(EventListener eventListener) {
        graph.removeEventListener(eventListener);
    }

    private List<File> getAbsoluteLoadPath(List<String> loadPath) {
        ArrayList<File> result = new ArrayList<File>();
        for (String elem : loadPath) {
            File dir = new File(elem);
            if (!dir.isAbsolute()) {
                File absdir = new File(path, dir.getPath());
                if (absdir.isDirectory()) {
                    dir = absdir;
                }
            }

            if (dir.isDirectory()) {
                result.add(dir);
            } else {
                Logger.warn("Load-path not found: %s", dir);
            }
        }
        return result;
    }
}
