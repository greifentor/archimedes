/*
 * MethodCommentCreator.java
 *
 * 17.06.2016
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf.comments;

import static corentx.util.Checks.*;

import corentx.util.*;


/**
 * A creator for a method comment.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.06.2016 - Added.
 */

public class MethodCommentCreator {

    private String methodComment = null;
    private MethodCommentTag[] tags = null;

    /**
     * Creates a new method comment creator with the passed parameters.
     *
     * @param methodComment A method comment. Can be <CODE>null</CODE> if no comment is
     *         required.
     * @param tags Some tags for params, returns, throws etc. 
     * @throws IllegalArgumentException Passing a null pointer as line creator.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public MethodCommentCreator(String methodComment, MethodCommentTag... tags) {
        super();
        ensure(tags != null, "tags arrays cannot be null.");
        for (MethodCommentTag tag : tags) {
            ensure(tag != null, "method comment tag cannot be null.");
        }
        this.methodComment = methodComment;
        this.tags = tags;
    }

    /**
     * Creates the code for the whole method comment.
     *
     * @return The code for the whole method comment.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public String getCode() {
        return this.getCode(4);
    }

    /**
     * Creates the code for the whole method comment.
     *
     * @param indent The indent for the comment.
     * @return The code for the whole method comment.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public String getCode(int indent) {
        String indentStr = Str.pumpUp("", " ", indent, Direction.LEFT);
        String code = indentStr + "/**\n";
        if (this.methodComment != null) {
            code += indentStr + " * " + Str.toHTML(this.methodComment)
                    + (this.methodComment.endsWith(".") ? "" : ".") + "\n";
            if (this.tags.length > 0) {
                code += indentStr + " *\n";
            }
        }
        for (MethodCommentTag tag : this.tags) {
            code += tag.getCode(indent);
        }
        code += indentStr + " */\n";
        return code;
    }

}