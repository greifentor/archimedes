/*
 * SQLScriptTest.java
 *
 * 12.12.2013
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.sql;


import static org.junit.Assert.*;

import archimedes.legacy.script.sql.*;

import org.junit.*;


/**
 * Tests of the class <CODE>SQLScript</CODE>.
 *
 * @author ollie
 *
 * @changed OLI 12.12.2013 - Added.
 */

public class SQLScriptTest {

    private static final String STMT = "stmt";

    private SQLScript unitUnderTest = null;

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Before public void setUp() throws Exception {
        this.unitUnderTest = new SQLScript();
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test public void testAddChangingStatementAddsThePassedStmtsToTheList() {
        this.unitUnderTest.addChangingStatement(STMT+1);
        this.unitUnderTest.addChangingStatement(STMT+2);
        String[] a = this.unitUnderTest.getChangingStatements();
        assertEquals(2, a.length);
        assertEquals(STMT+1, a[0]);
        assertEquals(STMT+2, a[1]);
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test(expected = IllegalArgumentException.class) public void
            testAddChangingStatementThrowsAIllegalArgumentExceptionPassingANullString() {
        this.unitUnderTest.addChangingStatement(null);
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test public void testAddExtendingStatementAddsThePassedStmtsToTheList() {
        this.unitUnderTest.addExtendingStatement(STMT+1);
        this.unitUnderTest.addExtendingStatement(STMT+2);
        String[] a = this.unitUnderTest.getExtendingStatements();
        assertEquals(2, a.length);
        assertEquals(STMT+1, a[0]);
        assertEquals(STMT+2, a[1]);
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test(expected = IllegalArgumentException.class) public void
            testAddExtendingStatementThrowsAIllegalArgumentExceptionPassingANullString() {
        this.unitUnderTest.addExtendingStatement(null);
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test public void testAddReducingStatementAddsThePassedStmtsToTheList() {
        this.unitUnderTest.addReducingStatement(STMT+1);
        this.unitUnderTest.addReducingStatement(STMT+2);
        String[] a = this.unitUnderTest.getReducingStatements();
        assertEquals(2, a.length);
        assertEquals(STMT+1, a[0]);
        assertEquals(STMT+2, a[1]);
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test(expected = IllegalArgumentException.class) public void
            testAddReducingStatementThrowsAIllegalArgumentExceptionPassingANullString() {
        this.unitUnderTest.addReducingStatement(null);
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test public void testCreateScriptReturnsTheRightsScriptForThePassedParameters() {
        this.unitUnderTest.addChangingStatement("CHNG");
        this.unitUnderTest.addExtendingStatement("EXT");
        this.unitUnderTest.addReducingStatement("RDC");
        assertEquals("H\n\nPEXT\n\nEXT\n\nPRCHNG\n\nCHNG\n\nPSTCHNG\n\nRDC\n\nPRDC",
                this.unitUnderTest.createScript("H", "PEXT", "PRCHNG", "PSTCHNG", "PRDC"));
    }

    /**
     * @changed OLI 12.12.2013 - Added.
     */
    @Test public void testCreateScriptReturnsTheRightsScriptForThePassedParametersNoFragments()
            {
        this.unitUnderTest.addChangingStatement("CHNG1");
        this.unitUnderTest.addChangingStatement("CHNG2");
        this.unitUnderTest.addExtendingStatement("EXT");
        this.unitUnderTest.addReducingStatement("RDC");
        assertEquals("EXT\n\nCHNG1\nCHNG2\n\nRDC",
                this.unitUnderTest.createScript("", null, "", null, ""));
    }

}