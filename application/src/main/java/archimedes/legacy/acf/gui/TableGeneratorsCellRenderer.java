/*
 * TableGeneratorsCellEditor.java
 *
 * 23.02.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf.gui;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

import archimedes.legacy.acf.CodeGenerator;

/**
 * A special cell editor for the table of generators while generation configuration.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 23.02.2015 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public class TableGeneratorsCellRenderer implements TableCellRenderer {

	private CodeGenerator[] codeGenerators = null;

	/**
	 * Creates a new table generators cell editor.
	 *
	 * @changed OLI 23.02.2015 - Added.
	 */
	public TableGeneratorsCellRenderer(CodeGenerator[] codeGenerators) {
		super();
		this.codeGenerators = codeGenerators;
	}

	/**
	 * @changed OLI 23.02.2015 - Added.
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel l = new JLabel((String) value.toString());
		if (this.codeGenerators[row].isDeprecated()) {
			l.setForeground(Color.LIGHT_GRAY);
		}
		return l;
	}

}