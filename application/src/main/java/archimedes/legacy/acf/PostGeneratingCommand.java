/*
 * PostGeneratorCommand.java
 *
 * 02.10.2013
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.acf;

import java.util.List;

import archimedes.legacy.acf.io.SourceFileReader;
import archimedes.legacy.acf.io.SourceFileWriter;
import archimedes.legacy.acf.report.GenerationProcessReport;
import archimedes.legacy.model.DataModel;
import gengen.IndividualPreferences;

/**
 * A basic interface for code generator commands.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 02.10.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 */

public interface PostGeneratingCommand {

	/**
	 * A call of this method should process the command.
	 *
	 * @param model                  The data model which is the base for the code generation.
	 * @param individualPreferences  The individual preferences for the code generation.
	 * @param sourceFileWriter       A class which writes the created source file to the file system.
	 * @param sourceFileReader       A class which reads an already existing source file to the file system to look up
	 *                               for changes.
	 * @param report                 A report container where the code generator could trace report events.
	 * @param postGeneratingCommands A list with commands which are to execute after all code generators have done their
	 *                               work (e. g. creating additional classes). Add your commands to the list.
	 * @return <CODE>true</CODE> if the command is processed correctly, <CODE>false</CODE> otherwise.
	 *
	 * @changed OLI 18.04.2013 - Added.
	 */
	abstract public boolean process(DataModel dm, IndividualPreferences individualPreferences,
			SourceFileWriter sourceFileWriter, SourceFileReader sourceFileReader, GenerationProcessReport report,
			List<PostGeneratingCommand> postGeneratingCommands);

}