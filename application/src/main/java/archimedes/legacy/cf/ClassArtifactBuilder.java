/*
 * ClassArtifactBuilder.java
 *
 * 07.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import archimedes.legacy.acf.util.ImportClause;
import archimedes.legacy.acf.util.ImportList;
import archimedes.legacy.model.DataModel;
import corentx.dates.PDate;
import corentx.io.FileUtil;
import gengen.IndividualPreferences;

/**
 * A specialized artifact builder for classes.
 *
 * @param <T> The class which the class artifacts are build for.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 07.04.2018 - Added.
 */

abstract public class ClassArtifactBuilder<T> extends ArtifactBuilder<T> {

	public static final String IMPORT_PLACE_HOLDER = "$IMPORTS$";
	public static final String PACKAGE_NAME_PLACE_HOLDER = "$PACKAGE_NAME$";

	protected ImportList imports = null;
	protected ArtifactBuilderUtils utils = null;

	/**
	 * Creates a new class artifact builder with the passed parameters.
	 *
	 * @param o         The object which the class artifact is to build for.
	 * @param dataModel The data model which is the base of the code generation.
	 * @param ip        The individual preferences for the code generation process.
	 * @throws IllegalArgumentException Passing a null value.
	 */
	public ClassArtifactBuilder(T o, DataModel dataModel, IndividualPreferences ip) {
		super(o, dataModel, ip);
		this.imports = new ImportList(";op");
		this.utils = new ArtifactBuilderUtils(getObjectsName());
	}

	/**
	 * Creates a complete class code (with header) for the artifact builder.
	 *
	 * @return A complete class code (with header) for the artifact builder.
	 */
	public Artifact build() {
		String code = createHeader();
		code += buildClassSourceCode();
		code += "}";
		return new Artifact(replacePackageName(replaceImports(code)), getSourceFileName());
	}

	private String createHeader() {
		String code = createClassFileHeader();
		code += "package " + PACKAGE_NAME_PLACE_HOLDER + ";\n";
		code += "\n" + IMPORT_PLACE_HOLDER + "\n\n";
		code += "/**\n";
		code += " * " + getClassComment() + "\n";
		code += " *\n";
		code += " * @author " + this.ip.getUserName() + "\n";
		code += " *\n";
		code += " * @created " + new PDate() + "\n";
		code += " */\n";
		return code;
	}

	private String createClassFileHeader() {
		String code = "/*\n";
		code += " * " + getClassName() + ".java\n";
		code += " *\n";
		code += " * " + new PDate() + "\n";
		code += " *\n";
		code += " * (c) by " + this.ip.getCompanyName() + "\n";
		code += " */\n\n";
		return code;
	}

	private String replaceImports(String code) {
		String i = "";
		for (ImportClause ic : this.imports.getImports()) {
			i += "\nimport ";
			if (ic.isStaticImport()) {
				i += "static ";
			}
			i += ic.getImportClauseContent() + ".*;\n";
		}
		return code.replace(IMPORT_PLACE_HOLDER, i);
	}

	private String replacePackageName(String code) {
		code = code.replace(PACKAGE_NAME_PLACE_HOLDER, this.getPackageName());
		return code;
	}

	private String getSourceFileName() {
		String root = FileUtil.completePath(this.ip.getBaseCodePath());
		String p = FileUtil.completePath(this.dataModel.getBasicCodePath().replace("\\", "/"));
		String qn = this.getPackageName().replace(".", "/") + "/" + this.getClassName() + ".java";
		return root + p + (isTestClass() ? "unittests" : "src") + "/" + qn;
	}

	protected boolean isTestClass() {
		return false;
	}

	/**
	 * Returns the source of the class from head line (e. g. "public class ..." til line before closing bracket.
	 *
	 * @return The source of the class from head line (e. g. "public class ..." til line before closing bracket.
	 */
	abstract public String buildClassSourceCode();

	/**
	 * Returns the class comment.
	 *
	 * @return The class comment.
	 */
	abstract public String getClassComment();

	/**
	 * Returns the class name.
	 *
	 * @return The class name.
	 */
	abstract protected String getClassName();

	/**
	 * Returns the name of the object whose code is to create with the artifact builder.
	 *
	 * @return The name of the object whose code is to create with the artifact builder.
	 */
	abstract protected String getObjectsName();

	/**
	 * Returns the package name for the artifact builder.
	 *
	 * @return The package name for the artifact builder.
	 */
	abstract public String getPackageName();

}