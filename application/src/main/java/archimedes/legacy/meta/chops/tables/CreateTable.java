/*
 * CreateTable.java
 *
 * 11.12.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.meta.chops.tables;

import archimedes.legacy.meta.MetaDataTable;
import archimedes.legacy.meta.chops.AbstractTableChangeOperation;
import archimedes.legacy.meta.chops.ScriptSectionType;

/**
 * A change operation to create a missing table.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 11.12.2015 - Added.
 */

public class CreateTable extends AbstractTableChangeOperation {

	/**
	 * Creates a new change operation to create a missing table.
	 *
	 * @param table The information about the table to create.
	 *
	 * @changed OLI 11.12.2015 - Added.
	 */
	public CreateTable(MetaDataTable table) {
		super(table, ScriptSectionType.ADD_TABLES);
	}

	/**
	 * @changed OLI 11.12.2015 - Added.
	 */
	@Override
	public String toString() {
		return "CreateTable(table=\"" + this.getTable().getName() + "\")";
	}

}