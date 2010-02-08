package org.cx4a.rsense;

import java.io.File;
import java.io.Reader;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
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
        return get("format");
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
        return get("encoding");
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

    public boolean isTest() {
        return containsKey("test");
    }

    public String getTest() {
        return get("test");
    }

    public List<String> getShouldContain() {
        String str = get("should-contain");
        if (str == null) {
            return Collections.<String>emptyList();
        }
        return Arrays.asList(str.split(","));
    }

    public boolean isPrintAST() {
        return containsKey("print-ast");
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
