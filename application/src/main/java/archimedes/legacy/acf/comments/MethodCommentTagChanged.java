/*
 * MethodCommentTagChanged.java
 *
 * 17.06.2016
 *
 * (c) by HO.Lieshoff
 *
 */

package archimedes.legacy.acf.comments;

import corentx.util.*;


/**
 * An implementation of the "MethodCommentTag" interface for "changed" tags.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 17.06.2016 - Added.
 */

public class MethodCommentTagChanged implements MethodCommentTag {

    private boolean leadingEmptyLine = false;

    /**
     * Creates a new method comment changed tag with a leading empty line.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public MethodCommentTagChanged() {
        this(true);
    }

    /**
     * Creates a new method comment changed tag with the passed parameters.
     *
     * @param leadingEmptyLine Set this flag if a leading empty line should be set in the code.
     *
     * @changed OLI 17.06.2016 - Added.
     */
    public MethodCommentTagChanged(boolean leadingEmptyLine) {
        super();
        this.leadingEmptyLine = leadingEmptyLine;
    }

    /**
     * @changed OLI 17.06.2016 - Added.
     */
    @Override public String getCode(int indent) {
        String indentStr = Str.pumpUp("", " ", indent+1, Direction.LEFT);
        String code = (this.leadingEmptyLine ? indentStr + "*\n" : "");
        return code + indentStr + "* Generated (date see above).\n";
    }

}