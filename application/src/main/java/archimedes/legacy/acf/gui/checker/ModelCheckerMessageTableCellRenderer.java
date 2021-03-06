/*
 * ModelCheckerMessageTableCellRenderer.java
 *
 * 15.06.2016
 *
 * (c) by HO.Lieshoff
 *
 */

package archimedes.legacy.acf.gui.checker;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;

import archimedes.acf.checker.ModelCheckerMessage.*;


/**
 * A table cell renderer for the model checker messages.
 *
 * @author oliver.lieshoff
 *
 * @changed OLI 15.06.2016 - Added.
 */

public class ModelCheckerMessageTableCellRenderer extends DefaultTableCellRenderer {

    /**
     * @changed OLI 15.06.2016 - Added.
     */
    @Override public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        final Component c = super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
        Level level = (Level) table.getModel().getValueAt(row, 0);
        c.setBackground(level == Level.ERROR ? new Color(244, 165, 191) : new Color(255, 245,
                186));
        return c;
    }

}