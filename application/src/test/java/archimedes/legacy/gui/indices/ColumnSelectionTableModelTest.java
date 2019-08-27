/*
 * ColumnSelectionTableModelTest.java
 *
 * 16.12.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.gui.indices;


import gengen.metadata.*;

import static org.junit.Assert.*;
import org.junit.*;

import archimedes.legacy.*;


/**
 * Tests zur Klasse <CODE>ColumnSelectionTableModel</CODE>.
 *
 * @author ollie
 *
 * @changed OLI 16.12.2011 - Hinzugef&uuml;gt.
 */

public class ColumnSelectionTableModelTest {

    private ClassMetaData cmd = null;
    private ColumnSelectionTableModel unitUnderTest = null;

    /**
     * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
     */
    @Before
    public void setUp() throws Exception {
        this.cmd = Utils.createClassMetaDataMock("Mock");
        this.unitUnderTest = new ColumnSelectionTableModel(this.cmd);
    }

    /**
     * @changed OLI 16.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testIsCellEditableOnAnEditableColumn() throws Exception {
        assertTrue(this.unitUnderTest.isCellEditable(4711, 1));
    }

    /**
     * @changed OLI 16.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testIsCellEditableOnAnNotEditableColumn() throws Exception {
        assertFalse(this.unitUnderTest.isCellEditable(4711, 0));
    }

}