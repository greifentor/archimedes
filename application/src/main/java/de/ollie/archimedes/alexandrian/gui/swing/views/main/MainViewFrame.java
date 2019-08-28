package de.ollie.archimedes.alexandrian.gui.swing.views.main;

import javax.annotation.PostConstruct;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.springframework.beans.factory.annotation.Autowired;

import de.ollie.archimedes.alexandrian.gui.swing.views.main.menu.MainViewMenuBarFactory;

/**
 * A frame which presents the main view of the application
 * 
 * @author ollie
 *
 */
// @Component
public class MainViewFrame extends JFrame {

	@Autowired
	private MainViewMenuBarFactory menuBarProvider;

	@PostConstruct
	public void blubs() {
		JOptionPane.showMessageDialog(null, "Archimedes (Alexandrian) started.", "Archimedes (Alexandrian)",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}