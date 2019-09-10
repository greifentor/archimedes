/*
 * DefaultResourceFileReader.java
 *
 * 26.11.2013
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf.io;


import java.io.*;
import java.util.*;


/**
 * An implementation for the regular run of the Archimedes code factory.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 26.11.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public class DefaultResourceFileReader implements ResourceFileReader {

    /**
     * @changed OLI 26.11.2013 - Added.
     */
    @Override public Properties read(String fileName) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(new File(fileName)));
        return p;
    }

}