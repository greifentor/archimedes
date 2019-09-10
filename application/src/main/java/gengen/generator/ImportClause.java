/*
 * ImportClause.java
 *
 * 04.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

/**
 * Interface zur Repr&auml;sentation einer Java-Importklausel.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
 */

public interface ImportClause extends Comparable {

	/**
	 * Liefert den Inhalt der Importklausel (den Packagenamen mit ".*"-Erweiterung).
	 *
	 * @return Der Inhalt der Importklausel (z. B. der Packagename mit ".*"-Erweiterung).
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	public String getImportContent();

	/**
	 * Pr&uuml;ft, ob der Import statisch ist.
	 *
	 * @return <TT>true</TT>, falls der Import statisch ist, sonst <TT>false</TT>.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	public boolean isStatic();

}