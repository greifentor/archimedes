/*
 * MethodCommentTag.java
 *
 * 17.06.2016
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf.comments;


/**
 * An interface which is to implement by all classes which represent comment tags like param,
 * return or throws. 
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.06.2016 - Added.
 */

public interface MethodCommentTag {

    /**
     * Returns the code of the tag for the comment line.
     *
     * @param indent The indent for the comment tag.
     * @return The code of the tag for the comment line.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    abstract public String getCode(int indent);

}