/*
 * DefaultIndexListCleanerTest.java
 *
 * 19.12.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;

import static org.junit.Assert.assertEquals;
import gengen.metadata.AttributeMetaData;
import gengen.metadata.ClassMetaData;

import org.junit.Before;
import org.junit.Test;

import archimedes.legacy.Utils;
import archimedes.legacy.model.ColumnModel;
import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.DomainModel;
import archimedes.legacy.model.IndexMetaData;
import archimedes.legacy.model.TableModel;
import archimedes.legacy.model.ViewModel;
import archimedes.legacy.scheme.xml.ObjectFactory;

/**
 * Tests zur Klasse <CODE>DefaultIndexListCleaner</CODE>.
 * 
 * @author ollie
 * 
 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
 */

public class DefaultIndexListCleanerTest {

	private static final String COLUMN_NAME_A = "ColumnA";
	private static final String COLUMN_NAME_B = "ColumnB";
	private static final String COLUMN_NAME_C = "ColumnC";
	private static final String INDEX_NAME_0 = "Index0";
	private static final String INDEX_NAME_1 = "Index1";
	private static final String TABLE_NAME_A = "TableA";
	private static final String TABLE_NAME_B = "TableB";

	private DefaultIndexListCleaner unitUnderTest = null;
	private IndexMetaData index0 = null;
	private IndexMetaData index1 = null;

	/**
	 * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
	 */
	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new DefaultIndexListCleaner();
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCleanThrowsAnExceptionOnPassingANullPointerAsModel() throws Exception {
		this.unitUnderTest.clean(null);
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testCleanRemovesColumnBFromIndex0AndIndex1AtAll() throws Exception {
		ObjectFactory of = new ArchimedesObjectFactory();
		DataModel dm = of.createDataModel();
		ViewModel vm = of.createMainView("Main", ";op", true);
		DomainModel dom = of.createDomain("Domain", 1, 2, 3);
		TableModel tmA = of.createTable(dm, vm);
		tmA.setName(TABLE_NAME_A);
		dm.addTable(tmA);
		ColumnModel cA = of.createColumn(COLUMN_NAME_A, dom);
		tmA.addColumn(cA);
		ColumnModel cC = of.createColumn(COLUMN_NAME_C, dom);
		tmA.addColumn(cC);
		TableModel tmB = of.createTable(dm, vm);
		tmB.setName(TABLE_NAME_B);
		ColumnModel cB = of.createColumn(COLUMN_NAME_B, dom);
		tmB.addColumn(cB);
		dm.addTable(tmB);
		this.index0 = Utils.createIndexMetaData(INDEX_NAME_0, (ClassMetaData) tmA, (AttributeMetaData) tmA
				.getColumnByName(COLUMN_NAME_A), (AttributeMetaData) tmA.getColumnByName(COLUMN_NAME_C));
		this.index1 = Utils.createIndexMetaData(INDEX_NAME_1, (ClassMetaData) tmB, (AttributeMetaData) tmB
				.getColumnByName(COLUMN_NAME_B));
		dm.addComplexIndex(this.index0);
		dm.addComplexIndex(this.index1);
		tmA.removeColumn(cC);
		dm.removeTable(tmB);
		this.unitUnderTest.clean(dm);
		assertEquals(1, this.index0.getColumns().length);
		assertEquals(cA, this.index0.getColumns()[0]);
		assertEquals(1, dm.getComplexIndexCount());
	}

}