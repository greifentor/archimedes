/*
 * OrderDirectionTest.java
 *
 * 09.09.2009
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.metadata;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

/**
 * Testf&auml;lle zur Klasse <TT>OrderDirection</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 09.09.2009 - Hinzugef&uuml;gt.
 *
 */

public class OrderDirectionTest {

	@Test
	/**
	 * Test zur Methode <TT>valueOf(String)</TT>.
	 *
	 * @changed OLI 04.06.2010 - Hinzugef&uuml;gt.
	 */
	public void testValueOfString() {
		// Kontraktpruefung.
		try {
			OrderDirection.valueOf(null);
			fail("a NullPointerException should be thrown.");
		} catch (NullPointerException e) {
		} catch (Exception e) {
			fail("a NullPointerException should be thrown, but was: " + e.getClass().getName());
		}
		// Nutzung.
		try {
			assertEquals(OrderDirection.ASC, OrderDirection.valueOf("ASC"));
			assertEquals(OrderDirection.DESC, OrderDirection.valueOf("DESC"));
		} catch (Exception e) {
			fail("an IllegalArgumentException should be thrown.");
		}
	}

	@Test
	/**
	 * Test zur Methode <TT>values()</TT>.
	 *
	 * @changed OLI 04.06.2010 - Hinzugef&uuml;gt.
	 */
	public void testValues() {
		// Nutzung.
		try {
			assertEquals(2, OrderDirection.values().length);
			assertEquals(OrderDirection.ASC, OrderDirection.values()[0]);
			assertEquals(OrderDirection.DESC, OrderDirection.values()[1]);
		} catch (Exception e) {
			e.printStackTrace();
			fail("an IllegalArgumentException should be thrown.");
		}
	}

}
