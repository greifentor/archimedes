package de.ollie.archimedes.alexandrian.gui.swing.views.main.menu;

import java.util.Optional;

/**
 * A listener for main view menu bars.
 *
 * @author ollie
 *
 */
public interface MainViewMenuBarListener {

	/**
	 * Is called if a menu item of the main view menu has been clicked.
	 * 
	 * @param menuItemId    The id of the menu item which has been clicked.
	 * @param menuItemParam An optional with a parameter for the menu item.
	 */
	void menuItemSelected(MainViewMenuItemId menuItemId, Optional<String> menuItemParameter);

}