/*
 * ModelXMLWriter.java
 *
 * 14.02.2016
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.scheme.xml;

import static corentx.util.Checks.ensure;

import java.io.IOException;

import archimedes.legacy.model.DataModel;
import corent.gui.ExtendedColor;
import corentx.io.FileUtil;

/**
 * This class stores a data model in a XML file.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 14.02.2016 - Added.
 */

public class ModelXMLWriter {

	private ObjectFactory objectFactory = null;

	/**
	 * Creates a new model XML writer with the passed parameters.
	 *
	 * @param objectFactory An object factory for instance generation.
	 * @throws IllegalArgumentException Passing a null pointer.
	 *
	 * @changed OLI 16.06.2016 - Added.
	 */
	public ModelXMLWriter(ObjectFactory objectFactory) {
		super();
		ensure(objectFactory != null, "object factory cannot be null");
		this.objectFactory = objectFactory;
	}

	/**
	 * Stores the passed data model in a XML file with the passed name.
	 *
	 * @param dataModel The data model to store.
	 * @param fileName  The name of the file which the data model is to store into.
	 * @throws IOException If an error occurs while writing the diagram.
	 *
	 * @changed OLI 14.02.2016 - Added.
	 */
	public void store(DataModel dataModel, String fileName) throws IOException {
		String s = new DiagramXMLBuilder().buildXMLContentForDiagram(dataModel,
				this.objectFactory.getPalette().getColors().toArray(new ExtendedColor[0]));
		FileUtil.writeTextToFile(fileName, false, s);
	}

}