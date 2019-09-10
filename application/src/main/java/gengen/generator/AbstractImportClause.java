/*
 * AbstractImportClause.java
 *
 * 04.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

import static corentx.util.Checks.ensure;

import corentx.util.Str;

/**
 * Eine Implementierung der grundlegenden Methoden des Interfaces <TT>ImportClause</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
 */

abstract public class AbstractImportClause implements ImportClause {

	private String importContent = null;

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
	public AbstractImportClause(String importContent) throws IllegalArgumentException {
		super();
		ensure(importContent != null, "import content cannot be null.");
		ensure(importContent.length() > 0, "import content cannot be empty.");
		this.importContent = importContent;
	}

	/**
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public int compareTo(Object o) {
		ImportClause importClause = (ImportClause) o;
		int erg = Str.nextSubstring(this.getImportContent(), ".")
				.compareTo(Str.nextSubstring(importClause.getImportContent(), "."));
		if (erg == 0) {
			erg = (this.isStatic() ? 0 : 1) - (importClause.isStatic() ? 0 : 1);
			if (erg == 0) {
				erg = this.getImportContent().compareTo(importClause.getImportContent());
			}
		}
		return erg;
	}

	/**
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getImportContent() {
		return this.importContent;
	}

}