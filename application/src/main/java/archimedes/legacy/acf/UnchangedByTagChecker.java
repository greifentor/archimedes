/*
 * UnchangedByTagChecker.java
 *
 * 20.09.2017
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf;


/**
 * An interface for classes which are able to check file for the unchanged tag.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 20.09.2017 - Added.
 */

public interface UnchangedByTagChecker {

    /**
     * Checks if the class file in the package with the passed name is marked as UNCHANGED.
     *
     * @param absoluteFileName The absolute file name of the class file.
     * @return <CODE>true</CODE> if the class file is existing and marked as UNCHANGED.
     *
     * @changed OLI 20.09.2017 - Added.
     */
    abstract public boolean isFileUnchanged(String absoluteFileName);

}