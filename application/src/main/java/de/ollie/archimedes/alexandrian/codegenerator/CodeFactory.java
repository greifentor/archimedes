package de.ollie.archimedes.alexandrian.codegenerator;

/**
 * An interface for code generators.
 *
 * @author ollie
 *
 */
public interface CodeFactory {

	/**
	 * Generates code into the passed path.
	 * 
	 * @param path The path which the code is to create into.
	 * @return "true" if the generation process is successful.
	 */
	void generate(String path);

}