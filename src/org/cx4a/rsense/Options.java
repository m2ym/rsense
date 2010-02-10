package org.cx4a.rsense;

import java.io.File;
import java.io.Reader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;
import java.util.Collection;
import java.util.Arrays;
import java.util.Collections;

import org.cx4a.rsense.util.HereDocReader;

public class Options extends HashMap<String, String> {
    private static final long serialVersionUID = 0L;

    private List<String> rest = new ArrayList<String>();

    public void addRestArg(String arg) {
        rest.add(arg);
    }

    public List<String> getRestArgs() {
        return rest;
    }

    public boolean isFormatGiven() {
        return containsKey("format");
    }

    public boolean isEncodingGiven() {
        return containsKey("encoding");
    }

    public String getFormat() {
        String format = get("format");
        return format != null ? format : defaultFormat();
    }

    public void setFormat(String format) {
        put("format", format);
    }

    public boolean isPlainFormat() {
        return "plain".equals(getFormat());
    }

    public boolean isEmacsFormat() {
        return "emacs".equals(getFormat());
    }

    public String getEncoding() {
        String encoding = get("encoding");
        return encoding != null ? encoding : defaultEncoding();
    }

    public void setEncoding(String encoding) {
        put("encoding", encoding);
    }

    public String getPrompt() {
        return containsKey("no-prompt") ? "" : get("prompt");
    }

    public File getFile() {
        String file = get("file");
        return file == null ? null : new File(file);
    }

    public boolean isFileStdin() {
        return !containsKey("file") || "-".equals(get("file"));
    }

    public HereDocReader getHereDocReader(Reader reader) {
        return new HereDocReader(reader, "EOF");
    }

    public CodeAssist.Location getLocation() {
        String location = get("location");
        if (location == null) {
            return CodeAssist.Location.markLocation("_|_");
        }
        String[] lr = location.split(":");
        if (lr.length == 2) {
            return CodeAssist.Location.logicalLocation(Integer.parseInt(lr[0]), Integer.parseInt(lr[1]));
        } else {
            try {
                return CodeAssist.Location.offsetLocation(Integer.parseInt(lr[0]));
            } catch (NumberFormatException e) {
                return CodeAssist.Location.markLocation(lr[0]);
            }
        }
    }

    public String getEndMark() {
        return get("end-mark");
    }

    public boolean isDebug() {
        return containsKey("debug");
    }

    public String getLog() {
        return get("log");
    }

    public String getRsenseHome() {
        String rsenseHome = get("rsense-home");
        return rsenseHome != null ? rsenseHome : ".";
    }

    public String getLoadPath() {
        String loadPath = get("load-path");
        if (loadPath == null) {
            String sep = File.pathSeparator;
            if (sep.equals(";")) {
                // Windows maybe (ActiveRuby)
                return "C:\\Program Files\\ruby-1.8\\lib\\ruby\\1.8";
            } else {
                // Unix maybe
                return "/usr/lib/ruby/1.8";
            }
        }
        return loadPath;
    }

    public String getProject() {
        return get("project");
    }

    public boolean isDetectProject() {
        return containsKey("detect-project");
    }

    public boolean isTest() {
        return containsKey("test");
    }

    public boolean isTestColor() {
        return containsKey("test-color");
    }

    public void setTestColor(boolean testColor) {
        put("test-color", "");
    }

    public String getTest() {
        return get("test");
    }

    public Set<String> getShouldContain() {
        return getStringSet("should-contain");
    }

    public Set<String> getShouldNotContain() {
        return getStringSet("should-not-contain");
    }

    public Set<String> getShouldBe() {
        return getStringSet("should-be");
    }

    public boolean isShouldBeGiven() {
        return containsKey("should-be");
    }

    private Set<String> getStringSet(String name) {
        Set<String> result;
        String str = get(name);
        if (str == null) {
            result = Collections.<String>emptySet();
        } else {
            result = new HashSet<String>(Arrays.asList(str.split(",")));
        }
        return result;
    }

    public boolean isPrintAST() {
        return containsKey("print-ast");
    }

    public void inherit(Options parent) {
        if (!isFormatGiven()) {
            setFormat(parent.getFormat());
        }
        if (!isEncodingGiven()) {
            setEncoding(parent.getEncoding());
        }
        setTestColor(parent.isTestColor());
    }

    public static String defaultFormat() {
        return "plain";
    }

    public static String defaultEncoding() {
        return "UTF-8";
    }

    public static String defaultEndMark() {
        return "END";
    }

    public static String defaultPrompt() {
        return "> ";
    }
}
