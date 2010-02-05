package org.cx4a.rsense.util;

import java.io.Reader;
import java.io.IOException;

public class HereDocReader extends Reader {
    private Reader reader;
    private char[] end;
    private int checked = 0;

    public HereDocReader(Reader reader, String end) {
        this.reader = reader;
        this.end = end.toCharArray();
    }

    public void close() throws IOException {
        reader.close();
    }

    public boolean markSupported() {
        return false;
    }

    public int read(char[] cbuf, int off, int len) throws IOException {
        if (checked == end.length) {
            return -1;
        }
        
        int read = 0;
        int c;
        while ((c = reader.read()) != -1) {
            if (end[checked] == c) {
                if (++checked == end.length) {
                    // here doc end detected
                    return read - checked;
                }
            } else {
                checked = 0;
            }
            read++;
            cbuf[off++] = (char) c;
            if (read == len) {
                return read - checked;
            }
        }
        return -1;
    }
}
