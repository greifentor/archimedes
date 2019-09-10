/*
 * ArtifactGeneratorListTableModel.java
 *
 * 16.10.2013
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;


import archimedes.cf.*;
import archimedes.legacy.cf.ArtifactGenerator;
import baccara.gui.*;

import java.util.*;


/**
 * A table model for an array of code generators.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 16.10.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 * @changed OLI 03.04.2018 - Copied and made ready for the artifact generator logic. 
 */

public class ArtifactGeneratorListTableModel
        extends AbstractSelectionManagerTableModel<ArtifactGenerator<?>> {

    private static final String[] COLUMN_HEADER_RESOURCE_NAMES = new String[] {
            "artifact.generators.configuration.table.column.name",
            "artifact.generators.configuration.table.column.active"};
    private GUIBundle guiBundle = null;

    /**
     * Creates a new table model for the passed code generator array.
     *
     * @param artifactGenerators The code generator arrays which is represented by the table
     *         model.
     * @param guiBundle A bundle with GUI information.
     */
    public ArtifactGeneratorListTableModel(ArtifactGenerator<?>[] artifactGenerators,
            GUIBundle guiBundle) {
        super(artifactGenerators);
        this.guiBundle = guiBundle;
        Arrays.sort(this.selectionManagers, new ArtifactGeneratorComparator(this.guiBundle));
    }

    @Override public Class<?> getColumnClass(int column) {
        if (column == 1) {
            return Boolean.class;
        }
        return String.class;
    }

    @Override public int getColumnCount() {
        return COLUMN_HEADER_RESOURCE_NAMES.length;
    }

    @Override public String getColumnName(int column) {
        return this.guiBundle.getResourceText(COLUMN_HEADER_RESOURCE_NAMES[column]);
    }

    @Override public Object getValueAt(int row, int column) {
        if (column == 1) {
            return this.selectionManagers[row].isSelected();
        }
        return this.selectionManagers[row].getObject().getName();
    }

    @Override public boolean isObjectSelectedWhileInitialization(ArtifactGenerator<?> object) {
        if (object.isDeprecated()) {
            return false;
        }
        return true;
    }

}


class ArtifactGeneratorComparator
        implements Comparator<SelectionManager<ArtifactGenerator<?>>> {

    private GUIBundle guiBundle = null;

    ArtifactGeneratorComparator(GUIBundle guiBundle) {
        super();
        this.guiBundle = guiBundle;
    }

    @Override public int compare(SelectionManager<ArtifactGenerator<?>> ag0,
            SelectionManager<ArtifactGenerator<?>> ag1) {
        String agn0 = this.guiBundle.getResourceText("artifact.generators.configuration.name."
                + ag0.getObject().getClass().getSimpleName() + ".title");
        String agn1 = this.guiBundle.getResourceText("artifact.generators.configuration.name."
                + ag1.getObject().getClass().getSimpleName() + ".title");
        return agn0.compareToIgnoreCase(agn1);
    }
}