package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.LinkedList;
import java.util.logging.Logger;

/**
 * This class transforms the streams of character sent to the decorated writer.
 * When filter encounters a line separator, it sends it to the decorated writer.
 * It then sends the line number and a tab character, before resuming the write
 * process.
 * <p>
 * Hello\n\World -> 1\Hello\n2\tWorld
 *
 * @author Olivier Liechti
 */
public class FileNumberingFilterWriter extends FilterWriter {

    private static final Logger LOG = Logger.getLogger(FileNumberingFilterWriter.class.getName());
    private int nomberOfLine = 1;
    private boolean folow = false;

    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        for (int i = 0; i < len; i++) {
            write(cbuf[off + i]);
        }
        if (folow) {
            for (char n : String.valueOf(nomberOfLine++).toCharArray()) {
                super.write(n);
            }
            super.write('\t');
            folow = false;
        }
    }

    @Override
    public void write(int c) throws IOException {
        if (folow) {
            if (c == '\n' || c == '\r') {
                folow = false;
                super.write(c);
            }
            for (char n : String.valueOf(nomberOfLine++).toCharArray()) {
                super.write(n);
            }
            super.write('\t');
            if (folow) {
                super.write(c);
                folow = false;
            }
        } else if (nomberOfLine == 1) {
            super.write('1');
            super.write('\t');
            nomberOfLine++;
            super.write(c);
        } else if (c == '\n' || c == '\r') {
            folow = true;
            super.write(c);
            folow = true;
        } else {
            super.write(c);
        }
    }
}
