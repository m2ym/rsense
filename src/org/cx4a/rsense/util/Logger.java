package org.cx4a.rsense.util;

public class Logger {
    private Logger() {}

    private static boolean debug = false;

    public static void error(String format, Object... args) {
        System.err.println(String.format(format, args));
    }

    public static void warn(String format, Object... args) {
        System.err.println(String.format(format, args));
    }

    public static void debug(String format, Object... args) {
        if (debug) {
            System.err.println(String.format(format, args));
        }
    }

    public static void fixme(String format, Object... args) {
        System.err.println(String.format(format, args));
    }

    public static boolean getDebug() {
        return debug;
    }

    public static void setDebug(boolean debugFlag) {
        debug = debugFlag;
    }
}
