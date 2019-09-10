/*
 * AbstractCodeGeneratorTest.java
 *
 * 07.09.2009
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.generator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import corentx.dates.PDate;
import gengen.metadata.AttributeMetaData;
import gengen.metadata.ClassMetaData;
import gengen.metadata.ModelMetaData;
import gengen.metadata.SelectionViewMetaData;

/**
 * Testf&auml;lle zur Klasse AbstractCodeGenerator.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 07.09.2009 - Hinzugef&uuml;gt.
 * @changed OLI 21.05.2010 - Entfernung des Tests zur aufgehobenen Methode <TT>getAttributeNameList(ClassMetaData)</TT>.
 * @changed OLI 18.11.2010 - Anpassungen nach Erweiterung der Spezifikation der Klasse <TT>AttributeMetaData</TT>.
 */

public class AbstractCodeGeneratorTest {

	private AbstractCodeGenerator acg = new AbstractCodeGeneratorFailureMock();
	private ModelMetaData mmd = null;

	/**
	 * Erzeugt ein ModelMetaData zum Test.
	 *
	 * @changed OLI 19.10.2010 - Anpassung nach Erweiterung des Interfaces ClassMetaData.
	 */
	@Before
	public void setUp() {
		// FIXME (A) OLI 18.11.2010 - Auslagern der Interfaceimplementierungen in Mocks.
		this.mmd = new ModelMetaDataMock();
	}

	/** Test der Methode getAccessorName(). */
	@Test
	public void testGetAccessorNameAttributeMetadata() {
		ClassMetaData cmd = null;
		// Negativbeispiele.
		try {
			acg.getAccessorName(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		// Positivbeispiele.
		cmd = this.mmd.getClass(0);
		assertEquals("getId", acg.getAccessorName(cmd.getAttribute(0)));
		assertEquals("getDescription", acg.getAccessorName(cmd.getAttribute(1)));
		assertEquals("getComment", acg.getAccessorName(cmd.getAttribute(2)));
		assertEquals("getLastModificationDate", acg.getAccessorName(cmd.getAttribute(3)));
		assertEquals("isChecked", acg.getAccessorName(cmd.getAttribute(4)));
		assertEquals("isUnchecked", acg.getAccessorName(cmd.getAttribute(5)));
		assertEquals("getEmptyDefault", acg.getAccessorName(cmd.getAttribute(6)));
		assertEquals("getEmptyDefaultRef", acg.getAccessorName(cmd.getAttribute(7)));
	}

	/** Test der Methode getAttributeDefinition(ClassMetaData). */
	@Test
	public void testGetAttributeDefinitionClassMetaData() {
		// Negativbeispiele.
		try {
			acg.getAttributeDefinition(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("    private boolean checked = true;\n" + "    private boolean unchecked = false;\n"
				+ "    private corentx.dates.PDate lastmodificationdate = null;\n" + "    private int emptydefault;\n"
				+ "    private int id = -1;\n" + "    private String comment = null;\n"
				+ "    private String description = \"\";\n" + "    private String emptydefaultref;\n",
				acg.getAttributeDefinition(this.mmd.getClass(0)));
	}

	/** Test der Methode getAttributeNameList(ClassMetaData). */
	@Ignore
	@Test
	public void testGetAttributeNameListClassMetaData() {
		// Negativbeispiele.
		try {
			acg.getAttributeNameList(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("Id, Description, Comment, LastModificationDate, Checked, Unchecked, "
				+ "EmptyDefault, EmptyDefaultRef", acg.getAttributeNameList(this.mmd.getClass(0)));
	}

	/** Testet die Methode getEqualsReturnStatement(ClassMetaData, String). */
	@Test
	public void testGetEqualsReturnStatementClassMetaDataString() {
		// Negativbeispiele.
		try {
			acg.getEqualsReturnStatement(null, "x");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.getEqualsReturnStatement(this.mmd.getClass(0), null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.getEqualsReturnStatement(null, null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("        return Utl.equals(this.getId(), equ.getId())"
				+ "\n                && Utl.equals(this.getDescription(), equ.getDescription())"
				+ "\n                && Utl.equals(this.getComment(), equ.getComment())"
				+ "\n                && Utl.equals(this.getLastModificationDate(), " + "equ.getLastModificationDate())"
				+ "\n                && Utl.equals(this.isChecked(), equ.isChecked())"
				+ "\n                && Utl.equals(this.isUnchecked(), equ.isUnchecked())"
				+ "\n                && Utl.equals(this.getEmptyDefault(), equ.getEmptyDefault()" + ")"
				+ "\n                && Utl.equals(this.getEmptyDefaultRef(), " + "equ.getEmptyDefaultRef());\n",
				acg.getEqualsReturnStatement(this.mmd.getClass(0), "equ"));
	}

	/**
	 * Testet die Methode getDefaultValueString(AttributeMetaData).
	 *
	 * @changed OLI 04.10.2009 - Hinzugef&uuml;gt.
	 * @changed OLI 07.10.2009 - Erweiterung um die Testf&auml;lle f&uuml;r die Boolean-Werte.
	 */
	@Test
	public void testGetDefaultValueStringAttributeMetaData() {
		assertEquals("-1", acg.getDefaultValueString(this.mmd.getClass(0).getAttribute(0)));
		assertEquals("\"\"", acg.getDefaultValueString(this.mmd.getClass(0).getAttribute(1)));
		assertEquals("null", acg.getDefaultValueString(this.mmd.getClass(0).getAttribute(2)));
		assertEquals("true", acg.getDefaultValueString(this.mmd.getClass(0).getAttribute(4)));
		assertEquals("false", acg.getDefaultValueString(this.mmd.getClass(0).getAttribute(5)));
	}

	/** Testet die Methode getEJBJarSessionTag(ClassMetaData, int). */
	@Test
	public void testGetEJBJarSessionTag() {
		String s = null;
		// Negativbeispiele.
		try {
			acg.getEJBJarSessionTag(null, ";o)", 0);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.getEJBJarSessionTag(this.mmd.getClass(0), null, 0);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.getEJBJarSessionTag(this.mmd.getClass(0), ";o)", -1);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		// Positivbeispiele.
		s = "    <session>\n" + "        <description>TestMasterDataInterfaceTest</description>\n"
				+ "        <ejb-name>TestMasterDataInterfaceTest</ejb-name>\n"
				+ "        <home>com.disney.scheme.ejb.interfaces.TestMasterDataInterfaceTestHome</home>\n"
				+ "        <remote>com.disney.scheme.ejb.interfaces.TestMasterDataInterfaceTestRemote</remote>\n"
				+ "        <local-home>com.disney.scheme.ejb.interfaces.TestMasterDataInterfaceTestLocalHome</local-home>\n"
				+ "        <local>com.disney.scheme.ejb.interfaces.TestMasterDataInterfaceTestLocalRemote</local>\n"
				+ "        <ejb-class>com.disney.scheme.ejb.beans.TestMasterDataInterfaceTestBean</ejb-class>\n"
				+ "        <session-type>Stateless</session-type>\n"
				+ "        <transaction-type>Container</transaction-type>\n" + "    </session>\n";
		assertEquals(s, acg.getEJBJarSessionTag(this.mmd.getClass(0), "com.disney", 4));
		s = "    <session>\n" + "        <description>TestMasterDataInterfaceTest</description>\n"
				+ "        <ejb-name>TestMasterDataInterfaceTest</ejb-name>\n"
				+ "        <home>com.loony.toons.scheme.ejb.interfaces.TestMasterDataInterfaceTestHome</home>\n"
				+ "        <remote>com.loony.toons.scheme.ejb.interfaces.TestMasterDataInterfaceTestRemote</remote>\n"
				+ "        <local-home>com.loony.toons.scheme.ejb.interfaces.TestMasterDataInterfaceTestLocalHome</local-home>\n"
				+ "        <local>com.loony.toons.scheme.ejb.interfaces.TestMasterDataInterfaceTestLocalRemote</local>\n"
				+ "        <ejb-class>com.loony.toons.scheme.ejb.beans.TestMasterDataInterfaceTestBean</ejb-class>\n"
				+ "        <session-type>Stateless</session-type>\n"
				+ "        <transaction-type>Container</transaction-type>\n" + "    </session>\n";
		assertEquals(s, acg.getEJBJarSessionTag(this.mmd.getClass(0), "com.loony.toons", 4));
	}

	/** Testet die Methode getRSGet(AttributeMetaData, int, String). */
	@Test
	public void testGetRSGetAttributeDataIntString() {
		AttributeMetaData amdLPTs = new AttributeMetaDataMock("", "corentx.dates.LongPTimestamp", 0,
				"LastModificationDate", false, false, false);
		AttributeMetaData amdPTs = new AttributeMetaDataMock("", "corentx.dates.PTimestamp", 0, "LastModificationDate",
				false, false, false);
		ClassMetaData cmd = null;
		// Negativbeispiele.
		try {
			acg.getRSGet(null, 1, "rs");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.getRSGet(this.mmd.getClass(0).getAttribute(0), -2, "rs");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		// Positivbeispiele.
		cmd = this.mmd.getClass(0);
		assertEquals("DBDataSourceUtil.getNumber(rs, 1, -1)", acg.getRSGet(cmd.getAttribute(0), 1, null));
		assertEquals("DBDataSourceUtil.getNumber(andererName, 1, -1)",
				acg.getRSGet(cmd.getAttribute(0), 1, "andererName"));
		assertEquals("rs.getString(42)", acg.getRSGet(cmd.getAttribute(1), 42, null));
		assertEquals("DBDataSourceUtil.getLongPTimestamp(rs, 43)", acg.getRSGet(amdLPTs, 43, null));
		assertEquals("DBDataSourceUtil.getPDate(rs, 44)", acg.getRSGet(cmd.getAttribute(3), 44, null));
		assertEquals("DBDataSourceUtil.getPTimestamp(rs, 45)", acg.getRSGet(amdPTs, 45, null));
		assertEquals("(rs.getInt(4711) != 0)", acg.getRSGet(cmd.getAttribute(4), 4711, null));
	}

	/**
	 * Test der Methode statischen <TT>getSetterFromString(AttributeMetaData, String)</TT>.
	 *
	 * @changed OLI 04.10.2009 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGetSetterFromStringAttributeMetaDataString() {
		AttributeMetaData amd = this.mmd.getClass(0).getAttribute(0);
		// Negativbeispiele.
		try {
			AbstractCodeGenerator.getSetterFromString(null, "foo", false);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			AbstractCodeGenerator.getSetterFromString(amd, null, false);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			AbstractCodeGenerator.getSetterFromString(amd, "", false);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("setId(Integer.valueOf(value));", AbstractCodeGenerator.getSetterFromString(amd, "value", false));
		assertEquals("setId(Integer.valueOf(str));", AbstractCodeGenerator.getSetterFromString(amd, "str", true));
		amd = this.mmd.getClass(0).getAttribute(1);
		assertEquals("setDescription(String.valueOf(value));",
				AbstractCodeGenerator.getSetterFromString(amd, "value", false));
		assertEquals("setDescription(String.valueOf(str));",
				AbstractCodeGenerator.getSetterFromString(amd, "str", true));
		amd = this.mmd.getClass(0).getAttribute(3);
		assertEquals("setLastModificationDate(corentx.dates.PDate.valueOf(value));",
				AbstractCodeGenerator.getSetterFromString(amd, "value", false));
		assertEquals("setLastModificationDate(new corentx.dates.PDate(Long.valueOf(str)));",
				AbstractCodeGenerator.getSetterFromString(amd, "str", true));
		amd = this.mmd.getClass(0).getAttribute(4);
		assertEquals("setChecked(Boolean.valueOf(value));",
				AbstractCodeGenerator.getSetterFromString(amd, "value", false));
		assertEquals("setChecked(Boolean.valueOf(str));", AbstractCodeGenerator.getSetterFromString(amd, "str", true));
	}

	/**
	 * Test der Methode <TT>getWrapperType(String)</TT>.
	 *
	 * @changed OLI 04.10.2009 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testGetWrapperTypeString() {
		// Negativbeispiele.
		try {
			AbstractCodeGenerator.getWrapperType(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			AbstractCodeGenerator.getWrapperType("");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("Boolean", AbstractCodeGenerator.getWrapperType("boolean"));
		assertEquals("Byte", AbstractCodeGenerator.getWrapperType("byte"));
		assertEquals("Character", AbstractCodeGenerator.getWrapperType("char"));
		assertEquals("Double", AbstractCodeGenerator.getWrapperType("double"));
		assertEquals("Float", AbstractCodeGenerator.getWrapperType("float"));
		assertEquals("Foo", AbstractCodeGenerator.getWrapperType("Foo"));
		assertEquals("Integer", AbstractCodeGenerator.getWrapperType("int"));
		assertEquals("Long", AbstractCodeGenerator.getWrapperType("long"));
		assertEquals("PDate", AbstractCodeGenerator.getWrapperType("PDate"));
		assertEquals("corentx.dates.PDate", AbstractCodeGenerator.getWrapperType("corentx.dates.PDate"));
		assertEquals("Short", AbstractCodeGenerator.getWrapperType("short"));
		assertEquals("String", AbstractCodeGenerator.getWrapperType("String"));
	}

	/** Testet die Methode isElementaryType(String). */
	@Test
	public void testIsElementaryTypeString() {
		try {
			acg.isElementaryType(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertTrue(acg.isElementaryType("boolean"));
		assertTrue(acg.isElementaryType("byte"));
		assertTrue(acg.isElementaryType("char"));
		assertTrue(acg.isElementaryType("double"));
		assertTrue(acg.isElementaryType("float"));
		assertTrue(acg.isElementaryType("int"));
		assertTrue(acg.isElementaryType("long"));
		assertTrue(acg.isElementaryType("short"));
		assertFalse(acg.isElementaryType("String"));
		assertFalse(acg.isElementaryType("corentx.dates.PDate"));
		assertFalse(acg.isElementaryType("Foo"));
	}

	/** Testet die Methode replaceClassWildCards(StringBuffer, ClassMetaData). */
	@Test
	public void testReplaceClassWildCardsStringBufferClassMetaData() {
		ClassMetaData cmd = null;
		String d = new PDate().toString();
		StringBuffer source = new StringBuffer("$AUTHOR$, $CLASSNAME$, $DATE$");
		assertEquals("Bugs Bunny, Test, " + d, acg.replaceClassWildCards(source, this.mmd.getClass(0)).toString());
		try {
			acg.replaceClassWildCards(null, this.mmd.getClass(0));
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.replaceClassWildCards(source, cmd);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.replaceClassWildCards(null, cmd);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
	}

	/** Testet die Methode replaceModelWildCards(StringBuffer, ModelMetaData). */
	@Test
	public void testReplaceModelWildCardsStringBufferClassMetaData() {
		StringBuffer source = new StringBuffer("$BASEPACKAGE$, $VENDOR$, $PROJECTTOKEN$");
		assertEquals("com.disney, Lieshoff Bros. Inc., Test", acg.replaceModelWildCards(source, this.mmd).toString());
		try {
			acg.replaceModelWildCards(null, this.mmd);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.replaceModelWildCards(source, this.mmd);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			acg.replaceModelWildCards(null, this.mmd);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
	}

	/** Testet die Methode setBasePath(String). */
	@Test
	public void testSetBasePathString() {
		acg.setBasePath("test");
		assertEquals("test/", acg.getBasePath());
		try {
			acg.setBasePath(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertEquals("test/", acg.getBasePath());
	}

}

class AbstractCodeGeneratorFailureMock extends AbstractCodeGenerator {

	@Override
	public String generate(ClassMetaData cmd) {
		throw new UnsupportedOperationException("test instance can not produce any " + "code.");
	}

}

class AttributeMetaDataMock implements AttributeMetaData {

	private boolean notNull = false;
	private boolean primaryKey = false;
	private boolean technicalAttribute = false;
	private ClassMetaData referencedClass = null;
	private int maxLength = 0;
	private String defaultValue = null;
	private String javaType = null;
	private String name = null;

	/**
	 * Generiert ein AttributeMetaDataMock anhand der &uuml;bergebenen Parameter.
	 *
	 * @param dv Der DefaultValue zum Attribut.
	 * @param jt Der Java-Typ des Attributs.
	 * @param ml Die maximale L&auml;nge der Attributeintr&auml;ge in Zeichen.
	 * @param n  Der Name des Attributs.
	 * @param nn Diese Flagge ist zu setzen, falls das Attribut nicht mit einem <TT>null</TT>-Wert belegt werden kann.
	 * @param pk Diese Flagge ist zu setzen, wenn das Attribut ein Prim&auml;rschl&uuml;sel ist.
	 * @param ta Diese Flagge ist zu setzen, wenn es sich bei dem Attribut um ein technisches Attribut handelt.
	 */
	public AttributeMetaDataMock(String dv, String jt, int ml, String n, boolean nn, boolean pk, boolean ta) {
		super();
		this.defaultValue = dv;
		this.javaType = jt;
		this.maxLength = ml;
		this.name = n;
		this.notNull = nn;
		this.primaryKey = pk;
		this.technicalAttribute = ta;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getDefaultValue() {
		return this.defaultValue;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getJavaType() {
		return this.javaType;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public int getMaxLength() {
		return this.maxLength;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public String getName() {
		return this.name;
	}

	/**
	 * @changed OLI 07.01.2011 - Hinzugef&uuml;gt.
	 */
	@Override
	public ClassMetaData getReferencedClass() {
		return this.referencedClass;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public boolean isNotNull() {
		return this.notNull;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public boolean isPrimaryKeyMember() {
		return this.primaryKey;
	}

	/**
	 * @changed OLI 18.11.2010 - Hinzugef&uuml;gt.
	 */
	@Override
	public boolean isTechnicalAttribute() {
		return this.technicalAttribute;
	}

}

class ClassMetaDataMock implements ClassMetaData {

	ModelMetaData mmd = null;

	public ClassMetaDataMock(ModelMetaData mmd) {
		super();
		this.mmd = mmd;
	}

	@Override
	public AttributeMetaData getAttribute(int i) throws IndexOutOfBoundsException {
		switch (i) {
		case 0:
			return new AttributeMetaDataMock("-1", "int", 0, "Id", false, true, true);
		case 1:
			return new AttributeMetaDataMock("\"\"", "String", 0, "Description", false, false, false);
		case 2:
			return new AttributeMetaDataMock(null, "String", 0, "Comment", false, false, false);
		case 3:
			return new AttributeMetaDataMock("-1", "corentx.dates.PDate", 0, "LastModificationDate", false, false,
					true);
		case 4:
			return new AttributeMetaDataMock("-1", "boolean", 0, "Checked", false, false, false);
		case 5:
			return new AttributeMetaDataMock("0", "boolean", 0, "Unchecked", false, false, false);
		case 6:
			return new AttributeMetaDataMock("", "int", 0, "EmptyDefault", false, false, false);
		case 7:
			return new AttributeMetaDataMock("", "String", 0, "EmptyDefaultRef", false, false, false);
		}
		throw new IndexOutOfBoundsException("index " + i + " is not in range.");
	}

	@Override
	public AttributeMetaData getAttribute(String name) {
		throw new UnsupportedOperationException("This method have not been implemented for the " + "mock.");
	}

	@Override
	public List<AttributeMetaData> getAttributes() {
		List<AttributeMetaData> amds = new Vector<AttributeMetaData>();
		amds.add(this.getAttribute(0));
		amds.add(this.getAttribute(1));
		amds.add(this.getAttribute(2));
		amds.add(this.getAttribute(3));
		amds.add(this.getAttribute(4));
		amds.add(this.getAttribute(5));
		amds.add(this.getAttribute(6));
		amds.add(this.getAttribute(7));
		return amds;
	}

	@Override
	public String getAuthor() {
		return "Bugs Bunny";
	}

	@Override
	public String getName() {
		return "Test";
	}

	@Override
	public ModelMetaData getModel() {
		return this.mmd;
	}

	@Override
	public String getPackageName() {
		return "test";
	}

	/** @changed OLI 19.10.2010 - Hinzugef&uuml;gt. */
	@Override
	public boolean isFirstGenerationDone() {
		return false;
	}

	@Override
	public boolean isOfStereotype(String sn) throws NullPointerException {
		assert sn != null : "null is not a name for stereotypes.";
		return sn.equals("Test");
	}

	@Override
	public boolean isReadyToCode() {
		return true;
	}

	@Override
	public List<SelectionViewMetaData> getSelectionViewMetaData() {
		return null;
	}

}

class ModelMetaDataMock implements ModelMetaData {

	@Override
	public String getBasePackageName() {
		return "com.disney";
	}

	@Override
	public ClassMetaData getClass(int i) {
		return new ClassMetaDataMock(this);
	}

	@Override
	public ClassMetaData getClass(String name) {
		return new ClassMetaDataMock(this);
	}

	@Override
	public List<ClassMetaData> getClasses() {
		List<ClassMetaData> cmds = new Vector<ClassMetaData>();
		cmds.add(this.getClass(0));
		return cmds;
	}

	@Override
	public String getAuthor() {
		return "Micky Maus";
	}

	@Override
	public String getProjectToken() {
		return "Test";
	}

	@Override
	public String getVendor() {
		return "Lieshoff Bros. Inc.";
	}

}