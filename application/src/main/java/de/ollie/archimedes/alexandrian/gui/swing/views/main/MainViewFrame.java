package de.ollie.archimedes.alexandrian.gui.swing.views.main;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;

import org.springframework.stereotype.Component;

/**
 * A frame which presents the main view of the application
 * 
 * @author ollie
 *
 */
@Component
public class MainViewFrame extends JFrame {

	private JMenuBar menuBar = null;

	public void blubs() {
		JOptionPane.showMessageDialog(null, "Archimedes (Alexandrian) started.", "Archimedes (Alexandrian)",
				JOptionPane.INFORMATION_MESSAGE);
		System.exit(0);
	}

}