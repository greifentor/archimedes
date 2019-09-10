/*
 * GermanCodeGeneratorStringResource.java
 *
 * 01.11.2011
 *
 * (c) by O.Lieshoff
 */

package gengen.generator;

/**
 * Eine Implementierung des Interfaces <TT>CodeGeneratorStringResource<TT> zur Erzeugung von Kommentaren in deutscher
 * Sprache.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
 */

public class GermanCodeGeneratorStringResource implements CodeGeneratorStringResource {

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getAdded() {
		return "Hinzugef&uuml;gt";
	}

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getCompleteCommentString() {
		return "Kommentar vervollst&auml;ndigen";
	}

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getSimpleConstructorComment() {
		return "Erzeugt eine neue Instanz zur Klasse <TT>$CLASSNAME$</TT> mit Defaultwerten.";
	}

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getTestClassComment() {
		return "Tests zur Klasse <TT>$CLASSNAME$</TT>";
	}

}