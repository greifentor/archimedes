package de.ollie.archimedes.alexandrian.gui.swing.views.main.menu;

import org.springframework.stereotype.Component;

/**
 * A component which provides a menu bar for the main view.
 *
 * @author ollie
 *
 */
@Component
public class MainViewMenuBarFactory {

	private final MainViewMenuBarComponentFactory componentFactory;

	/**
	 * Creates a new MainViewMenuBarFactory with the passed parameters.
	 *
	 * @param componentFactory A component factory for the components of the main view menu bar.
	 */
	public MainViewMenuBarFactory(MainViewMenuBarComponentFactory componentFactory) {
		super();
		this.componentFactory = componentFactory;
	}

	/**
	 * Creates a menu bar for the main view of the Archimedes GUI.
	 * 
	 * @return A menu bar for the main view of the Archimedes GUI.
	 */
	public MainViewMenuBar create() {
		return new MainViewMenuBar(this.componentFactory);
	}

}