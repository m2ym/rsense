package org.cx4a.rsense.util;

import java.io.PrintStream;

public class Logger {
    public enum Level {
        FIXME, ERROR, WARN, MESSAGE, INFO, DEBUG
    }

    private PrintStream out;
    private Level level = Level.MESSAGE;

    private Logger() {
        out = System.err;
    }

    public PrintStream getOut() {
        return out;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public Level getLevel() {
        return level;
    }
    
    public void setLevel(Level level) {
        this.level = level;
    }

    public void log(Level level, String format, Object... args) {
        log(null, level, format, args);
    }

    public void log(SourceLocation loc, Level level, String format, Object... args) {
        if (needsToPrint(level)) {
            printLocation(loc);
            printLevel(level);
            out.println(String.format(format, args));
        }
    }
    
    private void printLocation(SourceLocation loc) {
        if (loc != null) {
            out.print(loc.toString() + ": ");
        }
    }

    private void printLevel(Level level) {
        out.print(level.toString().toLowerCase() + ": ");
    }

    private boolean needsToPrint(Level level) {
        return this.level.ordinal() >= level.ordinal();
    }

    private static Logger instance = new Logger();
    public static Logger getInstance() {
        return instance;
    }

    public static void error(String format, Object... args) {
        error(null, format, args);
    }

    public static void error(SourceLocation loc, String format, Object... args) {
        getInstance().log(loc, Level.ERROR, format, args);
    }

    public static void warn(String format, Object... args) {
        warn(null, format, args);
    }

    public static void warn(SourceLocation loc, String format, Object... args) {
        getInstance().log(loc, Level.WARN, format, args);
    }

    public static void message(String format, Object... args) {
        message(null, format, args);
    }

    public static void message(SourceLocation loc, String format, Object... args) {
        getInstance().log(loc, Level.MESSAGE, format, args);
    }

    public static void info(String format, Object... args) {
        info(null, format, args);
    }

    public static void info(SourceLocation loc, String format, Object... args) {
        getInstance().log(loc, Level.INFO, format, args);
    }

    public static void debug(String format, Object... args) {
        debug(null, format, args);
    }

    public static void debug(SourceLocation loc, String format, Object... args) {
        getInstance().log(loc, Level.DEBUG, format, args);
    }

    public static void fixme(String format, Object... args) {
        fixme(null, format, args);
    }

    public static void fixme(SourceLocation loc, String format, Object... args) {
        getInstance().log(loc, Level.FIXME, format, args);
    }
}
