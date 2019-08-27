/*
 * DefaultUserInformationTest.java
 *
 * 24.02.2012
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;


import static org.junit.Assert.*;
import org.junit.*;


/**
 * Tests zur Klasse <CODE>DefaultUserInformation</CODE>.
 *
 * @author ollie
 *
 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
 */

public class DefaultUserInformationTest {

    private static final String USER_NAME = "user name";
    private static final String USER_TOKEN = "usr";
    private static final String VENDOR_NAME = "vendor";

    private DefaultUserInformation unitUnderTest = null;

    /**
     * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
     */
    @Before
    public void setUp() throws Exception {
        this.unitUnderTest = new DefaultUserInformation(USER_NAME, USER_TOKEN, VENDOR_NAME);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test
    public void testConstructorSetsTheUserNameCorrectly() throws Exception {
        assertEquals(USER_NAME, this.unitUnderTest.getUserName());
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test
    public void testConstructorSetsTheUserTokenCorrectly() throws Exception {
        assertEquals(USER_TOKEN, this.unitUnderTest.getUserToken());
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test
    public void testConstructorSetsTheVendorNameCorrectly() throws Exception {
        assertEquals(VENDOR_NAME, this.unitUnderTest.getVendorName());
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsUserName()
            throws Exception {
        new DefaultUserInformation("", USER_TOKEN, VENDOR_NAME);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsUserToken()
            throws Exception {
        new DefaultUserInformation(USER_NAME, "", VENDOR_NAME);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsVendorName()
            throws Exception {
        new DefaultUserInformation(USER_NAME, USER_TOKEN, "");
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsAnExceptionPassingANullPointerAsUserName()
            throws Exception {
        new DefaultUserInformation(null, USER_TOKEN, VENDOR_NAME);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsAnExceptionPassingANullPointerAsUserToken()
            throws Exception {
        new DefaultUserInformation(USER_NAME, null, VENDOR_NAME);
    }

    /**
     * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
     */
    @Test(expected=IllegalArgumentException.class)
    public void testConstructorThrowsAnExceptionPassingANullPointerAsVendorName()
            throws Exception {
        new DefaultUserInformation(USER_NAME, USER_TOKEN, null);
    }

}