/*
 * MethodCommentTagReturn.java
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
 * An implementation of the "MethodCommentTag" interface for "return" tags.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.06.2016 - Added.
 */

public class MethodCommentTagReturn implements MethodCommentTag {

    private String comment = null;

    /**
     * Creates a new method comment tag for a return tag with passed parameters.
     *
     * @param comment A comment for the return value.
     * @throws IllegalArgumentException Passing a null pointer or an empty string.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public MethodCommentTagReturn(String comment) {
        super();
        ensure(comment != null, "comment cannot be null");
        ensure(!comment.isEmpty(), "comment cannot be empty.");
        this.comment = comment;
    }

    /**
     * @changed OLI 17.06.2016 - Added.
     */
    @Override public String getCode(int indent) {
        String code = Str.pumpUp("", " ", indent+1, Direction.LEFT);
        code += "* @return " + Str.toHTML(this.comment) + (this.comment.endsWith(".")
                ? "" : ".") + "\n";
        return code;
    }

}