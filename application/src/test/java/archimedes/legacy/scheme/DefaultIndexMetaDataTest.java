/*
 * DefaultIndexMetaData.java
 *
 * 14.12.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;


import static org.junit.Assert.*;
import org.junit.*;

import archimedes.legacy.*;

import gengen.metadata.*;


/**
 * Tests zur Klasse <CODE>DefaultIndexMetaData</CODE>.
 *
 * @author ollie
 *
 * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
 */

public class DefaultIndexMetaDataTest {

    private static final String ANOTHER_INDEX_NAME = "AnotherIndex";
    private static final String COLUMN_NAME0 = "Column0";
    private static final String COLUMN_NAME1 = "Column1";
    private static final String INDEX_NAME = "Index";
    private static final String TABLE_NAME = "Table";

    private AttributeMetaData column = null;
    private ClassMetaData table = null;
    private DefaultIndexMetaData unitUnderTest = null;

    /**
     * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
     */
    @Before
    public void setUp() throws Exception {
        this.column = Utils.createAttributeMetaDataMock(COLUMN_NAME0);
        this.table = Utils.createClassMetaDataMock(TABLE_NAME, this.column);
        this.unitUnderTest = new DefaultIndexMetaData(INDEX_NAME, this.table);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testAddColumnThrowsAnExceptionPassingANullPointerAsColumnName() throws Exception
            {
        this.unitUnderTest.addColumn(null);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testAddColumnAddsThePassedColumnNameToTheList() throws Exception {
        assertEquals(0, this.unitUnderTest.getColumns().length);
        this.fillAndCheckColumnNameListWithColumnName0();
    }

    private void fillAndCheckColumnNameListWithColumnName0() {
        this.unitUnderTest.addColumn(this.column);
        assertEquals(1, this.unitUnderTest.getColumns().length);
        assertEquals(this.column, this.unitUnderTest.getColumns()[0]);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCompareToIsEqualToItSelf() throws Exception {
        assertEquals(0, this.unitUnderTest.compareTo(this.unitUnderTest));
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCompareToPassingAnotherImplementationOfTheIndexMetaDataInterface()
            throws Exception {
        assertEquals(0, this.unitUnderTest.compareTo(Utils.createIndexMetaData(INDEX_NAME,
                this.table)));
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=ClassCastException.class)
    public void testCompareToThrowsAnExceptionPassingANonIndexMetaDataObject() throws Exception
            {
        this.unitUnderTest.compareTo(":o)");
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCompareToWithAGreateIndexObject() throws Exception {
        assertTrue(this.unitUnderTest.compareTo(new DefaultIndexMetaData("Z", this.table)) < 0);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCompareToWithALesserIndexObject() throws Exception {
        assertTrue(this.unitUnderTest.compareTo(new DefaultIndexMetaData("A", this.table)) > 0);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCompareToWithAnEqualIndexObject() throws Exception {
        assertEquals(0, this.unitUnderTest.compareTo(new DefaultIndexMetaData(INDEX_NAME,
                this.table)));
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsExceptionPassingAnEmptyIndexName() throws Exception {
        new DefaultIndexMetaData("", this.table);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsExceptionPassingANullPointerAsIndexName() throws Exception
            {
        new DefaultIndexMetaData(null, this.table);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsExceptionPassingANullPointerAsTableName() throws Exception
            {
        new DefaultIndexMetaData(INDEX_NAME, null);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testGetName() throws Exception {
        assertEquals(INDEX_NAME, this.unitUnderTest.getName());
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testGetTable() {
        assertEquals(this.table, this.unitUnderTest.getTable());
    }

    /**
     * @changed OLI 20.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testIsMemberThrowingAnExceptionPassingAnEmptyStringAsColumnName()
            throws Exception {
        this.unitUnderTest.isMember("");
    }

    /**
     * @changed OLI 20.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testIsMemberThrowingAnExceptionPassingANullPointerAsColumnName()
            throws Exception {
        this.unitUnderTest.isMember(null);
    }

    /**
     * @changed OLI 20.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testIsMemberRegularRunWithOnAnExistingColumn() throws Exception {
        this.unitUnderTest.addColumn(this.column);
        assertTrue(this.unitUnderTest.isMember(COLUMN_NAME0));
    }

    /**
     * @changed OLI 20.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testIsMemberRegularRunWithOnANotExistingColumn() throws Exception {
        this.unitUnderTest.addColumn(this.column);
        assertFalse(this.unitUnderTest.isMember(COLUMN_NAME1));
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testRemoveColumnThrowsAnExceptionPassingANullPointerAsColumnName()
            throws Exception {
        this.unitUnderTest.removeColumn(null);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testRemoveColumnDoesNothingOnPassingANonExistingColumnName() throws Exception {
        this.fillAndCheckColumnNameListWithColumnName0();
        this.unitUnderTest.removeColumn(Utils.createAttributeMetaDataMock(":o)"));
        assertEquals(1, this.unitUnderTest.getColumns().length);
        assertEquals(this.column, this.unitUnderTest.getColumns()[0]);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testRemoveColumnRemovesThePassedColumnNameFromTheList() throws Exception {
        this.fillAndCheckColumnNameListWithColumnName0();
        this.unitUnderTest.removeColumn(this.column);
        assertEquals(0, this.unitUnderTest.getColumns().length);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetNameThrowsAnExceptionPassingAnEmptyIndexName() throws Exception {
        this.unitUnderTest.setName("");
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetNameThrowsAnExceptionPassingANullPointerAsIndexName() throws Exception {
        this.unitUnderTest.setName(null);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testSetNameSetsThePassedNameToTheIndex() throws Exception {
        assertEquals(INDEX_NAME, this.unitUnderTest.getName());
        this.unitUnderTest.setName(ANOTHER_INDEX_NAME);
        assertEquals(ANOTHER_INDEX_NAME, this.unitUnderTest.getName());
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testSetTableThrowsAnExceptionPassingANullPointerAsTable() throws Exception {
        this.unitUnderTest.setTable(null);
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testSetTableSetsThePassedTableToTheIndexObject() throws Exception {
        this.unitUnderTest.setTable(this.table);
        assertSame(this.table, this.unitUnderTest.getTable());
    }

    /**
     * @changed OLI 14.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testToString() throws Exception {
        this.unitUnderTest.addColumn(this.column);
        this.unitUnderTest.addColumn(Utils.createAttributeMetaDataMock(COLUMN_NAME1));
        assertEquals("Name=" + INDEX_NAME + ", Table=" + TABLE_NAME + ", Columns=["
                + COLUMN_NAME0 + "," + COLUMN_NAME1 + "]", this.unitUnderTest.toString());
    }

}