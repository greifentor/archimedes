/*
 * CodeUtilTest.java
 *
 * 24.07.2013
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.util;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import corentx.dates.PDate;
import gengen.IndividualPreferences;

/**
 * Tests of the class <CODE>CodeUtil</CODE>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 24.07.2013 - Added.
 */

public class CodeUtilTest {

	private static final String BASE_CODE_PATH = "./";
	private static final String CLASS_NAME = "ClassName";
	private static final String COMMENT = "comment";
	private static final String COMPANY = "company";
	private static final PDate DATE = new PDate(20130724);
	private static final String OWNER_NAME = "owner";
	private static final String USER_NAME = "name";
	private static final String USER_TOKEN = "u";

	private CodeUtil unitUnderTest = null;

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new CodeUtil(DATE,
				new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN), null);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsDate() {
		new CodeUtil(null, new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN), null);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsIndividualPreferences() {
		new CodeUtil(DATE, null, null);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testCreateChangedTagReturnsTheRightString() {
		assertEquals("@changed " + USER_TOKEN + "   24.07.2013 - " + COMMENT,
				this.unitUnderTest.createChangedTag(COMMENT));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateChangedTagThrowsAnExceptionPassingAnEmptyStringAsComment() {
		this.unitUnderTest.createChangedTag("");
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateChangedTagThrowsAnExceptionPassingANullPointerAsComment() {
		this.unitUnderTest.createChangedTag(null);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testCreateClassCommentReturnsTheClassComment() {
		assertEquals("/**\n * " + COMMENT + "\n *\n * @author " + USER_NAME + "\n *\n * " + "@changed " + USER_TOKEN
				+ "   " + DATE + " - Generated.\n */\n", this.unitUnderTest.createClassComment(COMMENT, false));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testCreateClassCommentReturnsTheClassCommentWithGeneratedFlagSet() {
		assertEquals(
				"/**\n * " + COMMENT + "\n *\n * @author " + USER_NAME + "\n *\n * " + "@changed " + USER_TOKEN + "   "
						+ DATE + " - Generated.\n *\n * GENERATED "
						+ "AUTOMATICALLY !!!\n *\n * Please, don't change manually.\n */\n",
				this.unitUnderTest.createClassComment(COMMENT, true));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateClassCommentThrowsAnExceptionPassingAnEmptyStringAsComment() {
		this.unitUnderTest.createClassComment("", true);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateClassCommentThrowsAnExceptionPassingANullPointerAsComment() {
		this.unitUnderTest.createClassComment(null, true);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testCreateClassHeaderCommentReturnsTheCommentString() {
		assertEquals("/*\n * " + CLASS_NAME + ".java\n *\n * " + DATE + "\n *\n * (c) by " + COMPANY + "\n *\n */\n",
				this.unitUnderTest.createClassHeaderComment(CLASS_NAME, false));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testCreateClassHeaderCommentReturnsTheCommentStringWithEmptyOwnerName() {
		this.unitUnderTest = new CodeUtil(DATE,
				new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN), "");
		assertEquals("/*\n * " + CLASS_NAME + ".java\n *\n * " + DATE + "\n *\n * (c) by " + COMPANY + "\n *\n */\n",
				this.unitUnderTest.createClassHeaderComment(CLASS_NAME, false));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testCreateClassHeaderCommentReturnsTheCommentStringWithGeneratedWarning() {
		assertEquals(
				"/*\n * " + CLASS_NAME + ".java\n *\n * " + DATE + "\n *\n * (c) by " + COMPANY
						+ "\n *\n * GENERATED AUTOMATICALLY !!!\n *\n * Please, don't change " + "manually.\n *\n */\n",
				this.unitUnderTest.createClassHeaderComment(CLASS_NAME, true));
	}

	/**
	 * @changed OLI 05.07.2016 - Added.
	 */
	@Test
	public void testCreateClassHeaderCommentReturnsTheCommentWithOwnerName() {
		this.unitUnderTest = new CodeUtil(DATE,
				new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN), OWNER_NAME);
		assertEquals("/*\n * " + CLASS_NAME + ".java\n *\n * " + DATE + "\n *\n * (c) by " + OWNER_NAME + "\n *\n */\n",
				this.unitUnderTest.createClassHeaderComment(CLASS_NAME, false));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateClassHeaderCommentThrowsAnExceptionPassingAnEmptyStringAsClassName() {
		this.unitUnderTest.createClassHeaderComment("", false);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testCreateClassHeaderCommentThrowsAnExceptionPassingAnNullPointerAsClassName() {
		this.unitUnderTest.createClassHeaderComment(null, false);
	}

}