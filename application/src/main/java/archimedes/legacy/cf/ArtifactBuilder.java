/*
 * DomainCodeBuilder.java
 *
 * 31.03.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf;

import static corentx.util.Checks.ensure;

import archimedes.legacy.model.DataModel;
import gengen.IndividualPreferences;

/**
 * An interface for code builders.
 *
 * <T> The type of class which the code builder is working for.
 * 
 * @author O. Lieshoff
 *
 * @changed OLI 31.03.2018 - Added.
 */

abstract public class ArtifactBuilder<T> {

	protected DataModel dataModel = null;
	protected IndividualPreferences ip = null;
	protected T o = null;

	/**
	 * Creates a new code builder with the passed parameters.
	 *
	 * @param o         The object which the code is to build for.
	 * @param dataModel The data model which the code is created for.
	 * @param ip        The individual preferences for the code generation process.
	 * @throws IllegalArgumentException Passing a null pointer.
	 *
	 * @changed OLI 01.04.2018 - Added.
	 */
	public ArtifactBuilder(T o, DataModel dataModel, IndividualPreferences ip) {
		super();
		ensure(dataModel != null, "data model cannot be null.");
		ensure(ip != null, "individual preferences cannot be null.");
		ensure(o != null, "object which the code is to build for cannot be null.");
		this.dataModel = dataModel;
		this.ip = ip;
		this.o = o;
	}

	/**
	 * Creates an artifact for the object passed to the constructor.
	 *
	 * @return The artifact for the object passed to the constructor.
	 *
	 * @changed OLI 31.03.2018 - Added.
	 */
	abstract public Artifact build();

	/**
	 * Returns <CODE>true</CODE> no code to generate for the passed object.
	 *
	 * @param o         The object which is to check for code generation.
	 * @param dataModel The data model which the domain belongs to.
	 *
	 * @changed OLI 31.03.2018 - Added.
	 */
	public boolean isSuppressCodeGeneration() {
		return false;
	};

}