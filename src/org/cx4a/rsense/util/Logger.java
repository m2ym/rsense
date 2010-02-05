package org.cx4a.rsense.util;

import java.io.PrintStream;

public class Logger {
    public enum Level {
        ERROR, WARN, MESSAGE, INFO, DEBUG, FIXME,
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
        if (needsToPrint(level)) {
            printLevel(level);
            out.println(String.format(format, args));
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
        getInstance().log(Level.ERROR, format, args);
    }

    public static void warn(String format, Object... args) {
        getInstance().log(Level.WARN, format, args);
    }

    public static void message(String format, Object... args) {
        getInstance().log(Level.MESSAGE, format, args);
    }

    public static void info(String format, Object... args) {
        getInstance().log(Level.INFO, format, args);
    }

    public static void debug(String format, Object... args) {
        getInstance().log(Level.DEBUG, format, args);
    }

    public static void fixme(String format, Object... args) {
        getInstance().log(Level.FIXME, format, args);
    }
}
