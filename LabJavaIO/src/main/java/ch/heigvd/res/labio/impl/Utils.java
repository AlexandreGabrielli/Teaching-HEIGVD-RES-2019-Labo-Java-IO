package ch.heigvd.res.labio.impl;

import java.util.logging.Logger;

/**
 * @author Olivier Liechti
 */
public class Utils {

    private static final Logger LOG = Logger.getLogger(Utils.class.getName());

    /**
     * This method looks for the next new line separators (\r, \n, \r\n) to extract
     * the next line in the string passed in arguments.
     *
     * @param lines a string that may contain 0, 1 or more lines
     * @return an array with 2 elements; the first element is the next line with
     * the line separator, the second element is the remaining text. If the argument does not
     * contain any line separator, then the first element is an empty string.
     */
    public static String[] getNextLine(String lines) {
        int i = 0;
        String[] resultat = new String[2];
        resultat[0] ="";
        resultat[1]="";
        boolean next = false;
        boolean hastwoligne =false;

        for (char c : lines.toCharArray()) {
            if (next) {
                if (c == '\r' || c == '\n') {
                    resultat[i] += c;
                    i = 1;
                } else {
                    i = 1;
                    resultat[i] += c;
                }
                next = false;
            } else {
                resultat[i] += c;
                if (c == '\r' || c == '\n') {
                    next = true;
                    hastwoligne =true;
                }
            }
        }

        if (!hastwoligne){
            resultat[1] = resultat[0];
            resultat[0] = "";
        }

        return resultat;
    }

}
