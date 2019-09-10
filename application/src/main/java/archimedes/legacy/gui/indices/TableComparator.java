/*
 * TableComparator.java
 *
 * 19.12.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.gui.indices;

import java.util.Comparator;

import gengen.metadata.ClassMetaData;

/**
 * Comparator zum Vergleich zweier ClassMetaData-Objekte.
 *
 * @author ollie
 *
 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
 */

public class TableComparator implements Comparator<ClassMetaData> {

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public int compare(ClassMetaData cmd0, ClassMetaData cmd1) {
		return cmd0.getName().compareTo(cmd1.getName());
	}

}