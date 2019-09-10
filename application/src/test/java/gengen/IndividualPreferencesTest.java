/*
 * IndividualPreferencesTest.java
 *
 * 24.07.2013
 *
 * (c) by O.Lieshoff
 *
 */

package gengen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests of the class <CODE>IndividualPreferences<CODE>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 24.07.2013 - Added.
 */

public class IndividualPreferencesTest {

	private static final String BASE_CODE_PATH = "./";
	private static final String BASE_CODE_PATH_FOR_PROJECT = "./proj-xxl/";
	private static final String COMPANY = "company";
	private static final String PROJECT_TOKEN = "XXL";
	private static final String USER_NAME = "user";
	private static final String USER_TOKEN = "u";

	private IndividualPreferences unitUnderTest = null;

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Before
	public void setUp() throws Exception {
		this.unitUnderTest = new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testConstructorSetsCorrectValueForBaseCodePath() throws Exception {
		assertEquals(BASE_CODE_PATH, this.unitUnderTest.getBaseCodePath());
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testConstructorSetsCorrectValueForCompanyName() throws Exception {
		assertEquals(COMPANY, this.unitUnderTest.getCompanyName());
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testConstructorSetsCorrectValueForUserName() throws Exception {
		assertEquals(USER_NAME, this.unitUnderTest.getUserName());
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testConstructorSetsCorrectValueForUserToken() throws Exception {
		assertEquals(USER_TOKEN, this.unitUnderTest.getUserToken());
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsBaseCodePath() {
		new IndividualPreferences(null, COMPANY, USER_NAME, USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsBaseCodePath() {
		new IndividualPreferences("", COMPANY, USER_NAME, USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsCompanyName() {
		new IndividualPreferences(BASE_CODE_PATH, "", USER_NAME, USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsUserName() {
		new IndividualPreferences(BASE_CODE_PATH, COMPANY, "", USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingAnEmptyStringAsUserToken() {
		new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, "");
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsCompanyName() {
		new IndividualPreferences(BASE_CODE_PATH, null, USER_NAME, USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsUserName() {
		new IndividualPreferences(BASE_CODE_PATH, COMPANY, null, USER_TOKEN);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorThrowsAnExceptionPassingANullPointerAsUserToken() {
		new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, null);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsFalsePassingAnObjectOfAnotherType() {
		assertFalse(this.unitUnderTest.equals(";op"));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsFalsePassingANullPointer() {
		assertFalse(this.unitUnderTest.equals(null));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsFalsePassingDifferentValueForBaseCodePath() {
		assertFalse(this.unitUnderTest
				.equals(new IndividualPreferences(BASE_CODE_PATH + 1, COMPANY, USER_NAME, USER_TOKEN)));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsFalsePassingDifferentValueForCompanyName() {
		assertFalse(this.unitUnderTest
				.equals(new IndividualPreferences(BASE_CODE_PATH, COMPANY + 1, USER_NAME, USER_TOKEN)));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsFalsePassingDifferentValueForUserName() {
		assertFalse(this.unitUnderTest
				.equals(new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME + 1, USER_TOKEN)));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsFalsePassingDifferentValueForUserToken() {
		assertFalse(this.unitUnderTest
				.equals(new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN + 1)));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsTrueForEqualObjects() {
		assertTrue(
				this.unitUnderTest.equals(new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN)));
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testEqualsReturnsTrueForTheSameObject() {
		assertTrue(this.unitUnderTest.equals(this.unitUnderTest));
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testGetBaseCodePathProjectTokenReturnsAlternatePathForProjectToken() {
		this.unitUnderTest.addAlternateBaseCodePath(PROJECT_TOKEN, BASE_CODE_PATH_FOR_PROJECT);
		assertEquals(BASE_CODE_PATH_FOR_PROJECT, this.unitUnderTest.getBaseCodePath(PROJECT_TOKEN));
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testGetBaseCodePathProjectTokenReturnsGeneralBaseCodePathIfNoAlternExists() {
		assertEquals(BASE_CODE_PATH, this.unitUnderTest.getBaseCodePath(PROJECT_TOKEN));
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testGetBaseCodePathesReturnsTheCorrectStringWithTheStoredPathes() {
		this.unitUnderTest.addAlternateBaseCodePath(PROJECT_TOKEN, BASE_CODE_PATH_FOR_PROJECT);
		assertEquals(BASE_CODE_PATH + ";" + PROJECT_TOKEN + "=" + BASE_CODE_PATH_FOR_PROJECT,
				this.unitUnderTest.getBaseCodePathes());
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testGetBaseCodePathAlternateTokensReturnsCorrectListOfTokens() {
		this.unitUnderTest.addAlternateBaseCodePath(PROJECT_TOKEN + 1, BASE_CODE_PATH_FOR_PROJECT + 1);
		this.unitUnderTest.addAlternateBaseCodePath(PROJECT_TOKEN, BASE_CODE_PATH_FOR_PROJECT);
		String[] tokens = this.unitUnderTest.getBaseCodePathAlternateTokens();
		assertNotNull(tokens);
		assertEquals(2, tokens.length);
		assertEquals(PROJECT_TOKEN, tokens[0]);
		assertEquals(PROJECT_TOKEN + 1, tokens[1]);
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testHashCodeReturnsSameValueForEqualObjects() {
		assertEquals(this.unitUnderTest.hashCode(),
				new IndividualPreferences(BASE_CODE_PATH, COMPANY, USER_NAME, USER_TOKEN).hashCode());
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testHashCodeReturnsSameValueForSameObject() {
		assertEquals(this.unitUnderTest.hashCode(), this.unitUnderTest.hashCode());
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testSetBaseCodePathSetsThePassedValue() throws Exception {
		this.unitUnderTest.setBaseCodePath(BASE_CODE_PATH + 1);
		assertEquals(BASE_CODE_PATH + 1, this.unitUnderTest.getBaseCodePath());
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testSetCompanyNameSetsThePassedValue() throws Exception {
		this.unitUnderTest.setCompanyName(COMPANY + 1);
		assertEquals(COMPANY + 1, this.unitUnderTest.getCompanyName());
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testSetUserNameSetsThePassedValue() throws Exception {
		this.unitUnderTest.setUserName(USER_NAME + 1);
		assertEquals(USER_NAME + 1, this.unitUnderTest.getUserName());
	}

	/**
	 * @changed OLI 10.07.2014 - Added.
	 */
	@Test
	public void testSetUserTokenSetsThePassedValue() throws Exception {
		this.unitUnderTest.setUserToken(USER_TOKEN + 1);
		assertEquals(USER_TOKEN + 1, this.unitUnderTest.getUserToken());
	}

	/**
	 * @changed OLI 24.07.2013 - Added.
	 */
	@Test
	public void testToStringReturnsTheRightStringRepresentation() {
		assertEquals("BaseCodePath=" + BASE_CODE_PATH + ",CompanyName=" + COMPANY + ",UserName=" + USER_NAME
				+ ",UserToken=" + USER_TOKEN, this.unitUnderTest.toString());
	}

}