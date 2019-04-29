package de.ollie.archimedes.alexandrian;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.ollie.archimedes.alexandrian.gui.swing.views.main.MainViewFrame;

/**
 * Starts the Archimedes (Alexandrian) application.
 *
 * @author ollie
 *
 */
@Component
public class ArchimedesSwingStarter {

	@Autowired
	MainViewFrame mainViewFrame;

	public void startApplication(String[] args) {
		System.out.println("Archimedes (Alexandrian) is starting ...");
		this.mainViewFrame.blubs();
	}

}