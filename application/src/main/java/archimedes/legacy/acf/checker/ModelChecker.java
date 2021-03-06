/*
 * ModelChecker.java
 *
 * 28.01.2016
 *
 * (c) by HO.Lieshoff
 *
 */

package archimedes.legacy.acf.checker;

import archimedes.legacy.model.DataModel;
import archimedes.model.*;


/**
 * An interface for checker which are executed before code generation.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 28.01.2016 - Added.
 * @changed OLI 18.05.2016 - Renamed to "ModelChecker" and changed return type of method
 *         <CODE>check()</CODE>.
 */

public interface ModelChecker {

    /**
     * Checks a special problem an returns a string with a HTML problem description in case of
     * an error is detected. Otherwise an empty string is returned.
     *
     * @param dataModel The model to check.
     * @return A problem description in HTML format or an empty string if no problems are found.
     *
     * @changed OLI 28.01.2016 - Added.
     * @changed OLI 18.05.2016 - Return type changed to an array of
     *         <CODE>ModelCheckerMessage</CODE>.
     */
    abstract public ModelCheckerMessage[] check(DataModel model);

}