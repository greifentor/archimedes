/*
 * DefaultImportClauseTest.java
 *
 * 04.01.2011
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests zur Klasse <TT>DefaultImportClause</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
 */

public class DefaultImportClauseTest {

	private static final String PACKAGENAME = "a.package";

	private DefaultImportClause defaultImportClause = null;
	private ImportClause importClauseToCompare = null;

	/**
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Before
	public void setUp() throws Exception {
		this.defaultImportClause = new DefaultImportClause(PACKAGENAME);
	}

	/**
	 * Test der Methode <TT>compareTo(Object)</TT>.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testCompareToObject() throws Exception {
		// Vergleich unterschiedlicher DefaultImportklauseln.
		this.importClauseToCompare = new DefaultImportClause("another.package");
		assertEquals(-6, this.defaultImportClause.compareTo(this.importClauseToCompare));
		assertEquals(6, this.importClauseToCompare.compareTo(this.defaultImportClause));
		// Vergleich gleicher DefaultImportklauseln.
		this.importClauseToCompare = new DefaultImportClause(PACKAGENAME);
		assertEquals(0, this.defaultImportClause.compareTo(this.importClauseToCompare));
		// Vergleich bei gleichem Inhalt gegen eine statische Importklausel.
		this.importClauseToCompare = new StaticImportClause(PACKAGENAME);
		assertEquals(1, this.defaultImportClause.compareTo(this.importClauseToCompare));
		// Vergleich bei gleichen Praefices gegen eine statische Importklausel.
		this.importClauseToCompare = new StaticImportClause(PACKAGENAME.concat(".Assert"));
		assertEquals(1, this.defaultImportClause.compareTo(this.importClauseToCompare));
	}

	/**
	 * Test des Konstruktors <TT>DefaultImportClause(String)</TT>.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testDefaultImportClauseString() throws Exception {
		assertEquals(PACKAGENAME, this.defaultImportClause.getImportContent());
		assertFalse(this.defaultImportClause.isStatic());
	}

	/**
	 * Test des Konstruktors <TT>DefaultImportClause(String)</TT> bei Angabe eines leeren Importinhalts.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDefaultImportClauseStringFailEmptyImportContent() throws Exception {
		new DefaultImportClause("");
	}

	/**
	 * Test des Konstruktors <TT>DefaultImportClause(String)</TT> ohne Angabe eines Importinhalts.
	 *
	 * @changed OLI 04.01.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testDefaultImportClauseStringFailNoImportContent() throws Exception {
		new DefaultImportClause(null);
	}

}