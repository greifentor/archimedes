/*
 * DomainStringBuilderTest.java
 *
 * 14.06.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;

import static org.junit.Assert.assertEquals;

import java.sql.Types;

import org.junit.Before;
import org.junit.Test;

import archimedes.legacy.model.DomainModel;

/**
 * Tests zur Klasse <TT>DomainStringBuilder</TT>.
 * 
 * @author ollie
 * 
 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
 */

public class DomainStringBuilderTest {

	private DomainModel domain = null;
	private DomainStringBuilder unitUnderTest = null;
	private Tabellenspalte column = null;

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Before
	public void setUp() throws Exception {
		this.domain = new Domain("int", Types.INTEGER, 0, 0);
		this.column = new Tabellenspalte("Id", this.domain);
		this.unitUnderTest = new DomainStringBuilder(this.column);
	}

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testBuildDomainDefaultAndAttributeDefaultUnset() {
		assertEquals("int (int)", this.unitUnderTest.build());
	}

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testBuildDomainDefaultAndAttributeDefaultSet() {
		this.domain.setInitialValue("4711");
		this.column.setIndividualDefaultValue("815");
		assertEquals("individual default value should be set.", "int (int) - 815", this.unitUnderTest.build());
	}

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testBuildNoDomainForColumnSet() {
		this.column.setDomain(null);
		assertEquals("there is no domain set.", "<null>", this.unitUnderTest.build());
	}

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testBuildOnlyAttributeDefaultSet() {
		this.column.setIndividualDefaultValue("815");
		assertEquals("individual default value should be set.", "int (int) - 815", this.unitUnderTest.build());
	}

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testBuildOnlyDomainDefaultSet() {
		this.domain.setInitialValue("4711");
		assertEquals("domain default value should be set.", "int (int) - 4711", this.unitUnderTest.build());
	}

	/**
	 * @changed OLI 14.06.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPassingANullPointerAsColumn() throws Exception {
		new DomainStringBuilder(null);
	}

}