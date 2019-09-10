/*
 * CodeGeneratorStringResource.java
 *
 * 01.11.2011
 *
 * (c) by O.Lieshoff
 */

package gengen.generator;

/**
 * Implementierungen dieses Interfaces liefern die entsprechenden Stringressourcen (eventuell lokalisiert), die zur
 * Erzeugung der Codefragmente notwendig sind.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
 */

public interface CodeGeneratorStringResource {

	/**
	 * Liefert den String, der auf eine Erweiterung durch eine neue Codepassage hinweist.
	 *
	 * @return Der String, der auf eine Erweiterung durch eine neue Codepassage hinweist.
	 *
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	abstract public String getAdded();

	/**
	 * Liefert den String, der zur Vervollst&auml;ndigung von Kommentaren angegeben werden soll.
	 *
	 * @return Der String, der zur Vervollst&auml;ndigung von Kommentaren angegeben werden soll.
	 *
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	abstract public String getCompleteCommentString();

	/**
	 * Liefert den Kommentar zum parameterlosen Konstruktor.
	 *
	 * @return Der Kommentar zum parameterlosen Konstruktor.
	 *
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	abstract public String getSimpleConstructorComment();

	/**
	 * Liefert den Kommentar zur Testklasse.
	 *
	 * @return Der Kommentar zur Testklasse.
	 *
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	abstract public String getTestClassComment();

}