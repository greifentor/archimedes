/*
 * DO NOT REMOVE !!!
 * 

 * CodeUtil.java
 *
 * 24.07.2013
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.util;

import static corentx.util.Checks.ensure;

import corentx.dates.PDate;
import corentx.util.Direction;
import corentx.util.Str;
import gengen.IndividualPreferences;

/**
 * A utility class for code generation.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 24.07.2013 - Added.
 */

public class CodeUtil {

	private PDate date = null;
	private IndividualPreferences individualPreferences = null;
	private String ownerName = null;

	/**
	 * Creates a new code util with the passed parameters.
	 *
	 * @param date                  The date of creation for comments.
	 * @param individualPreferences The individual preferences.
	 * @param ownerName             The name of the code owner (or <CODE>null</CODE> if the company name of the
	 *                              individual preferences should be used).
	 * @throws IllegalArgumentException Passing a null pointer.
	 *
	 * @changed OLI 24.07.2013 - Added.
	 */
	public CodeUtil(PDate date, IndividualPreferences individualPreferences, String ownerName)
			throws IllegalArgumentException {
		super();
		ensure(date != null, "date cannot be null.");
		ensure(individualPreferences != null, "individual preferences cannot be null.");
		this.date = date;
		this.individualPreferences = individualPreferences;
		this.ownerName = ownerName;
	}

	/**
	 * Creates a javadoc changed tag for the passed data.
	 *
	 * @param comment The comment for the change.
	 * @return A javadoc changed tag with the authors data.
	 * @throws IllegalArgumentException Passing a null pointer or an empty string.
	 *
	 * @changed OLI 24.07.2013 - Added.
	 */
	public String createChangedTag(String comment) throws IllegalArgumentException {
		ensure(comment != null, "comment cannot be null.");
		ensure(!comment.isEmpty(), "comment cannot be empty.");
		return "@changed " + Str.pumpUp(this.getUserToken(), " ", 3, Direction.RIGHT) + " " + this.date + " - "
				+ comment;
	}

	private String getUserToken() {
		return this.individualPreferences.getUserToken();
	}

	/**
	 * Creates a class comment with the passed parameters.
	 *
	 * @param classComment     The comment of the class which the comment is to create for.
	 * @param generatedWarning Set this flag to create a warning which marks the class as generated.
	 * @return A String with the class comment.
	 * @throws IllegalArgumentException Passing a null pointer or an empty string.
	 *
	 * @changed OLI 24.07.2013 - Added.
	 */
	public String createClassComment(String classComment, boolean generatedWarning) throws IllegalArgumentException {
		ensure(classComment != null, "class comment cannot be null.");
		ensure(!classComment.isEmpty(), "class comment cannot be empty.");
		String s = "/**\n" + " * " + classComment + "\n" + " *\n" + " * @author " + this.getUserName() + "\n" + " *\n"
				+ " * " + this.createChangedTag("Generated.") + "\n";
		if (generatedWarning) {
			s += " *\n";
			s += " * GENERATED AUTOMATICALLY !!!\n";
			s += " *\n";
			s += " * Please, don't change manually.\n";
		}
		s += " */\n";
		return s;
	}

	private String getUserName() {
		return this.individualPreferences.getUserName();
	}

	/**
	 * Creates a class header comment with the passed parameters.
	 *
	 * @param className        The name of the class which the comment is to create for.
	 * @param generatedWarning Set this flag to create a warning which marks the class as generated.
	 * @return A String with the class header comment.
	 * @throws IllegalArgumentException Passing a null pointer or an empty string.
	 *
	 * @changed OLI 24.07.2013 - Added.
	 */
	public String createClassHeaderComment(String className, boolean generatedWarning) throws IllegalArgumentException {
		ensure(className != null, "class name cannot be null.");
		ensure(!className.isEmpty(), "class name cannot be empty.");
		String s = "/*\n" + " * " + className + ".java\n" + " *\n" + " * " + this.date + "\n" + " *\n" + " * (c) by "
				+ this.getCompanyName() + "\n" + " *\n";
		if (generatedWarning) {
			s += " * GENERATED AUTOMATICALLY !!!\n";
			s += " *\n";
			s += " * Please, don't change manually.\n";
			s += " *\n";
		}
		s += " */\n";
		return s;
	}

	private String getCompanyName() {
		if ((this.ownerName == null) || this.ownerName.isEmpty()) {
			return this.individualPreferences.getCompanyName();
		}
		return this.ownerName;
	}

}