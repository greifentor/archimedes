/*
 * MethodCommentTagParam.java
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
 * An implementation of the "MethodCommentTag" interface for "param" tags.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.06.2016 - Added.
 */

public class MethodCommentTagParam implements MethodCommentTag {

    private String comment = null;
    private String paramName = null;

    /**
     * Creates a new method comment tag for a param tag with passed parameters.
     *
     * @param paramName The name of the parameter field.
     * @param comment A comment for the parameter field.
     * @throws IllegalArgumentException Passing a null pointer or an empty string.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public MethodCommentTagParam(String paramName, String comment) {
        super();
        ensure(paramName != null, "parameter name cannot be null");
        ensure(!paramName.isEmpty(), "parameter name cannot be empty.");
        ensure(comment != null, "comment cannot be null");
        ensure(!comment.isEmpty(), "comment cannot be empty.");
        this.comment = comment;
        this.paramName = paramName;
    }

    /**
     * @changed OLI 17.06.2016 - Added.
     */
    @Override public String getCode(int indent) {
        String code = Str.pumpUp("", " ", indent+1, Direction.LEFT);
        return code + "* @param " + this.paramName + " " + Str.toHTML(this.comment)
                + (this.comment.endsWith(".") ? "" : ".") + "\n";
    }

}