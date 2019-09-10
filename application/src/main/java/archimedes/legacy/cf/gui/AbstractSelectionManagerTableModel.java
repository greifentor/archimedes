/*
 * AbstractSelectionManagerTableModel.java
 *
 * 21.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;


import java.util.*;

import javax.swing.table.*;


/**
 * A base class for table models which are using the selection manager logic.
 *
 * @param <T> The type of object which are to select via the table model.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 21.04.2018 - Added.
 */

abstract public class AbstractSelectionManagerTableModel<T> extends AbstractTableModel {

    protected SelectionManager<T>[] selectionManagers = null;

    /**
     * Creates a new table model for the passed object array.
     *
     * @param objects The objects which are represented by the table model.
     */
    public AbstractSelectionManagerTableModel(T[] objects) {
        super();
        this.selectionManagers = new SelectionManager[objects.length];
        for (int i = 0; i < objects.length; i++) {
            this.selectionManagers[i] = new SelectionManager<T>(objects[i],
                    isObjectSelectedWhileInitialization(objects[i]));
        }
    }

    @Override public int getRowCount() {
        return this.selectionManagers.length;
    }

    @Override public boolean isCellEditable(int row, int column) {
        return (column == 1);
    }

    /**
     * Returns an array with the selected objects.
     *
     * @return An array with the selected objects.
     */
    public List<T> getSelectedObjects() {
        List<T> l = new LinkedList<T>();
        for (SelectionManager<T> sm : this.selectionManagers) {
            if (sm.isSelected()) {
                l.add(sm.getObject());
            }
        }
        return l;
    }

    /**
     * Returns "true" if the object is selected by default when initializing the selection
     * manager.
     *
     * @param object The object whose initial selection state is to return.
     * @return "true" if the object is selected by default when initializing the selection
     *         manager.
     */
    abstract public boolean isObjectSelectedWhileInitialization(T object);

    /**
     * Sets the selected status for all elements in the table model to the passed value.
     *
     * @param selected The new selected state for all elements of the table model.
     */
    public void setSelectedAll(boolean selected) {
        for (SelectionManager<T> m : this.selectionManagers) {
            m.setSelected(selected);
        }
    }

    @Override public void setValueAt(Object o, int row, int column) {
        if (column == 1) {
            this.selectionManagers[row].setSelected(((Boolean) o).booleanValue());
        }
    }

}