/*
 * DefaultFileBlockTest.java
 *
 * 22.04.2010
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Testf&auml;lle zur Klasse <TT>DefaultFileBlock</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
 *
 */

public class DefaultFileBlockTest {

	@Test
	/**
	 * Check des Typs der Implementierung.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testCheckInstances() {
		try {
			assertTrue(new DefaultFileBlock("a", "b") instanceof FileBlock);
		} catch (Exception e) {
			e.printStackTrace();
			fail("no exception should be thrown here, but was a " + e.getClass().getName());
		}
	}

	@Test
	/**
	 * Test der Methode <TT>Constructor(String, String)</TT>.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testConstructorStringString() {
		DefaultFileBlock dfb = null;
		// Kontrakt-Pruefung.
		try {
			new DefaultFileBlock(null, "b");
			fail("an IllegalArgumentException should be thrown here.");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("an IllegalArgumentException should be thrown here, but was a " + e.getClass().getName());
		}
		try {
			new DefaultFileBlock("a", null);
			fail("an IllegalArgumentException should be thrown here.");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("an IllegalArgumentException should be thrown here, but was a " + e.getClass().getName());
		}
		// Nutzung.
		try {
			dfb = new DefaultFileBlock("a", "b");
			assertNotNull(dfb);
			assertEquals("b", dfb.getContent());
			assertEquals("a", dfb.getType());
		} catch (Exception e) {
			e.printStackTrace();
			fail("no exception should be thrown here, but was a " + e.getClass().getName());
		}
	}

	@Test
	/**
	 * Test der Methode <TT>equals(Object)</TT>.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testEqualsObject() {
		DefaultFileBlock dfb0 = new DefaultFileBlock("a", "b");
		DefaultFileBlock dfb1 = new DefaultFileBlock("a", "b");
		DefaultFileBlock dfb2 = new DefaultFileBlock("a", "b");
		// Kontrakt-Pruefung
		assertTrue(dfb0.equals(dfb0));
		assertFalse(dfb0.equals(null));
		assertFalse(dfb0.equals("a String object."));
		// Zweimal hintereinander
		assertTrue(dfb0.equals(dfb1));
		assertTrue(dfb0.equals(dfb1));
		// Symetrie
		assertTrue(dfb0.equals(dfb1));
		assertTrue(dfb1.equals(dfb0));
		// Gleichheiten
		assertTrue(dfb0.equals(dfb1));
		assertTrue(dfb1.equals(dfb2));
		assertTrue(dfb2.equals(dfb0));
		assertTrue(dfb0.equals(dfb1) && dfb1.equals(dfb2) && dfb2.equals(dfb0));
		// Attribut Content
		dfb1 = new DefaultFileBlock("a", "x");
		assertFalse(dfb0.equals(dfb1));
		// Attribut Type
		dfb1 = new DefaultFileBlock("x", "b");
		assertFalse(dfb0.equals(dfb1));
		// Verschiedene Objekte
		dfb1 = new DefaultFileBlock("x", "b");
		dfb2 = new DefaultFileBlock("a", "x");
		assertFalse(dfb0.equals(dfb1));
		assertFalse(dfb0.equals(dfb1));
		// Symetrie
		assertFalse(dfb0.equals(dfb1));
		assertFalse(dfb1.equals(dfb0));
		// Gleichheiten
		assertFalse(dfb0.equals(dfb1));
		assertFalse(dfb1.equals(dfb2));
		assertFalse(dfb2.equals(dfb0));
		assertFalse(dfb0.equals(dfb1) && dfb1.equals(dfb2) && dfb2.equals(dfb0));
	}

	@Test
	/**
	 * Test der Methode <TT>setContent(String)</TT>.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testSetContentString() {
		DefaultFileBlock dfb = new DefaultFileBlock("a", "b");
		// Kontrakt-Pruefung.
		try {
			dfb.setContent(null);
			fail("an IllegalArgumentException should be thrown here.");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("an IllegalArgumentException should be thrown here, but was a " + e.getClass().getName());
		}
		// Nutzung.
		try {
			dfb = new DefaultFileBlock("a", "b");
			assertEquals("b", dfb.getContent());
			dfb.setContent("x");
			assertEquals("x", dfb.getContent());
		} catch (Exception e) {
			e.printStackTrace();
			fail("no exception should be thrown here, but was a " + e.getClass().getName());
		}
	}

	@Test
	/**
	 * Test der Methode <TT>setType(String)</TT>.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testSetTypeString() {
		DefaultFileBlock dfb = new DefaultFileBlock("a", "b");
		// Kontrakt-Pruefung.
		try {
			dfb.setType(null);
			fail("an IllegalArgumentException should be thrown here.");
		} catch (IllegalArgumentException e) {
		} catch (Exception e) {
			fail("an IllegalArgumentException should be thrown here, but was a " + e.getClass().getName());
		}
		// Nutzung.
		try {
			dfb = new DefaultFileBlock("a", "b");
			assertEquals("a", dfb.getType());
			dfb.setType("x");
			assertEquals("x", dfb.getType());
		} catch (Exception e) {
			e.printStackTrace();
			fail("no exception should be thrown here, but was a " + e.getClass().getName());
		}
	}

	@Test
	/**
	 * Test der Methode <TT>toString()</TT>.
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void testToString() {
		DefaultFileBlock dfb = new DefaultFileBlock("a", "b");
		// Nutzung.
		try {
			assertEquals("a (b)", dfb.toString());
		} catch (Exception e) {
			e.printStackTrace();
			fail("no exception should be thrown here, but was a " + e.getClass().getName());
		}
	}

}
