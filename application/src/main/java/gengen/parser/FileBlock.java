/*
 * FileBlock.java
 *
 * 22.04.2010
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.parser;

/**
 * Interface zur Definition der Methoden eines File-Blocks. Hinter Implementierungen dieses Typs verbergen sich z. B.
 * Dateieingangskommentare, Klassenkommentare, Package-Hinweise, Import-Klauseln und der Klassen-Code selbst.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
 */

public interface FileBlock {

	/**
	 * Liefert den Inhalt des FileBlocks.
	 *
	 * @return Der Inhalt des FileBlocks.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public String getContent();

	/**
	 * Liefert den Typ des FileBlocks (z. B. HEADER, IMPORT, PACKAGE usw.).
	 *
	 * @return Der Typ des FileBlocks.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public String getType();

	/**
	 * Setzt den angegebenen Wert als neuen Inhalt zum FileBlock.
	 *
	 * @param c Der neue Inhalt zum FileBlock.
	 * @throws IllegalArgumentException Falls der Inhalt als <TT>null</TT>-Referenz &uuml;bergeben wird.
	 * @precondition c != <TT>null</TT>
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void setContent(String t) throws IllegalArgumentException;

	/**
	 * Setzt den angegebenen Wert als neuen Typ zum FileBlock (z. B. HEADER, IMPORT, PACKAGE usw.).
	 *
	 * @param t Der neue Typ zum FileBlock.
	 * @throws IllegalArgumentException Falls der Typ als <TT>null</TT>-Referenz &uuml;bergeben wird.
	 * @precondition t != <TT>null</TT>
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void setType(String t) throws IllegalArgumentException;

}
