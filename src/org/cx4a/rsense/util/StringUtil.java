package org.cx4a.rsense.util;

import java.util.List;
import java.util.ArrayList;

public class StringUtil {
    private StringUtil() {}

    public static String[] shellwords(String command) {
        List<String> result = new ArrayList<String>();
        char[] cs = command.toCharArray();
        int len = cs.length;
        StringBuilder sb = null;
        boolean escape = false;
        char quote = 0;
        for (int i = 0; i < len; ) {
            char c = cs[i++];

            // Skip whitespaces
            if (!escape && quote == 0) {
                while (i < len && Character.isWhitespace(c)) {
                    if (sb != null) {
                        result.add(sb.toString());
                        sb = null;
                    }
                    c = cs[i++];
                }
                if (i > len) {
                    break;
                }
            }

            // New field
            if (sb == null) {
                sb = new StringBuilder();
            }

            // Append character
            if (escape) {
                sb.append(c);
                escape = false;
            } else if (c == quote) {
                quote = 0;
            } else {
                switch (c) {
                case '"': case '\'':
                    quote = c;
                    break;
                case '\\':
                    escape = true;
                    break;
                default:
                    sb.append(c);
                    break;
                }
            }
        }
        if (sb != null) {
            result.add(sb.toString());
        }
        return result.toArray(new String[0]);
    }
}
