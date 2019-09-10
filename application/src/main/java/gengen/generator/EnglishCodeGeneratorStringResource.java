/*
 * EnglishCodeGeneratorStringResource.java
 *
 * 01.11.2011
 *
 * (c) by O.Lieshoff
 */

package gengen.generator;

/**
 * Eine Implementierung des Interfaces <TT>CodeGeneratorStringResource<TT> zur Erzeugung von Kommentaren in englischer
 * Sprache.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
 */

public class EnglishCodeGeneratorStringResource implements CodeGeneratorStringResource {

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getAdded() {
		return "Added";
	}

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getCompleteCommentString() {
		return "Complete comment";
	}

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getSimpleConstructorComment() {
		return "Creates an instance of class <TT>$CLASSNAME$</TT> with defaults";
	}

	/**
	 * @changed OLI 01.11.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getTestClassComment() {
		return "Tests for the class <TT>$CLASSNAME$</TT>";
	}

}