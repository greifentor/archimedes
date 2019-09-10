/*
 * TablesTableModel.java
 *
 * 16.09.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;


import archimedes.acf.param.*;
import archimedes.legacy.acf.param.TableParamIds;
import archimedes.legacy.model.TableModel;
import baccara.gui.*;


/**
 * A table model to represent the Archimedes table models in a swing table.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 23.10.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public class TablesTableModel extends AbstractSelectionManagerTableModel<TableModel> {

    private static final String[] COLUMN_HEADER_RESOURCE_NAMES = new String[] {
            "code.tables.configuration.table.column.name",
            "code.tables.configuration.table.column.generate.code"};
    private GUIBundle guiBundle = null;

    /**
     * Creates a new table model for the passed code table model array.
     *
     * @param tables The tableSelectors which are represented by the table model.
     * @param guiBundle A bundle with GUI information.
     */
    public TablesTableModel(archimedes.legacy.model.TableModel[] tables,
            GUIBundle guiBundle) {
        super(tables);
        this.guiBundle = guiBundle;
    }

    private boolean isTableUnselectedByStereoType(archimedes.legacy.model.TableModel table) {
        return false;
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
            if (isSuppressedByOption(this.selectionManagers[row].getObject())) {
                return false;
            }
            return this.selectionManagers[row].isSelected();
        }
        return this.selectionManagers[row].getObject().getName();
    }

    @Override public boolean isObjectSelectedWhileInitialization(TableModel object) {
        if (object.isGenerateCode() && !isTableUnselectedByStereoType(object)
                && !isSuppressedByOption(object)) {
            return true;
        }
        return false;
    }

    private boolean isSuppressedByOption(archimedes.legacy.model.TableModel table) {
        return table.isOptionSet(TableParamIds.NO_CODE_GENERATION);
    }

    @Override public void setValueAt(Object o, int row, int column) {
        if ((column == 1) && (isSuppressedByOption(this.selectionManagers[row].getObject()))) {
            return;
        }
        super.setValueAt(o, row, column);
    }

}