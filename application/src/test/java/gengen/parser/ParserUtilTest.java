/*
 * ParserUtilTest.java
 *
 * 23.04.2010
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Testf&auml;lle zur Klasse <TT>ParserUtil</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
 *
 */

public class ParserUtilTest {

	private static final String exampleCode = "/*\n" + " * ExampleCode.java\n" + " *\n" + " * 23.04.2010\n" + " *\n"
			+ " * (c) by O.Lieshoff\n" + " *\n" + " */\n" + "\n" + "package test;\n" + "\n" + "\n"
			+ "import corentx.dates.*;\n" + "\n" + "\n" + "/**\n" + " * Klasse zum Test des Parsers.\n" + " *\n"
			+ " * @author O.Lieshoff\n" + " *\n" + " * @changed OLI 23.04.2010 - Hinzugef&uuml;gt.\n" + " */\n" + "\n"
			+ "public class ExampleCode extends SomeClass implements AnotherClass {\n" + "\n"
			+ "    /* Referenz auf das Log der Klasse. */\n"
			+ "    private static Log log = Logger.getLogger(ExampleCode.class);\n" + "\n" + "}";

	@Ignore
	// TODO OLI 04.06.2010 - Ignore-Annotation entfernen.
	@Test
	/**
	 * Test der Methode <TT>getFileBlocks(String)</TT>.
	 *
	 * @changed OLI 23.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testGetFileBlocksString() {
		List<FileBlock> lfb = null;
		// Kontrakt-Pruefung.
		try {
			ParserUtil.getFileBlocks(null);
			fail("an IllegalArgumentException should be thrown here.");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("an IllegalArgumentException should be thrown here, but was a " + e.getClass().getName());
		}
		// Nutzung.
		try {
			lfb = ParserUtil.getFileBlocks("");
			assertNotNull(lfb);
			assertEquals(0, lfb.size());
			lfb = ParserUtil.getFileBlocks(exampleCode);
			assertNotNull(lfb);
			assertEquals(11, lfb.size());
			assertEquals("HEADER", lfb.get(0).getType());
			assertEquals("/*\n" + " * ExampleCode.java\n" + " *\n" + " * 23.04.2010\n" + " *\n"
					+ " * (c) by O.Lieshoff\n" + " *\n" + " */\n", lfb.get(0).getContent());
			assertEquals("EMPTYLINE", lfb.get(1).getType());
			assertEquals("", lfb.get(1).getContent());
			assertEquals("PACKAGE", lfb.get(2).getType());
			assertEquals("package test;", lfb.get(2).getContent());
			assertEquals("EMPTYLINE", lfb.get(3).getType());
			assertEquals("", lfb.get(3).getContent());
			assertEquals("EMPTYLINE", lfb.get(4).getType());
			assertEquals("", lfb.get(4).getContent());
			assertEquals("IMPORT", lfb.get(5).getType());
			assertEquals("import corentx.dates.*;", lfb.get(5).getContent());
			assertEquals("EMPTYLINE", lfb.get(6).getType());
			assertEquals("", lfb.get(6).getContent());
			assertEquals("EMPTYLINE", lfb.get(7).getType());
			assertEquals("", lfb.get(7).getContent());
			assertEquals("COMMENT", lfb.get(8).getType());
			assertEquals("", lfb.get(8).getContent());
			assertEquals("EMPTYLINE", lfb.get(9).getType());
			assertEquals("", lfb.get(9).getContent());
			assertEquals("CLASS", lfb.get(10).getType());
			assertEquals(
					"public class ExampleCode extends SomeClass implements AnotherClass " + "{\n" + "\n"
							+ "    /* Referenz auf das Log der Klasse. */\n"
							+ "    private static Log log = Logger.getLogger(ExampleCode.class);\n" + "\n" + "}",
					lfb.get(10).getContent());
		} catch (Exception e) {
			e.printStackTrace();
			fail("no exception should be thrown here, but was a " + e.getClass().getName());
		}
	}

}
