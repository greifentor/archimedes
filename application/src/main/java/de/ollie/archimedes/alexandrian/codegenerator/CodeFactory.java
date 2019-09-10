package de.ollie.archimedes.alexandrian.codegenerator;

import archimedes.legacy.model.DataModel;

/**
 * An interface for code generators.
 *
 * @author ollie
 *
 */
public interface CodeFactory {

	/**
	 * Adds the passed listener to observers of the code factory.
	 *
	 * @param listener The listener to add.
	 */
	void addCodeFactoryListener(CodeFactoryListener listener);

	/**
	 * Generates code into the passed path.
	 * 
	 * @param path The path which the code is to create into.
	 * @return "true" if the generation process is successful.
	 */
	boolean generate(String path);

	/**
	 * Removes the passed listener from the observers of the code factory.
	 *
	 * @param listener The listener to remove.
	 */
	void removeCodeFactoryListener(CodeFactoryListener listener);

	/**
	 * Sets the passed data model for the code factory.
	 * 
	 * @param dataModel The data model to set.
	 */
	void setDataModel(DataModel dataModel);

}