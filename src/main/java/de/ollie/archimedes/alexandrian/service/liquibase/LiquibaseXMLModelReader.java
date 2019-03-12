package de.ollie.archimedes.alexandrian.service.liquibase;

import java.io.File;
import java.io.FileNotFoundException;

import de.ollie.archimedes.alexandrian.service.DatabaseModelSO;

/**
 * A model reader which is able to read Liquibase XML files and convert them into a archimedes database model.
 *
 * @author ollie
 *
 */
public class LiquibaseXMLModelReader {

	/**
	 * Reads the model with the passed file name.
	 *
	 * @param file The file which is to read.
	 * @return An Archimedes database model with the content of the Liquibase XML file.
	 * @throws FileNotFoundException If the file does not exists.
	 */
	public DatabaseModelSO readModel(File file) throws FileNotFoundException {
		if (!file.exists()) {
			throw new FileNotFoundException("file does not exists: " + file.getAbsolutePath());
		}
		return new DatabaseModelSO();
	}

}