package de.ollie.archimedes.alexandrian.gui.swing.views.main.menu;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for class "MainViewMenuBar".
 *
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewMenuBarTest {

	@Mock
	private MainViewMenuBarComponentFactory componentFactory;
	@Mock
	private JMenu menuFile;
	@Mock
	private JMenuItem menuItemFileQuit;

	private MainViewMenuBar unitUnderTest;

	@Before
	public void setUp() {
//		when(this.componentFactory.createJMenu("File")).thenReturn(this.menuFile);
//		when(this.componentFactory.createJMenuItem("Quit")).thenReturn(this.menuItemFileQuit);
//		this.unitUnderTest = spy(new MainViewMenuBar(this.componentFactory));
	}

	@Test
	public void test() {
	}

//	@Test
//	public void menuFileQuitClicked_FiresEventForFILE_QUIT() {
//		// Prepare
//		ActionEvent event = mock(ActionEvent.class);
//
//		when(event.getSource()).thenReturn(this.menuItemFileQuit);
//
//		MainViewMenuBarListener listener = mock(MainViewMenuBarListener.class);
//		this.unitUnderTest.addMainViewMenuBarListener(listener);
//		// Run
//		this.unitUnderTest.actionPerformed(event);
//		// Check
//		verify(listener, times(1)).menuItemSelected(MainViewMenuItemId.FILE_QUIT, Optional.empty());
//		verify(this.menuFile, times(1)).add(this.menuItemFileQuit);
//	}

}