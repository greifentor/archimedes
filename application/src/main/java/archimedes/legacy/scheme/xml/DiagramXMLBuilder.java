/*
 * DiagramXMLBuilder.java
 *
 * 08.10.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.scheme.xml;

import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.ViewModel;
import archimedes.legacy.model.gui.GUIDiagramModel;
import corent.gui.ExtendedColor;
import corentx.xml.XMLHeaderBuilder;
import corentx.xml.XMLNode;
import corentx.xml.XMLNodeFactory;

/**
 * This class builds a XML file for a Archimedes diagram.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 08.10.2015 - Added.
 */

public class DiagramXMLBuilder {

	private String lf = "\n";
	private XMLNodeFactory xmlNodeFactory = new XMLNodeFactory();

	/**
	 * Creates a string with the XML file content for the passed Archimedes diagram.
	 *
	 * @param diagram The diagram which the XML file content is to create for.
	 * @param colors  The color palette to save.
	 * @return The XML file content (a string) for the passed diagram.
	 *
	 * @changed OLI 08.10.2015 - Added.
	 */
	public String buildXMLContentForDiagram(DataModel dataModel, ExtendedColor[] colors) {
		StringBuffer sb = new StringBuffer();
		sb.append(new XMLHeaderBuilder(1.0D, "UTF-8", true).build()).append(this.lf);
		XMLNode diagram = this.xmlNodeFactory.createXMLRootNode("Diagram");
		diagram.add(new AdditionalSQLXMLBuilder().buildNodes(dataModel, this.xmlNodeFactory));
		diagram.add(new ColorsXMLBuilder().buildNodes(this.xmlNodeFactory, colors));
		diagram.add(new ComplexIndexXMLBuilder().buildNodes(dataModel.getComplexIndices(), this.xmlNodeFactory));
		diagram.add(new DatabaseConnectionsXMLBuilder().buildNodes(dataModel, this.xmlNodeFactory));
		diagram.add(new DomainsXMLBuilder().buildNodes(dataModel, this.xmlNodeFactory));
		diagram.add(new OptionXMLBuilder().buildNodes(dataModel.getOptions(), this.xmlNodeFactory));
		diagram.add(new DiagramParameterXMLBuilder().buildNodes(dataModel, this.xmlNodeFactory));
		diagram.add(new SequenceXMLBuilder().buildNodes(dataModel, this.xmlNodeFactory));
		diagram.add(new StereotypeXMLBuilder().buildNodes(dataModel, this.xmlNodeFactory));
		diagram.add(new TableXMLBuilder().buildNodes(dataModel.getTables(), this.xmlNodeFactory));
		diagram.add(new ViewXMLBuilder().buildNodes(((GUIDiagramModel) dataModel).getViews().toArray(new ViewModel[0]),
				this.xmlNodeFactory));
		sb.append(diagram.toXML(0, 4, this.lf));
		return sb.toString();
	}

}