/*
 * ResourceFileReader.java
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
 * This interface describes the methods of a resource file reader.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 26.11.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public interface ResourceFileReader {

    /**
     * Reads the file with the passed name and returns it in a Properties object.
     *
     * @param fileName The name of the resource file which is to read.
     * @return A Properties object which contains the content of the file with the passed name.
     * @throws IOException If something goes wrong while reading the file.
     *
     * @changed OLI 26.11.2013 - Added.
     */
    abstract public Properties read(String fileName) throws IOException;

}