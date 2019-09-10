/*
 * CodeFragmentGeneratorTest.java
 *
 * 05.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Vector;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import corentx.dates.PDate;
import gengen.metadata.ClassMetaData;
import gengen.metadata.ModelMetaData;

/**
 * Tests zur Klasse <TT>CodeFragmentGenerator</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
 */

public class CodeFragmentGeneratorTest {

	private static final PDate GENERATIONDATE = new PDate(19980206);
	private static final String AUTHORNAME = "O.Lieshoff";
	private static final String CLASSNAME = "NewClass";
	private static final String PACKAGENAME = "com.moppelsoft";
	private static final String VENDORNAME = "MoppelSoft Inc.";

	private CodeFragmentGenerator codeFragmentGenerator = null;
	private List<ImportClause> imports = null;
	private ClassMetaData table = null;

	/**
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Before
	public void setUp() throws Exception {
		this.codeFragmentGenerator = new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, VENDORNAME,
				GENERATIONDATE);
		this.imports = new Vector<ImportClause>();
		this.imports.add(new StaticImportClause("corentx.util.Checks.*"));
		this.imports.add(new DefaultImportClause("corentx.dates.*"));
		this.imports.add(new DefaultImportClause("gengen.generator.*"));
		this.imports.add(new DefaultImportClause("java.awt.event.*"));
		this.imports.add(new DefaultImportClause("java.util.*"));
		this.table = this.createClassMetaData("sub.package");
	}

	private ClassMetaData createClassMetaData(String packageName) {
		ClassMetaData cmd = EasyMock.createMock(ClassMetaData.class);
		EasyMock.expect(cmd.getModel()).andReturn(this.createModelMetaData()).anyTimes();
		EasyMock.expect(cmd.getPackageName()).andReturn(packageName).anyTimes();
		EasyMock.replay(cmd);
		return cmd;
	}

	private ModelMetaData createModelMetaData() {
		ModelMetaData mmd = EasyMock.createMock(ModelMetaData.class);
		EasyMock.expect(mmd.getBasePackageName()).andReturn(PACKAGENAME).anyTimes();
		EasyMock.replay(mmd);
		return mmd;
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testCodeFragmentGeneratorStringStringStringPDate() throws Exception {
		assertEquals(AUTHORNAME, this.codeFragmentGenerator.getAuthorName());
		assertEquals(CLASSNAME, this.codeFragmentGenerator.getClassName());
		assertEquals(GENERATIONDATE, this.codeFragmentGenerator.getGenerationDate());
		assertEquals(PACKAGENAME, this.codeFragmentGenerator.getPackageName());
		assertEquals(VENDORNAME, this.codeFragmentGenerator.getVendorName());
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> bei Angabe eines leeren
	 * Autorennamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailEmptyAuthorName() throws Exception {
		new CodeFragmentGenerator("", CLASSNAME, PACKAGENAME, VENDORNAME, GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> bei Angabe eines leeren
	 * Klassennamens
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailEmptyClassName() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, "", PACKAGENAME, VENDORNAME, GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> bei Angabe eines leeren
	 * Packagenamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailEmptyPackageName() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, "", VENDORNAME, GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> bei Angabe eines leeren
	 * Herstellernamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailEmptyVendorName() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, "", GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> ohne Angabe eines
	 * Autorennamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailNoAutorName() throws Exception {
		new CodeFragmentGenerator(null, CLASSNAME, PACKAGENAME, VENDORNAME, GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> ohne Angabe eines
	 * Klassennamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailNoClassName() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, null, PACKAGENAME, VENDORNAME, GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> ohne Angabe eines
	 * Generierungsdatums.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailNoGenerationDate() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, VENDORNAME, null);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> ohne Angabe eines
	 * Packagenamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailNoPackageName() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, null, VENDORNAME, GENERATIONDATE);
	}

	/**
	 * Test des Konstruktors <TT>CodeFragmentGenerator(String, String, String, PDate)</TT> ohne Angabe eines
	 * Herstellernamens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCodeFragmentGeneratorStringStringStringPDateFailNoVendorName() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, null, GENERATIONDATE);
	}

	/**
	 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testConstructorPassingAnEmptyAuthorToken() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, VENDORNAME, GENERATIONDATE, "");
		assertEquals("OLI", this.codeFragmentGenerator.getAuthorToken());
	}

	/**
	 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testConstructorPassingANullPointerAsAuthorToken() throws Exception {
		new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, VENDORNAME, GENERATIONDATE, (String) null);
		assertEquals("OLI", this.codeFragmentGenerator.getAuthorToken());
	}

	/**
	 * Test der Methode <TT>createNameToken(String)</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testCreateNameTokenString() throws Exception {
		// Vorname, Nachname.
		assertEquals("OLI", CodeFragmentGenerator.createNameToken("Oliver Lieshoff"));
		// Vorname abgekuerzt, Nachname.
		assertEquals("OLI", CodeFragmentGenerator.createNameToken("O.Lieshoff"));
		// Vorname, Nachname abgekuerzt.
		assertEquals("OLX", CodeFragmentGenerator.createNameToken("Oliver L"));
		// Nur Vorname.
		assertEquals("OXX", CodeFragmentGenerator.createNameToken("Oliver"));
		// Zwei Vornamen.
		assertEquals("GCA", CodeFragmentGenerator.createNameToken("Gaius Julius Caesar"));
		// Name hat nur ein Zeichen
		assertEquals("YXX", CodeFragmentGenerator.createNameToken("Y"));
	}

	/**
	 * Test der Methode <TT>getNameToken(String)</TT> bei Angabe eines leeren Namens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateNameTokenStringFailEmptyName() throws Exception {
		CodeFragmentGenerator.createNameToken("");
	}

	/**
	 * Test der Methode <TT>getNameToken(String)</TT> ohne Angabe eines Namens.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateNameTokenStringFailNoName() throws Exception {
		CodeFragmentGenerator.createNameToken(null);
	}

	/**
	 * Test der Methode <TT>generateClassCommentBlock(String)</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateClassCommentBlockString() throws Exception {
		StringBuffer sb = null;
		// Ohne Kommentarangabe.
		sb = this.codeFragmentGenerator.generateClassCommentBlock(null);
		assertEquals("/**\n" + " * TODO OLI " + GENERATIONDATE + " - Kommentar vervollst&auml;ndigen.\n" + " *\n"
				+ " * @author O.Lieshoff\n" + " *\n" + " * @changed OLI " + GENERATIONDATE + " - Hinzugef&uuml;gt.\n"
				+ " */\n", sb.toString());
		// Leere Kommentarangabe.
		sb = this.codeFragmentGenerator.generateClassCommentBlock(null);
		assertEquals("/**\n" + " * TODO OLI " + GENERATIONDATE + " - Kommentar vervollst&auml;ndigen.\n" + " *\n"
				+ " * @author O.Lieshoff\n" + " *\n" + " * @changed OLI " + GENERATIONDATE + " - Hinzugef&uuml;gt.\n"
				+ " */\n", sb.toString());
		// Mit Kommentarangabe.
		sb = this.codeFragmentGenerator.generateClassCommentBlock("Ein K&ouml;mmentar.");
		assertEquals("/**\n" + " * Ein K&ouml;mmentar.\n" + " *\n" + " * @author O.Lieshoff\n" + " *\n"
				+ " * @changed OLI " + GENERATIONDATE + " - Hinzugef&uuml;gt.\n" + " */\n", sb.toString());
	}

	/**
	 * Test der Methode <TT>generateClassEndLine()</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateClassEndLine() throws Exception {
		StringBuffer sb = this.codeFragmentGenerator.generateClassEndLine();
		assertEquals("}", sb.toString());
	}

	/**
	 * Test der Methode <TT>generateClassHeaderComment()</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateClassHeaderComment() throws Exception {
		StringBuffer sb = this.codeFragmentGenerator.generateClassHeaderComment();
		assertEquals("/*\n" + " * " + CLASSNAME + ".java\n" + " *\n" + " * " + GENERATIONDATE + "\n" + " *\n"
				+ " * (c) by " + VENDORNAME + "\n" + " *\n" + " */\n", sb.toString());
	}

	/**
	 * Test der Methode <TT>generateClassHeadLine()</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateClassHeadLine() throws Exception {
		StringBuffer sb = this.codeFragmentGenerator.generateClassHeadLine();
		assertEquals("public class " + CLASSNAME + " {\n", sb.toString());
	}

	/**
	 * Test der Methode <TT>generateEmptyLine()</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateEmptyLine() throws Exception {
		StringBuffer sb = this.codeFragmentGenerator.generateEmptyLine();
		assertEquals("\n", sb.toString());
	}

	/**
	 * Test der Methode <TT>generateImportBlock(List&lt;ImportClause&gt;)</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateImportBlockListImportClause() throws Exception {
		// Mit leerer Importliste.
		assertEquals("", this.codeFragmentGenerator.generateImportBlock(new Vector<ImportClause>()).toString());
		// Mit befuellter Importliste.
		assertEquals("import static corentx.util.Checks.*;\n" + "import corentx.dates.*;\n" + "\n"
				+ "import gengen.generator.*;\n" + "\n" + "import java.awt.event.*;\n" + "import java.util.*;\n",
				this.codeFragmentGenerator.generateImportBlock(this.imports).toString());
	}

	/**
	 * Test der Methode <TT>generateImportBlock(List&lt;ImportClause&gt;)</TT> ohne Angabe einer Liste von
	 * Importklauseln.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testGenerateImportBlockListImportClauseFailNoListOfImports() throws Exception {
		this.codeFragmentGenerator.generateImportBlock(null);
	}

	/**
	 * Test der Methode <TT>generatePackageLine()</TT>.
	 *
	 * @changed OLI 05.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGeneratePackageLine() throws Exception {
		StringBuffer sb = this.codeFragmentGenerator.generatePackageLine();
		assertEquals("package com.moppelsoft;\n", sb.toString());
	}

	/**
	 * @changed OLI 03.11.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGeneratePackageLineWithClassParameter() throws Exception {
		StringBuffer sb = this.codeFragmentGenerator.generatePackageLine(this.table);
		assertEquals("package com.moppelsoft.sub.package;\n", sb.toString());
	}

	/**
	 * Test der Methode <TT>generateTodoComplete()</TT>.
	 *
	 * @changed OLI 07.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGenerateTodoComplete() throws Exception {
		assertEquals("TODO OLI " + GENERATIONDATE + " - Kommentar vervollst&auml;ndigen.",
				this.codeFragmentGenerator.generateTodoComplete().toString());
	}

	/**
	 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGetNameTokenWithoutSetAuthorToken() {
		this.codeFragmentGenerator = new CodeFragmentGenerator(AUTHORNAME, CLASSNAME, PACKAGENAME, VENDORNAME,
				GENERATIONDATE, "TKN");
		assertEquals("TKN", this.codeFragmentGenerator.getAuthorToken());
	}

	/**
	 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGetNameTokenWithoutSettingASpecificAuthorToken() {
		assertEquals("OLI", this.codeFragmentGenerator.getAuthorToken());
	}

}