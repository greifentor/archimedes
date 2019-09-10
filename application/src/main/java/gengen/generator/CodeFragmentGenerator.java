/*
 * GeneratorUtil.java
 *
 * 05.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

import static corentx.util.Checks.ensure;

import java.util.List;

import corentx.dates.PDate;
import corentx.util.Str;
import gengen.metadata.ClassMetaData;

/**
 * Generator f&uuml;r festgef&uuml;gte Codefragemente.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
 */

public class CodeFragmentGenerator {

	public static final String EOL = "\n";

	private CodeGeneratorStringResource stringResource = new GermanCodeGeneratorStringResource();
	private PDate generationDate = null;
	private String authorName = null;
	private String authorToken = null;
	private String className = null;
	private String packageName = null;
	private String vendorName = null;

	/**
	 * Liefert ein K&uuml;rzel zum angegebenen Namen. In der Regel hat das K&uuml;rzel eine L&auml;nge von drei Zeichen
	 * und wird aus dem Anfangsbuchstaben des Vornamens und den beiden ersten Buchstaben des Nachnamens gebildet.
	 * Fehlende Zeichen werden durch 'X'e ersetzt. Vor- und Nachnamen k&ouml;nnen durch Leerzeichen, Kommata,
	 * Unterstriche oder Punkte voneinander abgesetzt sein. Es werden nur der erste Vorname und der letzte Nachname
	 * betrachtet.
	 *
	 * @param name Der Name zu dem das K&uuml;rzel gebildet werden soll.
	 * @return Ein K&uuml;rzel zum angegebenen Namen nach der obenstehenden Regel.
	 * @throws IllegalArgumentException Falls der Name als <TT>null</TT>-Referenz oder leer &uuml;bergeben wird.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 * @changed OLI 24.03.2012 - Von 'getNameToken' umbenannt.
	 */
	public static String createNameToken(String name) {
		ensure(name != null, "name cannot be null.");
		ensure(name.length() > 0, "name cannot be empty.");
		int nameCount = 0;
		List<String> names = null;
		String lastName = null;
		String token = "";
		names = Str.splitToList(name, " .,_");
		nameCount = names.size();
		token = "" + names.get(0).charAt(0);
		if (nameCount > 1) {
			lastName = names.get(nameCount - 1);
			if (lastName.length() > 1) {
				token = token + lastName.substring(0, 2);
			} else {
				token = token + lastName.charAt(0);
			}
		}
		while (token.length() < 3) {
			token = token.concat("X");
		}
		return token.toUpperCase();
	}

	/**
	 * Generiert eine neue Instanz des Codefragmentgenerators mit den angegebenen Parametern.
	 *
	 * @param authorName     Der Name des Entwicklers, der als Autor in den Codefragmenten angezeigt werden soll.
	 * @param className      Der Name der Klasse, f&uuml;r die die Fragmente erzeugt werden sollen.
	 * @param packageName    Der Name des Packages in dem die Klasse liegt, f&uuml;r die die Codefragmente generiert
	 *                       werden sollen.
	 * @param vendorName     Der Name des Herstellers der Codefragmente.
	 * @param generationDate Das Datum, dass in den Codefragmenten als Generierungsdatum angegeben werden soll.
	 * @throws IllegalArgumentException Falls einer der Parameter als <TT>null</TT>-Referenz oder leer &uuml;bergeben
	 *                                  wird.
	 * @precondition authorName != <TT>null</TT>
	 * @precondition authorName.length() &gt; <TT>0</TT>
	 * @precondition className != <TT>null</TT>
	 * @precondition className.length() &gt; <TT>0</TT>
	 * @precondition generationDate != <TT>null</TT>
	 * @precondition packageName != <TT>null</TT>
	 * @precondition packageName.length() &gt; <TT>0</TT>
	 * @precondition vendorName != <TT>null</TT>
	 * @precondition vendorName.length() &gt; <TT>0</TT>
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public CodeFragmentGenerator(String authorName, String className, String packageName, String vendorName,
			PDate generationDate) throws IllegalArgumentException {
		super();
		ensure(authorName != null, "authors name cannot be null.");
		ensure(authorName.length() > 0, "authors name cannot be null.");
		ensure(className != null, "class name cannot be null.");
		ensure(className.length() > 0, "class name cannot be null.");
		ensure(generationDate != null, "generation date cannot be null.");
		ensure(packageName != null, "package name cannot be null.");
		ensure(packageName.length() > 0, "package name cannot be null.");
		ensure(vendorName != null, "vendors name cannot be null.");
		ensure(vendorName.length() > 0, "vendors name cannot be null.");
		this.authorName = authorName;
		this.className = className;
		this.generationDate = generationDate;
		this.packageName = packageName;
		this.vendorName = vendorName;
	}

	/**
	 * Generiert eine neue Instanz des Codefragmentgenerators mit den angegebenen Parametern.
	 *
	 * @param authorName     Der Name des Entwicklers, der als Autor in den Codefragmenten angezeigt werden soll.
	 * @param className      Der Name der Klasse, f&uuml;r die die Fragmente erzeugt werden sollen.
	 * @param packageName    Der Name des Packages in dem die Klasse liegt, f&uuml;r die die Codefragmente generiert
	 *                       werden sollen.
	 * @param vendorName     Der Name des Herstellers der Codefragmente.
	 * @param generationDate Das Datum, dass in den Codefragmenten als Generierungsdatum angegeben werden soll.
	 * @throws IllegalArgumentException Falls einer der Parameter als <TT>null</TT>-Referenz oder leer &uuml;bergeben
	 *                                  wird.
	 * @precondition authorName != <TT>null</TT>
	 * @precondition authorName.length() &gt; <TT>0</TT>
	 * @precondition className != <TT>null</TT>
	 * @precondition className.length() &gt; <TT>0</TT>
	 * @precondition generationDate != <TT>null</TT>
	 * @precondition packageName != <TT>null</TT>
	 * @precondition packageName.length() &gt; <TT>0</TT>
	 * @precondition vendorName != <TT>null</TT>
	 * @precondition vendorName.length() &gt; <TT>0</TT>
	 *
	 * @changed OLI 24.02.2011 - Hinzugef&uuml;gt.
	 */
	public CodeFragmentGenerator(String authorName, String className, String packageName, String vendorName,
			PDate generationDate, String authorToken) throws IllegalArgumentException {
		this(authorName, className, packageName, vendorName, generationDate);
		this.authorToken = authorToken;
	}

	/**
	 * Generiert eine neue Instanz des Codefragmentgenerators mit den angegebenen Parametern.
	 *
	 * @param authorName     Der Name des Entwicklers, der als Autor in den Codefragmenten angezeigt werden soll.
	 * @param className      Der Name der Klasse, f&uuml;r die die Fragmente erzeugt werden sollen.
	 * @param packageName    Der Name des Packages in dem die Klasse liegt, f&uuml;r die die Codefragmente generiert
	 *                       werden sollen.
	 * @param vendorName     Der Name des Herstellers der Codefragmente.
	 * @param generationDate Das Datum, dass in den Codefragmenten als Generierungsdatum angegeben werden soll.
	 * @throws IllegalArgumentException Falls einer der Parameter als <TT>null</TT>-Referenz oder leer &uuml;bergeben
	 *                                  wird.
	 * @precondition authorName != <TT>null</TT>
	 * @precondition authorName.length() &gt; <TT>0</TT>
	 * @precondition className != <TT>null</TT>
	 * @precondition className.length() &gt; <TT>0</TT>
	 * @precondition generationDate != <TT>null</TT>
	 * @precondition packageName != <TT>null</TT>
	 * @precondition packageName.length() &gt; <TT>0</TT>
	 * @precondition vendorName != <TT>null</TT>
	 * @precondition vendorName.length() &gt; <TT>0</TT>
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public CodeFragmentGenerator(String authorName, String className, String packageName, String vendorName,
			PDate generationDate, CodeGeneratorStringResource stringResource) throws IllegalArgumentException {
		this(authorName, className, packageName, vendorName, generationDate);
		this.stringResource = stringResource;
	}

	/**
	 * Generiert eine neue Instanz des Codefragmentgenerators mit den angegebenen Parametern.
	 *
	 * @param authorName     Der Name des Entwicklers, der als Autor in den Codefragmenten angezeigt werden soll.
	 * @param className      Der Name der Klasse, f&uuml;r die die Fragmente erzeugt werden sollen.
	 * @param packageName    Der Name des Packages in dem die Klasse liegt, f&uuml;r die die Codefragmente generiert
	 *                       werden sollen.
	 * @param vendorName     Der Name des Herstellers der Codefragmente.
	 * @param generationDate Das Datum, dass in den Codefragmenten als Generierungsdatum angegeben werden soll.
	 * @throws IllegalArgumentException Falls einer der Parameter als <TT>null</TT>-Referenz oder leer &uuml;bergeben
	 *                                  wird.
	 * @precondition authorName != <TT>null</TT>
	 * @precondition authorName.length() &gt; <TT>0</TT>
	 * @precondition className != <TT>null</TT>
	 * @precondition className.length() &gt; <TT>0</TT>
	 * @precondition generationDate != <TT>null</TT>
	 * @precondition packageName != <TT>null</TT>
	 * @precondition packageName.length() &gt; <TT>0</TT>
	 * @precondition vendorName != <TT>null</TT>
	 * @precondition vendorName.length() &gt; <TT>0</TT>
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public CodeFragmentGenerator(String authorName, String className, String packageName, String vendorName,
			PDate generationDate, CodeGeneratorStringResource stringResource, String authorToken)
			throws IllegalArgumentException {
		this(authorName, className, packageName, vendorName, generationDate, stringResource);
		this.authorToken = authorToken;
	}

	/**
	 * Liefert ein @changed-Tag mit dem Autor, dem Generierungsdatum und dem angegeben Kommentar.
	 *
	 * @param comment Der Kommentar zum @changed-Tag.
	 * @return Das @changed-Tag mit Autorenk&uuml;rzel, Datum und Kommentar.
	 *
	 * @changed OLI 06.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateAtChangedTag(String comment) {
		StringBuffer sb = new StringBuffer();
		sb.append("@changed ").append(this.getAuthorToken()).append(" ").append(this.generationDate).append(" - ")
				.append(comment);
		return sb;
	}

	/**
	 * Liefert das Autorentoken, das innerhalb des Fragmentgenerators benutzt wird.
	 *
	 * @return Das Autorentoken, das innerhalb des Fragmentgenerators benutzt wird.
	 *
	 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
	 */
	public String getAuthorToken() {
		if (this.authorToken != null) {
			return this.authorToken;
		}
		return createNameToken(this.authorName);
	}

	/**
	 * Generiert einen Klassenkommentarblock mit Angaben zum Autor und dem angegebenen Kommentar.
	 *
	 * @param comment Der Kommentar, der in den Kommentarblock eingef&uuml;gt werden soll.
	 * @return Ein Klassenkommentarblock mit Angaben zum Autor und dem angegebenen Kommentar.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateClassCommentBlock(String comment) {
		StringBuffer sb = new StringBuffer();
		comment = this.replaceEmptyCommentWithTodoComment(comment);
		sb.append("/**").append(EOL);
		sb.append(" * ").append(comment).append(EOL);
		sb.append(" *").append(EOL);
		sb.append(" * @author ").append(this.authorName).append(EOL);
		sb.append(" *").append(EOL);
		sb.append(" * ").append(this.generateAtChangedTag(this.stringResource.getAdded())).append(".").append(EOL);
		sb.append(" */").append(EOL);
		return sb;
	}

	/**
	 * Generiert eine Klassenendezeile.
	 *
	 * @return Eine Klassenendezeile.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateClassEndLine() {
		StringBuffer sb = new StringBuffer();
		return sb.append("}");
	}

	/**
	 * Generiert einen Klassenkopfkommentar.
	 *
	 * @return Der Klassenkopfkommentar.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateClassHeaderComment() {
		StringBuffer sb = new StringBuffer();
		sb.append("/*").append(EOL);
		sb.append(" * ").append(this.className).append(".java").append(EOL);
		sb.append(" *").append(EOL);
		sb.append(" * ").append(this.generationDate).append(EOL);
		sb.append(" *").append(EOL);
		sb.append(" * (c) by ").append(this.vendorName).append(EOL);
		sb.append(" *").append(EOL);
		sb.append(" */").append(EOL);
		return sb;
	}

	/**
	 * Generiert eine Klassenkopfzeile.
	 *
	 * @return Eine Klassenkopfzeile.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateClassHeadLine() {
		StringBuffer sb = new StringBuffer();
		return sb.append("public class ").append(this.className).append(" {").append(EOL);
	}

	/**
	 * Generiert eine Leerzeile
	 *
	 * @return Eine Leerzeile
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateEmptyLine() {
		StringBuffer sb = new StringBuffer();
		return sb.append(EOL);
	}

	/**
	 * Erzeugt einen Importblock anhand der &uuml;bergebenen Importklauseln.
	 *
	 * @param imports Die Importklauseln, aus denen der Importblock zusammengestellt werden soll.
	 * @return Ein Importblock mit den angegebenen Importklauseln.
	 * @throws IllegalArgumentException Falls die Liste der Importklauseln als <TT>null</TT>-Referenz &uuml;bergeben
	 *                                  wird.
	 * @precondition imports != <TT>null</TT>
	 *
	 * @changed OLI 05.01.2011 - Hinzugefuegt.
	 */
	public StringBuffer generateImportBlock(List<ImportClause> imports) {
		ensure(imports != null, "list of imports cannot be null to generate an import block.");
		ImportClause importClause = null;
		int i = 0;
		int leni = 0;
		String lastPrefix = "";
		String prefix = "";
		StringBuffer sb = new StringBuffer();
		for (i = 0, leni = imports.size(); i < leni; i++) {
			importClause = imports.get(i);
			prefix = Str.nextSubstring(importClause.getImportContent(), ".");
			if ((!lastPrefix.equals("")) && (!lastPrefix.equals(prefix))) {
				sb.append(this.generateEmptyLine());
			}
			lastPrefix = prefix;
			sb.append(this.generateImportClause(importClause));
		}
		return sb;
	}

	/*
	 * Fuegt die angegebene Importklausel an den StringBuffer an.
	 *
	 * @changed OLI 05.01.2011 - Hinzugefuegt.
	 */
	private StringBuffer generateImportClause(ImportClause importClause) {
		StringBuffer sb = new StringBuffer();
		sb.append("import ");
		if (importClause.isStatic()) {
			sb.append("static ");
		}
		sb.append(importClause.getImportContent()).append(";").append(EOL);
		return sb;
	}

	/**
	 * Generiert eine Package-Zeile.
	 *
	 * @return Eine Package-Zeile.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generatePackageLine() {
		StringBuffer sb = new StringBuffer();
		return sb.append("package ").append(this.packageName).append(";").append(EOL);
	}

	/**
	 * Generiert eine Package-Zeile zur angegebenen Tabelle.
	 *
	 * @return Eine Package-Zeile zur angegebenen Tabelle.
	 *
	 * @changed OLI 03.11.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generatePackageLine(ClassMetaData table) {
		StringBuffer sb = new StringBuffer();
		sb.append("package ").append(table.getModel().getBasePackageName());
		if ((table.getPackageName() != null) && !table.getPackageName().isEmpty()) {
			sb.append(".").append(table.getPackageName());
		}
		sb.append(";").append(EOL);
		return sb;
	}

	/**
	 * Generiert ein TODO-Tag, das auf die Vervollst&auml;ndigung eines Kommentars hinweist.
	 *
	 * @return Ein TODO-Tag, das auf die Vervollst&auml;ndigung eines Kommentars hinweist.
	 *
	 * @changed OLI 07.01.2011 - Hinzugef&uuml;gt.
	 */
	public StringBuffer generateTodoComplete() {
		StringBuffer sb = new StringBuffer();
		return sb.append("TODO ").append(this.getAuthorToken()).append(" ").append(this.generationDate).append(" - ")
				.append(this.stringResource.getCompleteCommentString()).append(".");
	}

	/**
	 * Liefert den Namen des Autors, der in den Codefragementen angegeben werden soll.
	 *
	 * @return Der Name des Autors, der in den Codefragementen angegeben werden soll.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public String getAuthorName() {
		return this.authorName;
	}

	/**
	 * Liefert den Namen der Klasse, f&uuml;r die die Codefragmente generiert werden sollen.
	 *
	 * @return Der Name der Klasse, f&uuml;r die die Codefragmente generiert werden sollen.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public String getClassName() {
		return this.className;
	}

	/**
	 * Liefert das Datum, das in den Codefragmenten als Generierungsdatum angegeben werden soll.
	 *
	 * @return Das Datum, das in den Codefragmenten als Generierungsdatum angegeben werden soll.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public PDate getGenerationDate() {
		return this.generationDate;
	}

	/**
	 * Liefert den Namen des Packages, in dem die Klasse liegt, f&uuml;r die die Codefragemente generiert werden sollen.
	 *
	 * @return Der Name des Packages, in dem die Klasse liegt, f&uuml;r die die Codefragemente generiert werden sollen.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public String getPackageName() {
		return this.packageName;
	}

	/**
	 * Liefert den Namen des Herstellers, der in den Codefragmenten angegeben werden soll.
	 *
	 * @return Der Name des Herstellers, der in den Codefragmenten angegeben werden soll.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	public String getVendorName() {
		return this.vendorName;
	}

	/*
	 * Ersetzt einen leeren Kommentar durch eine TODO-Phrase.
	 *
	 * @changed OLI 05.01.2010 - Hinzugef&uuml;gt.
	 */
	private String replaceEmptyCommentWithTodoComment(String comment) {
		if ((comment == null) || (comment.length() == 0)) {
			comment = this.generateTodoComplete().toString();
		}
		return comment;
	}

}