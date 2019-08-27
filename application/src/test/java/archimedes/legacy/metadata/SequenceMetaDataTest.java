/*
 * SequenceMetaDataTest.java
 *
 * 23.04.2013
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.metadata;


import static org.junit.Assert.*;

import org.junit.*;


/**
 * Tests of the class <CODE>SequenceMetaData</CODE>.
 *
 * @author ollie
 *
 * @changed OLI 23.04.2013 - Added.
 */

public class SequenceMetaDataTest {

    private static final String NAME = "name";

    private SequenceMetaData unitUnderTest = null;

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Before public void setUp() {
        this.unitUnderTest = new SequenceMetaData(NAME);
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testConstructorSetsTheCorrectNameForTheSequence() {
        assertEquals(NAME, this.unitUnderTest.getName());
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test(expected = IllegalArgumentException.class) public void
            testConstructorThrowsAnExceptionPassingAnEmptyStringAsName() {
        new SequenceMetaData("");
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test(expected = IllegalArgumentException.class) public void
            testConstructorThrowsAnExceptionPassingANullPointerAsName() {
        new SequenceMetaData(null);
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testEqualsReturnsFalsePassingADifferentNames() {
        assertFalse(this.unitUnderTest.equals(new SequenceMetaData(NAME+1)));
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testEqualsReturnsFalsePassingAnotherClassType() {
        assertFalse(this.unitUnderTest.equals(":op"));
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testEqualsReturnsFalsePassingANullPointer() {
        assertFalse(this.unitUnderTest.equals(null));
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testEqualsReturnsTruePassingAnEqualSequenceMetaData() {
        assertTrue(this.unitUnderTest.equals(new SequenceMetaData(NAME)));
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testEqualsReturnsTruePassingTheSameSequenceMetaData() {
        assertTrue(this.unitUnderTest.equals(this.unitUnderTest));
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testHashCodeReturnsEqualValuesPassingAnEqualSequenceMetaData() {
        assertEquals(this.unitUnderTest.hashCode(), new SequenceMetaData(NAME).hashCode());
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testHashCodeReturnsEqualValuesPassingSameSequenceMetaData() {
        assertEquals(this.unitUnderTest.hashCode(), this.unitUnderTest.hashCode());
    }

    /**
     * @changed OLI 23.04.2013 - Added.
     */
    @Test public void testToStringReturnsTheCorrectString() throws Exception {
        assertEquals("Name=" + NAME, this.unitUnderTest.toString());
    }

}