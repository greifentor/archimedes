package de.ollie.archimedes.alexandrian.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Commands for the archimedes shell.
 *
 * @author ollie
 *
 */
@ShellComponent
public class ArchimedesShell {

	@ShellMethod("Performs a health check for the application.")
	public String health() {
		return "Anything all right, Clyde :o)";
	}

	@ShellMethod("[fileName] Imports the liquibase database change log file with the passed name. The work directory will be changed to the path which the file is stored into.")
	public String importFile(String fileName) {
		return "Done nothing with: " + fileName; // HIER SOLLTE EIN SERVICE AUFGERUFEN WERDEN !!!
	}

}