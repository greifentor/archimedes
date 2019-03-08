package de.ollie.archimedes.alexandrian;

import javax.swing.JOptionPane;

/**
 * Starts the Archimedes (Alexandrian) application.
 *
 * @author ollie
 *
 */
public class ArchimedesStarter {

	public void startApplication(String[] args) {
		System.out.println("Archimedes (Alexandrian) is starting ...");
		JOptionPane.showMessageDialog(null, "Archimedes (Alexandrian) started.", "Archimedes (Alexandrian)",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}