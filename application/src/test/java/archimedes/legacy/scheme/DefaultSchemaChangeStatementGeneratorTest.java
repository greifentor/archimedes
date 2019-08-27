/*
 * DefaultSchemaChangeStatementGeneratorTest.java
 *
 * 24.02.2012
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;


import corent.db.*;

import static org.junit.Assert.*;
import org.junit.*;


/**
 * Tests zur Klasse <CODE>DefaultSchemaChangeStatementGenerator</CODE>.
 *
 * @author ollie
 *
 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
 */

public class DefaultSchemaChangeStatementGeneratorTest {

    private static final DBExecMode DBMS = DBExecMode.POSTGRESQL;
    private static final String SCHEMA_NAME = "Schema";

    private DefaultSchemaChangeStatementGenerator unitUnderTest = null;

    /**
     * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
     */
    @Before
    public void setUp() throws Exception {
        this.unitUnderTest = new DefaultSchemaChangeStatementGenerator();
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCreateRegularRun() throws Exception {
        assertEquals("SET search_path TO " + SCHEMA_NAME +";", this.unitUnderTest.create(
                SCHEMA_NAME, DBMS));
    }

    @Test
    public void testCreateRegularRunWithQuotedSchemaName() throws Exception {
        assertEquals("SET search_path TO \"" + SCHEMA_NAME + "\";", this.unitUnderTest.create(
                "\"" + SCHEMA_NAME + "\"", DBMS));
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCreateReturnsANullPointerPassingSchemaNameAsEmptyString() throws Exception {
        assertNull(this.unitUnderTest.create("", DBMS));
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test
    public void testCreateReturnsANullPointerPassingSchemaNameAsNull() throws Exception {
        assertNull(this.unitUnderTest.create(null, DBMS));
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testCreateThrowsAnExceptionPassingANullPointerAsDBMS() throws Exception {
        this.unitUnderTest.create(SCHEMA_NAME, null);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testCreateThrowsAnExceptionPassingAnUnsupportedDBMSHSQL() throws Exception {
        this.unitUnderTest.create(SCHEMA_NAME, DBExecMode.HSQL);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testCreateThrowsAnExceptionPassingAnUnsupportedDBMSMSSQL() throws Exception {
        this.unitUnderTest.create(SCHEMA_NAME, DBExecMode.MSSQL);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=UnsupportedOperationException.class)
    public void testCreateThrowsAnExceptionPassingAnUnsupportedDBMSMYSQL() throws Exception {
        this.unitUnderTest.create(SCHEMA_NAME, DBExecMode.MYSQL);
    }

}