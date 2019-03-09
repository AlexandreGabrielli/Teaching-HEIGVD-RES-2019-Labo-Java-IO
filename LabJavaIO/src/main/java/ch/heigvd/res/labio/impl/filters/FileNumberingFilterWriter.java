package ch.heigvd.res.labio.impl.filters;

import java.io.FilterWriter;
import java.io.IOException;
import java.io.Writer;
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


    public FileNumberingFilterWriter(Writer out) {
        super(out);
    }

    @Override
    public void write(String str, int off, int len) throws IOException {
        write(str.toCharArray(), off, len);
    }

    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        //did we really need off and len ????????
        for (char c : cbuf) {
            write(c);
        }
    }

    @Override
    public void write(int c) throws IOException {
        //si c'est la 1er ligne
        if (nomberOfLine == 1) {
            super.write(nomberOfLine);
            super.write('\\');
            super.write(c);
            nomberOfLine++;
        } else if (c == '\n' || c == '\r') {
            super.write(c);
            super.write(nomberOfLine);
            super.write('\\');
        }
    }

}
