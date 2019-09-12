package de.ollie.archimedes.alexandrian.gui.swing.views.main.menu;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests for class "MainViewMenuBarComponentFactory".
 *
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class MainViewMenuBarComponentFactoryTest {

	@InjectMocks
	private MainViewMenuBarComponentFactory unitUnderTest;

	@Test
	public void createJMenu_PassATitle_ReturnsAJMenuWithThePassedTitle() {
		// Prepare
		String title = "title";
		// Run
		JMenu returned = this.unitUnderTest.createJMenu(title);
		// Check
		assertThat(returned.getText(), equalTo(title));
	}

	@Test
	public void createJMenuItem_PassATitle_ReturnsAJMenuItemWithThePassedTitle() {
		// Prepare
		String title = "title";
		// Run
		JMenuItem returned = this.unitUnderTest.createJMenuItem(title);
		// Check
		assertThat(returned.getText(), equalTo(title));
	}

}