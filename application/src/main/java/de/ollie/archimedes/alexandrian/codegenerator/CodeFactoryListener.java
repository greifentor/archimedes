package de.ollie.archimedes.alexandrian.codegenerator;

/**
 * An interface for code factory listeners.
 *
 * @author ollie
 *
 */
public interface CodeFactoryListener {

	/**
	 * If an exception occurred while code generation.
	 * 
	 * @param exception The exception which has been occurred.
	 */
	void exceptionOccurred(Exception exception);

	/**
	 * If a new message has been occurred.
	 * 
	 * @param message The new message which has been occurred.
	 */
	void messageAppended(String message);

	/**
	 * Is called if a progress percentage change has been detected.
	 * 
	 * @param newProgressPercentage The new value for the progress percentage.
	 */
	void progressChanged(int newProgressPercentage);

}