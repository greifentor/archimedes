package de.ollie.archimedes.alexandrian.service.liquibase;

import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import de.ollie.archimedes.alexandrian.service.DatabaseModelSO;

/**
 * Unit tests for class "LiquibaseXMLModelReader".
 *
 * @author ollie
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class LiquibaseXMLModelReaderTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@InjectMocks
	private LiquibaseXMLModelReader unitUnderTest;

	@Test(expected = FileNotFoundException.class)
	public void readModel_PassAnUnexistingFileName_ThrowsAnException() throws Exception {
		// Run
		String fileName = "/an/unexisting/file.name";
		File file = new File(fileName);
		// Check
		this.unitUnderTest.readModel(file);
	}

	@Test
	public void readModel_PassAnExistingFileName_ReturnsTheModelStoredInTheFileWithThePassedName() throws Exception {
		// Run
		DatabaseModelSO expected = new DatabaseModelSO();
		String fileContent = "";
		String fileName = "liquibase-test.xml";
		File file = writeFile(fileName, fileContent);
		// Run
		DatabaseModelSO returned = this.unitUnderTest.readModel(file);
		// Check
		assertThat(returned, equalTo(expected));
	}

	private File writeFile(String fileName, String content) throws IOException {
		File file = this.tempFolder.newFile(fileName);
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(content);
		fileWriter.close();
		return file;
	}

};