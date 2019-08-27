/*
 * UseCaseOpenComplexIndicesFrameAndCloseItTest.java
 *
 * 15.12.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.gui.indices;


import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;
import org.netbeans.jemmy.operators.*;

import archimedes.legacy.model.*;
import archimedes.legacy.scheme.*;


/**
 * &Ouml;ffnet den Frame zur Wartung von komplexen Indices und schliesst ihn wieder.
 *
 * @author ollie
 *
 * @changed OLI 15.12.2011 - Hinzugef&uuml;gt.
 */

public class UseCaseOpenComplexIndicesFrameAndCloseItTest {

    private JFrameOperator frame = null;

    /**
     * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
     */
    @Before
    public void setUp() throws Exception {
        Diagramm d = new Diagramm();
        this.frame =
                ComplexIndicesAdministrationFrameUtil.openComplexIndicesAdministrationFrame(d);
    }

    /**
     * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
     */
    @After
    public void tearDown() throws Exception {
        ComplexIndicesAdministrationFrameUtil.disposeComplexIndicesAdministrationFrame();
    }

    /**
     * @changed OLI 15.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testFrameIsOpenedAfterCreatingANewFrame() throws Exception {
        assertTrue(this.frame.isVisible());
    }

    /**
     * @changed OLI 15.12.2011 - Hinzugef&uuml;gt.
     */
    @Test
    public void testFrameIsClosedAfterPressingTheCloseButton() throws Exception {
        ComplexIndicesAdministrationFrameUtil.pressCloseButton();
        Thread.sleep(100);
        assertFalse(this.frame.isVisible());
    }

}