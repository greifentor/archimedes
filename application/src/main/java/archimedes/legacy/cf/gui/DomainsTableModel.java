/*
 * DomainsTableModel.java
 *
 * 19.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;

import archimedes.legacy.acf.param.DomainParamIds;
import archimedes.legacy.model.DomainModel;
import baccara.gui.GUIBundle;

/**
 * A table model which allows to select domains for code generation.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 19.04.2018 - Added.
 */

public class DomainsTableModel extends AbstractSelectionManagerTableModel<DomainModel> {

	private static final String[] COLUMN_HEADER_RESOURCE_NAMES = new String[] {
			"artifact.domains.configuration.table.column.name",
			"artifact.domains.configuration.table.column.generate.code" };
	private GUIBundle guiBundle = null;

	/**
	 * Creates a new table model for the passed code domain model array.
	 *
	 * @param domains   The domains which are represented by the table model.
	 * @param guiBundle A bundle with GUI information.
	 */
	public DomainsTableModel(DomainModel[] domains, GUIBundle guiBundle) {
		super(domains);
		this.guiBundle = guiBundle;
	}

	@Override
	public Class<?> getColumnClass(int column) {
		if (column == 1) {
			return Boolean.class;
		}
		return String.class;
	}

	@Override
	public int getColumnCount() {
		return COLUMN_HEADER_RESOURCE_NAMES.length;
	}

	@Override
	public String getColumnName(int column) {
		return this.guiBundle.getResourceText(COLUMN_HEADER_RESOURCE_NAMES[column]);
	}

	@Override
	public Object getValueAt(int row, int column) {
		if (column == 1) {
			if (isSuppressedByOption(this.selectionManagers[row].getObject())) {
				return false;
			}
			return this.selectionManagers[row].isSelected();
		}
		return this.selectionManagers[row].getObject().getName();
	}

	@Override
	public boolean isObjectSelectedWhileInitialization(DomainModel object) {
		if (isSuppressedByOption(object)) {
			return false;
		}
		return true;
	}

	private boolean isSuppressedByOption(DomainModel object) {
		return object.isOptionSet(DomainParamIds.NO_CODE_GENERATION);
	}

	@Override
	public void setValueAt(Object o, int row, int column) {
		if ((column == 1) && (isSuppressedByOption(this.selectionManagers[row].getObject()))) {
			return;
		}
		super.setValueAt(o, row, column);
	}

}