/*
 * ConstructorImportExtender.java
 *
 * 29.04.2016
 *
 * (c) by O. Lieshoff
 *
 */

package archimedes.legacy.acf;

import archimedes.legacy.acf.util.ImportList;
import archimedes.legacy.model.ColumnModel;

/**
 * An interface for classes which are extending the imports while calling for a constructor generation.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 29.04.2016 - Added.
 */

public interface ConstructorImportExtender {

	/**
	 * Opens the import list of the code generator for new imports. It is called while creating a constructor.
	 *
	 * @param imports The import list of the code generator.
	 * @param column  The column which the imports are to do for.
	 *
	 * @changed OLI 29.04.2016 - Added.
	 */
	abstract public void addImports(ImportList imports, ColumnModel column);

}