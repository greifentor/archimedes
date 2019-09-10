/*
 * SQLUtilTest.java
 *
 * 30.09.2009
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import gengen.metadata.AttributeMetaData;
import gengen.metadata.ClassMetaData;
import gengen.metadata.ModelMetaData;
import gengen.metadata.SelectionViewMetaData;

/**
 * Testf&auml;lle zur Klasse SQLUtil.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 09.09.2009 - Hinzugef&uuml;gt.
 * @changed OLI 18.11.2010 - Anpassungen nach Erweiterung der Spezifikation der Klasse <TT>AttributeMetaData</TT>.
 */

public class SQLUtilTest {

	private ModelMetaData mmd = null;

	@Before
	/**
	 * Erzeugt ein ModelMetaData zum Test.
	 *
	 * @changed OLI 19.10.2010 - Anpassung nach Erweiterung des Interfaces ClassMetaData.
	 */
	public void setUp() {
		// FIXME (A) OLI 18.11.2010 - Auslagern der Interfaceimplementierungen in Mocks.
		this.mmd = new ModelMetaData() {
			public String getBasePackageName() {
				return "com.disney";
			}

			public ClassMetaData getClass(int i) {
				ClassMetaData cmd = new ClassMetaData() {
					public AttributeMetaData getAttribute(int i) throws IndexOutOfBoundsException {
						switch (i) {
						case 0:
							return new AttributeMetaData() {
								public String getDefaultValue() {
									return "-1";
								}

								public String getJavaType() {
									return "int";
								}

								public int getMaxLength() {
									return 0;
								}

								public String getName() {
									return "Id";
								}

								public ClassMetaData getReferencedClass() {
									return null;
								}

								public boolean isNotNull() {
									return false;
								}

								public boolean isPrimaryKeyMember() {
									return true;
								}

								public boolean isTechnicalAttribute() {
									return true;
								}
							};
						case 1:
							return new AttributeMetaData() {
								public String getDefaultValue() {
									return "\"\"";
								}

								public String getJavaType() {
									return "String";
								}

								public int getMaxLength() {
									return 0;
								}

								public String getName() {
									return "Description";
								}

								public ClassMetaData getReferencedClass() {
									return null;
								}

								public boolean isNotNull() {
									return false;
								}

								public boolean isPrimaryKeyMember() {
									return false;
								}

								public boolean isTechnicalAttribute() {
									return false;
								}
							};
						case 2:
							return new AttributeMetaData() {
								public String getDefaultValue() {
									return null;
								}

								public String getJavaType() {
									return "String";
								}

								public int getMaxLength() {
									return 0;
								}

								public String getName() {
									return "Comment";
								}

								public ClassMetaData getReferencedClass() {
									return null;
								}

								public boolean isNotNull() {
									return false;
								}

								public boolean isPrimaryKeyMember() {
									return false;
								}

								public boolean isTechnicalAttribute() {
									return false;
								}
							};
						case 3:
							return new AttributeMetaData() {
								public String getDefaultValue() {
									return "-1";
								}

								public String getJavaType() {
									return "corentx.dates.PDate";
								}

								public int getMaxLength() {
									return 0;
								}

								public String getName() {
									return "LastModificationDate";
								}

								public ClassMetaData getReferencedClass() {
									return null;
								}

								public boolean isNotNull() {
									return false;
								}

								public boolean isPrimaryKeyMember() {
									return false;
								}

								public boolean isTechnicalAttribute() {
									return true;
								}
							};
						case 4:
							return new AttributeMetaData() {
								public String getDefaultValue() {
									return "-1";
								}

								public String getJavaType() {
									return "boolean";
								}

								public int getMaxLength() {
									return 0;
								}

								public String getName() {
									return "Checked";
								}

								public ClassMetaData getReferencedClass() {
									return null;
								}

								public boolean isNotNull() {
									return false;
								}

								public boolean isPrimaryKeyMember() {
									return false;
								}

								public boolean isTechnicalAttribute() {
									return false;
								}
							};
						}
						throw new IndexOutOfBoundsException("index " + i + " is not in range.");
					}

					public AttributeMetaData getAttribute(String name) {
						throw new UnsupportedOperationException(
								"This method have not been " + "implemented for the mock.");
					}

					public List<AttributeMetaData> getAttributes() {
						List<AttributeMetaData> amds = new Vector<AttributeMetaData>();
						amds.add(this.getAttribute(0));
						amds.add(this.getAttribute(1));
						amds.add(this.getAttribute(2));
						amds.add(this.getAttribute(3));
						amds.add(this.getAttribute(4));
						return amds;
					}

					public String getAuthor() {
						return "Bugs Bunny";
					}

					public String getName() {
						return "Test";
					}

					public ModelMetaData getModel() {
						return mmd;
					}

					public String getPackageName() {
						return "test";
					}

					/** @changed OLI 19.10.2010 - Hinzugef&uuml;gt. */
					public boolean isFirstGenerationDone() {
						return false;
					}

					public boolean isOfStereotype(String sn) throws NullPointerException {
						assert sn != null : "null is not a name for stereotypes.";
						return sn.equals("Test");
					}

					public boolean isReadyToCode() {
						return true;
					}

					@Override
					public List<SelectionViewMetaData> getSelectionViewMetaData() {
						return null;
					}
				};
				return cmd;
			}

			public ClassMetaData getClass(String name) {
				throw new UnsupportedOperationException("This method have not been implemented for the " + "mock.");
			}

			public List<ClassMetaData> getClasses() {
				List<ClassMetaData> cmds = new Vector<ClassMetaData>();
				cmds.add(this.getClass(0));
				return cmds;
			}

			public String getAuthor() {
				return "Micky Maus";
			}

			public String getProjectToken() {
				return "Test";
			}

			public String getVendor() {
				return "Lieshoff Bros. Inc.";
			}
		};
	}

	@Test
	/** Test der Methode <TT>getAttributeNameList(ClassMetaData)</TT>. */
	public void getAttributeNameListClassMetaData() {
		// Negativbeispiele.
		try {
			SQLUtil.getAttributeNameList(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("Id, Description, Comment, LastModificationDate, Checked",
				SQLUtil.getAttributeNameList(this.mmd.getClass(0)));
	}

	@Test
	/** Test der Methode <TT>getInsertStatement(ClassMetaData, String)</TT>. */
	public void testGetInsertStatementClassMetaDataString() {
		ClassMetaData cmd = this.mmd.getClass(0);
		String s = "vn";
		// Negativbeispiele.
		try {
			s = SQLUtil.getInsertStatement(null, s, null, false, 0);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			s = SQLUtil.getInsertStatement(cmd, null, null, false, 0);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			s = SQLUtil.getInsertStatement(cmd, null, null, false, -2);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		try {
			s = SQLUtil.getInsertStatement(cmd, null, null, false, 2);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals(
				"insert into Test (Id, Description, Comment, LastModificationDate, Checked"
						+ ") values (\" + t.getId() + \", '\" + t.getDescription() + \"', '\" + "
						+ "t.getComment() + \"', \" + DBDataSourceUtil.toDBString("
						+ "t.getLastModificationDate()) + \", \" + (t.isChecked() ? \"1\" : \"0\") + " + "\");",
				SQLUtil.getInsertStatement(cmd, "t", null, false, 0));
		assertEquals(
				"insert into Test (Id, Description, Comment, LastModificationDate, Checked"
						+ ") values (\" + id + \", '\" + t.getDescription() + \"', '\" + "
						+ "t.getComment() + \"', \" + DBDataSourceUtil.toDBString("
						+ "t.getLastModificationDate()) + \", \" + (t.isChecked() ? \"1\" : \"0\") + " + "\");",
				SQLUtil.getInsertStatement(cmd, "t", "id", false, 0));
		assertEquals(
				"insert into Test (Id, Description, Comment, LastModificationDate, Checked"
						+ "\"\n    + \") values (\"\n    + id + \", \"\n    + \"'\" + t.getDescription("
						+ ") + \"', \"\n    + \"'\" + t.getComment() + \"', \"\n    + "
						+ "DBDataSourceUtil.toDBString(t.getLastModificationDate()) + \", "
						+ "\"\n    + (t.isChecked() ? \"1\" : \"0\") + \");",
				SQLUtil.getInsertStatement(cmd, "t", "id", true, 4));
		assertEquals(
				"insert into Test (Id, Description, Comment, LastModificationDate, Checked"
						+ "\"\n    + \") values (\"\n    + id + \", \"\n    + \"'\" + x.getDescription("
						+ ") + \"', \"\n    + \"'\" + (x.getComment() < 1 ? id : x.getComment()) + "
						+ "\", \"\n    + DBDataSourceUtil.toDBString(x.getLastModification"
						+ "Date()) + \", \"\n    + (x.isChecked() ? \"1\" : \"0\") + \");",
				SQLUtil.getInsertStatement(cmd, "x", "id", true, 4, true, "Comment"));
	}

	@Test
	/** Testet die Methode <TT>getTypeSuffix(String)</TT>. */
	public void testGetTypeSuffixString() {
		// Negativbeispiele.
		try {
			SQLUtil.getTypeSuffix(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		try {
			SQLUtil.getTypeSuffix("boolean");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		try {
			SQLUtil.getTypeSuffix("char");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		try {
			SQLUtil.getTypeSuffix("String");
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (IllegalArgumentException iae) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertEquals("", SQLUtil.getTypeSuffix("byte"));
		assertEquals("D", SQLUtil.getTypeSuffix("double"));
		assertEquals("F", SQLUtil.getTypeSuffix("float"));
		assertEquals("", SQLUtil.getTypeSuffix("int"));
		assertEquals("L", SQLUtil.getTypeSuffix("long"));
		assertEquals("", SQLUtil.getTypeSuffix("short"));
	}

	@Test
	/** Testet die Methode <TT>isElementaryNumberType(String)</TT>. */
	public void testIsElementaryNumberTypeString() {
		try {
			SQLUtil.isElementaryNumberType(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertFalse(SQLUtil.isElementaryNumberType("boolean"));
		assertTrue(SQLUtil.isElementaryNumberType("byte"));
		assertFalse(SQLUtil.isElementaryNumberType("char"));
		assertTrue(SQLUtil.isElementaryNumberType("double"));
		assertTrue(SQLUtil.isElementaryNumberType("float"));
		assertTrue(SQLUtil.isElementaryNumberType("int"));
		assertTrue(SQLUtil.isElementaryNumberType("long"));
		assertTrue(SQLUtil.isElementaryNumberType("short"));
		assertFalse(SQLUtil.isElementaryNumberType("String"));
		assertFalse(SQLUtil.isElementaryNumberType("corentx.dates.PDate"));
		assertFalse(SQLUtil.isElementaryNumberType("Foo"));
	}

	@Test
	/** Testet die Methode <TT>isElementaryType(String)</TT>. */
	public void testIsElementaryTypeString() {
		try {
			SQLUtil.isElementaryType(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		assertTrue(SQLUtil.isElementaryType("boolean"));
		assertTrue(SQLUtil.isElementaryType("byte"));
		assertTrue(SQLUtil.isElementaryType("char"));
		assertTrue(SQLUtil.isElementaryType("double"));
		assertTrue(SQLUtil.isElementaryType("float"));
		assertTrue(SQLUtil.isElementaryType("int"));
		assertTrue(SQLUtil.isElementaryType("long"));
		assertTrue(SQLUtil.isElementaryType("short"));
		assertFalse(SQLUtil.isElementaryType("String"));
		assertFalse(SQLUtil.isElementaryType("corentx.dates.PDate"));
		assertFalse(SQLUtil.isElementaryType("Foo"));
	}

}
