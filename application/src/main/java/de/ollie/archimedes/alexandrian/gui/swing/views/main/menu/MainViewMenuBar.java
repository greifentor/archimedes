package de.ollie.archimedes.alexandrian.gui.swing.views.main.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * A menu bar for the main view.
 *
 * @author ollie
 *
 */
public class MainViewMenuBar extends JMenuBar implements ActionListener {

	private final MainViewMenuBarComponentFactory componentFactory;

	private JMenu menuFile = null;
	private JMenuItem menuItemFileQuit = null;
	private List<MainViewMenuBarListener> listeners = new ArrayList<>();

	/**
	 * Creates a new MainViewMenuBar object.
	 * 
	 * @param componentFactory A factory for the components of the main view menu bar.
	 */
	public MainViewMenuBar(MainViewMenuBarComponentFactory componentFactory) {
		super();
		this.componentFactory = componentFactory;
		this.menuFile = this.componentFactory.createJMenu("File");
		this.menuItemFileQuit = this.componentFactory.createJMenuItem("Quit");
		this.add(this.menuFile);
		this.menuFile.add(this.menuItemFileQuit);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.menuItemFileQuit) {
			fireMenuItemSelected(MainViewMenuItemId.FILE_QUIT, Optional.empty());
		}
	}

	/**
	 * Adds the passed Listener to the list of observing objects.
	 * 
	 * @param listener The listener to add.
	 */
	public void addMainViewMenuBarListener(MainViewMenuBarListener listener) {
		this.listeners.add(listener);
	}

	protected void fireMenuItemSelected(MainViewMenuItemId mainViewMenuItemId, Optional<String> menuItemParameter) {
		for (MainViewMenuBarListener listener : this.listeners) {
			try {
				listener.menuItemSelected(mainViewMenuItemId, menuItemParameter);
			} catch (Exception e) {
				// NOP.
			}
		}
	}

}