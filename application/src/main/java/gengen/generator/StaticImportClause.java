/*
 * StaticImportClause.java
 *
 * 04.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

/**
 * Eine Standardimplementierung f&uuml;r eine statische Importklausel.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
 */

public class StaticImportClause extends AbstractImportClause {

	/**
	 * Generiert eine neue Importklausel mit dem angegebenen Inhalt.
	 *
	 * @param importContent Der Inhalt der Importklausel.
	 * @throws IllegalArgumentException Falls der Inhalt der Importklausel als <TT>null</TT>-Referenz oder leer
	 *                                  angegeben wird.
	 * @precondition importContent != <TT>null</TT>
	 * @precondition importContent.length() &gt; <TT>0</TT>
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	public StaticImportClause(String importContent) throws IllegalArgumentException {
		super(importContent);
	}

	/**
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public boolean isStatic() {
		return true;
	}

}