/*
 * AbstractModelChecker.java
 *
 * 28.01.2016
 *
 * (c) by O. Lieshoff
 *
 */

package archimedes.legacy.acf.checker;

import static corentx.util.Checks.*;

import archimedes.legacy.model.DataModel;
import archimedes.model.*;


/**
 * A base class for <CODE>PreGenerationChecker</CODE> implementations.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 28.01.2016 - Added.
 * @changed OLI 18.05.2016 - Renamed to <CODE>AbstractModelChecker</CODE> and changed to an
 *         implementation of <CODE>ModelChecker</CODE>.
 */

abstract public class AbstractModelChecker implements ModelChecker {

    protected DataModel model = null;

    /**
     * Creates a new checker with the passed parameters.
     *
     * @param model The data model which is to check.
     *
     * @changed OLI 12.01.2015 - Added.
     */
    public AbstractModelChecker(DataModel model) {
        super();
        ensure(model != null, "model cannot be null.");
        this.model = model;
    }

    /**
     * @changed OLI 12.01.2015 - Added.
     * /
    @Override public ModelCheckerMessage[] check() {
        // String report = "";
        List<ModelCheckerMessage> l = this.checkForMessages();
        if (l.size() > 0) {
            report += "<HR SIZE=3>\n";
            report += "<H1>" + this.getReportSectionHeadline() + "</H1>\n";
            report += "<HR SIZE=1>\n";
            report += "<P>" + this.getReportSectionDescription() + "\n";
            report += "<UL>\n";
            for (String cn : l) {
                report += "    <LI>" + Str.toHTML(cn) + "</LI>\n";
            }
            report += "</UL>\n";
        }
        return report;
    }


    /**
     * Returns a description for the report section.
     *
     * @return A description for the report section.
     * 
     * @changed OLI 28.01.2016 - Added.
     */
    abstract public String getReportSectionDescription();

    /**
     * Returns a headline for the report section.
     *
     * @return A headline for the report section.
     * 
     * @changed OLI 28.01.2016 - Added.
     */
    abstract public String getReportSectionHeadline();

}