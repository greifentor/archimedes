/*
 * TableGeneratorsCellEditor.java
 *
 * 23.02.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;


import archimedes.cf.*;
import archimedes.legacy.cf.ArtifactGenerator;

import java.awt.*;

import javax.swing.*;
import javax.swing.table.*;


/**
 * A special cell editor for the table of generators while generation configuration.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 23.02.2015 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 * @changed OLI 03.04.2018 - Copied and made ready for the artifact generator logic. 
 */

public class TableGeneratorsCellRenderer implements TableCellRenderer {

    private ArtifactGenerator<?>[] artifactGenerators = null;

    /**
     * Creates a new table generators cell editor.
     *
     * @param artifactGenerators The artifact generators which are to render.
     *
     * @changed OLI 23.02.2015 - Added.
     */
    public TableGeneratorsCellRenderer(ArtifactGenerator<?>[] artifactGenerators) {
        super();
        this.artifactGenerators = artifactGenerators;
    }

    /**
     * @changed OLI 23.02.2015 - Added.
     */
    @Override public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel l = new JLabel((String) value.toString()); 
        if (this.artifactGenerators[row].isDeprecated()) {
            l.setForeground(Color.LIGHT_GRAY);
        }
        return l;
    }

}