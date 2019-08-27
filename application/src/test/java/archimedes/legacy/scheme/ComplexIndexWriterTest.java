/*
 * ComplexIndexWriterTest.java
 *
 * 16.12.2011
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import archimedes.legacy.model.ComplexIndicesToSTFWriter;
import archimedes.legacy.model.DiagrammModel;
import archimedes.legacy.model.IndexListCleaner;
import archimedes.model.IndexMetaData;
import corent.files.StructuredTextFile;

/**
 * Tests zur Klasse <CODE>ComplexIndexWriter</CODE>.
 * 
 * @author ollie
 * 
 * @changed OLI 16.12.2011 - Hinzugef&uuml;gt.
 */

public class ComplexIndexWriterTest {

	private ComplexIndicesToSTFWriter writer = null;
	private ComplexIndexWriter unitUnderTest = null;
	private DiagrammModel diagram = null;
	private IndexListCleaner cleaner = null;
	private IndexMetaData[] indices = null;
	private StructuredTextFile stf = null;

	/**
	 * @changed OLI 21.11.2011 - Hinzugef&uuml;gt.
	 */
	@Before
	public void setUp() throws Exception {
		this.indices = new IndexMetaData[0];
		this.diagram = this.createDiagramMock();
		this.cleaner = this.createIndexListCleaner();
		this.stf = new StructuredTextFile("tmp");
		this.writer = this.createComplexIndicesToSTFWriterMock();
		this.unitUnderTest = new ComplexIndexWriter(this.cleaner, this.writer);
	}

	private DiagrammModel createDiagramMock() {
		DiagrammModel d = EasyMock.createMock(DiagrammModel.class);
		EasyMock.expect(d.getComplexIndices()).andReturn(this.indices);
		EasyMock.replay(d);
		return d;
	}

	private IndexListCleaner createIndexListCleaner() {
		IndexListCleaner ilc = EasyMock.createMock(IndexListCleaner.class);
		ilc.clean(this.diagram);
		EasyMock.replay(ilc);
		return ilc;
	}

	private ComplexIndicesToSTFWriter createComplexIndicesToSTFWriterMock() {
		ComplexIndicesToSTFWriter ciw = EasyMock.createMock(ComplexIndicesToSTFWriter.class);
		ciw.write(this.stf, this.indices);
		EasyMock.replay(ciw);
		return ciw;
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPassingANullPointerAsComplexIndicesToSTFWriter() throws Exception {
		new ComplexIndexWriter(this.cleaner, null);
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testConstructorPassingANullPointerAsIndexListCleaner() throws Exception {
		new ComplexIndexWriter(null, this.writer);
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWritePassingANullPointerAsStrcuturedTextFile() throws Exception {
		this.unitUnderTest.store(null, this.diagram);
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testWritePassingANullPointerAsDiagrammModel() throws Exception {
		this.unitUnderTest.store(this.stf, null);
	}

	/**
	 * @changed OLI 19.12.2011 - Hinzugef&uuml;gt.
	 */
	@Test
	public void testWriteAfterARegularRunCleanerAndWriterShouldCalled() throws Exception {
		this.unitUnderTest.store(this.stf, this.diagram);
		EasyMock.verify(this.cleaner);
		EasyMock.verify(this.diagram);
		EasyMock.verify(this.writer);
	}

}