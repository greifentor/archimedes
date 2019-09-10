/*
 * AbstractPreGenerationCheckerForTables.java
 *
 * 28.01.2016
 *
 * (c) by O. Lieshoff
 *
 */

package archimedes.legacy.acf.checker;

import java.util.*;

import archimedes.acf.param.*;
import archimedes.legacy.acf.param.TableParamIds;
import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.TableModel;
import archimedes.model.*;


/**
 * A base class for pre generation checkers which are checking every single class of a model.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 28.01.2016 - Added.
 */

abstract public class AbstractModelCheckerForTables extends AbstractModelChecker {

    /**
     * Creates a new checker with the passed parameters.
     *
     * @param model The data model which is to check.
     *
     * @changed OLI 12.01.2015 - Added.
     */
    public AbstractModelCheckerForTables(DataModel model) {
        super(model);
    }

    /**
     * @changed OLI 28.01.2016 - Added.
     */
    @Override public ModelCheckerMessage[] check(DataModel model) {
        List<ModelCheckerMessage> l = new LinkedList<ModelCheckerMessage>();
        for (TableModel t : model.getTables()) {
            if (!t.isOptionSet(TableParamIds.NO_CODE_GENERATION)) {
                if (this.hasError(t)) {
                    this.addErrorObjectDescriptions(l, t);
                }
            }
        }
        return l.toArray(new ModelCheckerMessage[0]);
    }

    /**
     * Adds the descriptions of the error object (like a table or column name) which can be
     * printed in the error message to the passed list.
     *
     * @param l The list with the descriptions of the error objects.
     * @param t The table model which has an error.
     *
     * @changed OLI 28.01.2016 - Added.
     */
    abstract public void addErrorObjectDescriptions(List<ModelCheckerMessage> l, TableModel t);

    /**
     * Checks the passed table for errors.
     *
     * @param t The table model to check for errors.
     *
     * @return <CODE>true</CODE> if errors are found for the passed table.
     *
     * @changed OLI 28.01.2016 - Added.
     */
    abstract public boolean hasError(TableModel t);

}