/*
 * ArtifactGenerator.java
 *
 * 31.03.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import java.util.List;

import archimedes.legacy.cf.gui.ProgressFrame;
import archimedes.legacy.model.DataModel;
import gengen.IndividualPreferences;

/**
 * An interface for the artifact generators.
 *
 * <T> The type of objects which the artifacts are generated for.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 31.03.2018 - Added.
 */

public interface ArtifactGenerator<T> {

	/**
	 * Generates the artifacts and adds them to the passed artifact list.
	 *
	 * @param dm            The data model which the artifacts are created for.
	 * @param artifacts     The list which the generated artifact is to add to.
	 * @param progressFrame A class with the frame which shows the progress of the generation process.
	 * @param ip            The individual preferences for the code generation process.
	 * @param otgc          An objects to generate collection to decide if an object is to integrate into a generation
	 *                      process.
	 */
	abstract public void generate(DataModel dm, List<Artifact> artifacts, ProgressFrame progressFrame,
			IndividualPreferences ip, ObjectsToGenerateCollection otgc);

	/**
	 * Returns the name of the artifact generator for GUI purpose.
	 *
	 * @return The name of the artifact generator for GUI purpose.
	 */
	abstract public String getName();

	/**
	 * This method should return <CODE>true</CODE> if the artifact generator creates deprecated code.
	 *
	 * @return <CODE>true</CODE> if the code generated by the artifact generator is deprecated.
	 */
	abstract public boolean isDeprecated();

	/**
	 * This method should return <CODE>true</CODE> if the artifact generator is suspended for the current generation
	 * run.
	 *
	 * @return <CODE>true</CODE> if the code generated by the artifact generator is suspended for the current generation
	 *         run.
	 */
	abstract public boolean isTemporarilySuspended();

	/**
	 * Set this flag if the artifact generator should be suspended for the current generation run.
	 *
	 * @param temporarilySuspended Set this flag if the artifact generator should be suspended for the current
	 *                             generation run.
	 */
	abstract public void setTemporarilySuspended(boolean temporarilySuspended);

}