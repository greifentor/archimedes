/*
 * MethodCommentTagThrows.java
 *
 * 17.06.2016
 *
 * (c) by HO.Lieshoff
 *
 */

package archimedes.legacy.acf.comments;

import static corentx.util.Checks.*;

import corentx.util.*;


/**
 * An implementation of the "MethodCommentTag" interface for "throws" tags.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.06.2016 - Added.
 */

public class MethodCommentTagThrows implements MethodCommentTag {

    private String comment = null;
    private String exceptionClassName = null;

    /**
     * Creates a new method comment tag for a throwstag with passed parameters.
     *
     * @param exceptionClassName The name of the thrown exception.
     * @param comment A comment for the exception.
     * @throws IllegalArgumentException Passing a null pointer or an empty string.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public MethodCommentTagThrows(String exceptionClassName, String comment) {
        super();
        ensure(exceptionClassName != null, "exception class name cannot be null");
        ensure(!exceptionClassName.isEmpty(), "exception class name cannot be empty.");
        ensure(comment != null, "comment cannot be null");
        ensure(!comment.isEmpty(), "comment cannot be empty.");
        this.comment = comment;
        this.exceptionClassName = exceptionClassName;
    }

    /**
     * @changed OLI 17.06.2016 - Added.
     */
    @Override public String getCode(int indent) {
        String code = Str.pumpUp("", " ", indent+1, Direction.LEFT);
        return code + "* @throws " + this.exceptionClassName + " " + Str.toHTML(this.comment)
                + (this.comment.endsWith(".") ? "" : ".") + "\n";
    }

}