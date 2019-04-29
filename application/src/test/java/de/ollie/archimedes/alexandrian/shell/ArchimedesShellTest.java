package de.ollie.archimedes.alexandrian.shell;

import static org.junit.Assert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Unit tests of class "ArchimedesShell".
 * 
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class ArchimedesShellTest {

	@InjectMocks
	private ArchimedesShell unitUnderTest;

	@Test
	public void health_ReturnsAConstantString() {
		// Prepare
		String expected = "Anything all right, Clyde :o)";
		// Run
		String returned = this.unitUnderTest.health();
		// Check
		assertThat(returned, equalTo(expected));
	}

	@Test
	public void importFile_ReturnsAStringWithThePassedFileName() {
		// Prepare
		String fileName = "fileName";
		String expected = "Done nothing with: " + fileName;
		// Run
		String returned = this.unitUnderTest.importFile(fileName);
		// Check
		assertThat(returned, equalTo(expected));
	}

}