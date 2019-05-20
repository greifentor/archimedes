package de.ollie.archimedes.alexandrian.gui.swing.views.main.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.springframework.stereotype.Component;

/**
 * A factory for the components of a main view menu bar.
 *
 * @author ollie
 *
 */
@Component
public class MainViewMenuBarComponentFactory {

	/**
	 * Creates a JMenu with the passed title.
	 * 
	 * @param title The title for the JMenu.
	 */
	public JMenu createJMenu(String title) {
		return new JMenu(title);
	}

	/**
	 * Creates a JMenuItem with the passed title.
	 * 
	 * @param title The title for the JMenuItem.
	 */
	public JMenuItem createJMenuItem(String title) {
		return new JMenuItem(title);
	}

}