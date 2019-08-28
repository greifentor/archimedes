package de.ollie.archimedes.alexandrian;

import javax.annotation.PostConstruct;

import archimedes.Archimedes;

/**
 * Starts the Archimedes (Alexandrian) application.
 *
 * @author ollie
 *
 */
// @Component
public class ArchimedesSwingStarter {

	@PostConstruct
	public void startApplication(String[] args) {
		Archimedes.main(args);
	}

}