/*
 * StaticImportClauseTest.java
 *
 * 04.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests zur Klasse <TT>StaticImportClause</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
 */

public class StaticImportClauseTest {

	private static final String PACKAGENAME = "a.package";

	private StaticImportClause staticImportClause = null;
	private ImportClause importClauseToCompare = null;

	/**
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Before
	public void setUp() throws Exception {
		this.staticImportClause = new StaticImportClause(PACKAGENAME);
	}

	/**
	 * Test der Methode <TT>compareTo(Object)</TT>.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testCompareToObject() throws Exception {
		// Vergleich unterschiedlicher DefaultImportklauseln.
		this.importClauseToCompare = new StaticImportClause("another.package");
		assertEquals(-6, this.staticImportClause.compareTo(this.importClauseToCompare));
		assertEquals(6, this.importClauseToCompare.compareTo(this.staticImportClause));
		// Vergleich gleicher DefaultImportklauseln.
		this.importClauseToCompare = new StaticImportClause(PACKAGENAME);
		assertEquals(0, this.staticImportClause.compareTo(this.importClauseToCompare));
		// Vergleich bei gleichem Inhalt gegen eine statische Importklausel.
		this.importClauseToCompare = new DefaultImportClause(PACKAGENAME);
		assertEquals(-1, this.staticImportClause.compareTo(this.importClauseToCompare));
		// Vergleich bei gleichen Praefices gegen eine statische Importklausel.
		this.importClauseToCompare = new DefaultImportClause(PACKAGENAME.concat(".Assert"));
		assertEquals(-1, this.staticImportClause.compareTo(this.importClauseToCompare));
	}

	/**
	 * Test des Konstruktors <TT>StaticImportClause(String)</TT>.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testStaticImportClauseString() throws Exception {
		assertEquals(PACKAGENAME, this.staticImportClause.getImportContent());
		assertTrue(this.staticImportClause.isStatic());
	}

	/**
	 * Test des Konstruktors <TT>StaticImportClause(String)</TT> bei Angabe eines leeren Importinhalts.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStaticImportClauseStringFailEmptyImportContent() throws Exception {
		new DefaultImportClause("");
	}

	/**
	 * Test des Konstruktors <TT>StaticImportClause(String)</TT> ohne Angabe eines Importinhalts.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testStaticImportClauseStringFailNoImportContent() throws Exception {
		new DefaultImportClause(null);
	}

}