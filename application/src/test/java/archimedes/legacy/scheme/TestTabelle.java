/*
 * TestTabelle.java
 *
 * 12.05.2009
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.scheme;

import java.io.InputStream;
import java.util.List;

import archimedes.legacy.Archimedes;
import archimedes.legacy.model.DiagrammModel;
import archimedes.legacy.model.TabellenspaltenModel;
import corent.files.StructuredTextFile;
import junit.framework.TestCase;

/**
 * Testf&auml;lle f&uuml;r die Tabelle-Klasse.
 *
 * @author ollie
 *
 * @changed OLI 12.05.2009 - Hinzugef&uuml;gt.
 * @changed OLI 21.05.2009 - Erweiterung um den Testfall f&uuml;r die Methode
 *          <TT>getFieldnames(boolean, boolean, boolean)</TT>.
 * @changed OLI 28.09.2009 - Erweiterung um den Test zur Methode <TT>getPackageName()</TT>.
 * @changed OLI 06.10.2009 - Erweiterung um den Test zur Methode <TT>isOfStereotype(String)</TT>.
 */

public class TestTabelle extends TestCase {

	private DiagrammModel dm = null;

	private String killCR(String s) {
		return s.replace("\n", "").replace("\r", "");
	}

	protected void setUp() throws Exception {
		super.setUp();
		System.setProperty("archimedes.gui.ComponentDiagramm.PAGESPERCOLUMN", "99");
		System.setProperty("archimedes.gui.ComponentDiagramm.PAGESPERROW", "99");
		System.setProperty("corent.base.StrUtil.suppress.html.note", "true");
		InputStream in = Archimedes.Factory.getClass().getClassLoader().getResourceAsStream("dm/TestModel.ads");
		StructuredTextFile stf = new StructuredTextFile(in);
		try {
			stf.load();
			this.dm = new Diagramm();
			this.dm = (Diagramm) dm.createDiagramm(stf);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.setProperty("archimedes.gui.ArchimedesCommandProcessor.suppress.gui", "true");
	}

	/**
	 * Test der Methode getCodegeneratorOptionsListTag(String, String).
	 *
	 * @changed OLI 03.06.2009 - Hinzugef&uuml;gt.
	 */
	public void testGetCodegeneratorOptionsListTagStringString() {
		List<String> l = null;
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		t.setGenerateCodeOptions("<T>bla, laber</T>");
		l = t.getCodeGeneratorOptionsListTag("T", ",");
		assertEquals(l.size(), 2);
		assertEquals(l.get(0), "bla");
		assertEquals(l.get(1), "laber");
		t.setGenerateCodeOptions("<T>bla$laber</T>");
		l = t.getCodeGeneratorOptionsListTag("T", "$");
		assertEquals(l.size(), 2);
		assertEquals(l.get(0), "bla");
		assertEquals(l.get(1), "laber");
		t.setGenerateCodeOptions("<T>bla$$$laber</T>");
		l = t.getCodeGeneratorOptionsListTag("T", "$");
		assertEquals(l.size(), 2);
		assertEquals(l.get(0), "bla");
		assertEquals(l.get(1), "laber");
		t.setGenerateCodeOptions("<T>bla$$$laber</T>");
		l = t.getCodeGeneratorOptionsListTag("S", "$");
		assertEquals(l.size(), 0);
	}

	/** Test der Methode getFieldnames(boolean, boolean). */
	public void testGetFieldnamesBooleanBoolean() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		assertEquals(t.getFieldnames(true, true), "Id, Referenz, Deleted, Description, Token");
		assertEquals(t.getFieldnames(false, true), "Referenz, Deleted, Description, Token");
		assertEquals(t.getFieldnames(true, false), "Id");
		assertEquals(t.getFieldnames(false, false), "");
		assertEquals(t.getFieldnames(true, true, false), "Id, Referenz, Deleted, Description, " + "Token");
		assertEquals(t.getFieldnames(false, true, false), "Referenz, Deleted, Description, " + "Token");
		assertEquals(t.getFieldnames(true, false, false), "Id");
		assertEquals(t.getFieldnames(false, false, false), "");
	}

	/**
	 * Test der Methode getFieldnames(boolean, boolean, boolean).
	 *
	 * @changed OLI 21.05.2009 - Hinzugef&uuml;gt.
	 */
	public void testGetFieldnamesBooleanBooleanBoolean() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		assertEquals(t.getFieldnames(true, true, true), "TestTabelle.Id, TestTabelle.Referenz, "
				+ "TestTabelle.Deleted, TestTabelle.Description, TestTabelle.Token");
		assertEquals(t.getFieldnames(false, true, true),
				"TestTabelle.Referenz, " + "TestTabelle.Deleted, TestTabelle.Description, TestTabelle.Token");
		assertEquals(t.getFieldnames(true, false, true), "TestTabelle.Id");
		assertEquals(t.getFieldnames(false, false, true), "");
	}

	/**
	 * Test der Methode getFieldnamesPKFirst(boolean, boolean).
	 *
	 * @changed OLI 22.05.2009 - Hinzugef&uuml;gt.
	 * @changed OLI 28.05.2009 - Erweiterung um die Tests zu Tabellen, die nur aus Prim&auml;rschl&uuml;sseln bestehen,
	 *          bzw. keine haben.
	 */
	public void testGetFieldnamesPKFirstBooleanBoolean() {
		int i = 0;
		int leni = 0;
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		TabellenspaltenModel tsm = null;
		assertNotNull(t);
		// "Normale" Tabelle.
		assertEquals(t.getFieldnamesPKFirst(true, true), "TestTabelle.Id, TestTabelle.Referenz,"
				+ " TestTabelle.Deleted, TestTabelle.Description, TestTabelle.Token");
		assertEquals(t.getFieldnamesPKFirst(false, true), "TestTabelle.Id");
		assertEquals(t.getFieldnamesPKFirst(true, false), "Id, Referenz, Deleted, Description, " + "Token");
		assertEquals(t.getFieldnamesPKFirst(false, false), "Id");
		// Tabelle, die ausschliesslich aus Primaerschluesseln besteht.
		for (i = 0, leni = t.getTabellenspaltenCount(); i < leni; i++) {
			tsm = t.getTabellenspalteAt(i);
			tsm.setPrimarykey(true);
		}
		assertEquals(t.getFieldnamesPKFirst(true, true), "TestTabelle.Id, TestTabelle.Referenz,"
				+ " TestTabelle.Deleted, TestTabelle.Description, TestTabelle.Token");
		assertEquals(t.getFieldnamesPKFirst(false, true), "TestTabelle.Id, "
				+ "TestTabelle.Referenz, TestTabelle.Deleted, TestTabelle.Description, " + "TestTabelle.Token");
		assertEquals(t.getFieldnamesPKFirst(true, false), "Id, Referenz, Deleted, Description, " + "Token");
		assertEquals(t.getFieldnamesPKFirst(false, false), "Id, Referenz, Deleted, Description," + " Token");
		// Tabelle, die ausschliesslich aus Primaerschluesseln besteht.
		for (i = 0, leni = t.getTabellenspaltenCount(); i < leni; i++) {
			tsm = t.getTabellenspalteAt(i);
			tsm.setPrimarykey(false);
		}
		assertEquals(t.getFieldnamesPKFirst(true, true), "TestTabelle.Id, TestTabelle.Referenz,"
				+ " TestTabelle.Deleted, TestTabelle.Description, TestTabelle.Token");
		assertEquals(t.getFieldnamesPKFirst(false, true), "");
		assertEquals(t.getFieldnamesPKFirst(true, false), "Id, Referenz, Deleted, Description, " + "Token");
		assertEquals(t.getFieldnamesPKFirst(false, false), "");
	}

	/**
	 * Test der Methode <TT>isOfStereotype(String)</TT>.
	 *
	 * @changed OLI 06.10.2009 - Hinzugef&uuml;gt.
	 */
	public void testIsOfStereotypeString() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		// Negativbeispiele.
		try {
			t.isOfStereotype(null);
			fail();
		} catch (AssertionError ae) {
			assertTrue(true);
		} catch (NullPointerException npe) {
			assertTrue(true);
		}
		// Positivbeispiele.
		assertTrue(t.isOfStereotype("Deactivatable"));
		assertFalse(t.isOfStereotype("Gibt's Nicht"));
	}

	/** Test der Methode <TT>getPackageName()</TT>. */
	public void testGetPackageName() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		t.setCodeVerzeichnis(".test\\package/eins//");
		assertEquals("test.package.eins", t.getPackageName());
		t.setCodeVerzeichnis(".");
		assertEquals("", t.getPackageName());
		t.setCodeVerzeichnis("");
		assertEquals("", t.getPackageName());
	}

	/** Test der Methode makeCreateStatement(boolean, boolean, boolean). */
	public void testMakeCreateStatementBooleanBooleanBoolean() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		assertEquals(this.killCR(t.makeCreateStatement(false, false, false)),
				this.killCR("create table TestTabelle (" + "    Id bigint not null primary key default -1,"
						+ "    Referenz bigint default -1," + "    Deleted int,"
						+ "    Description varchar(100) not null," + "    Token varchar(20) default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(true, false, false)),
				this.killCR("create table TestTabelle (" + "    Id Ident not null primary key default -1,"
						+ "    Referenz Ident default -1," + "    Deleted Boolean,"
						+ "    Description Description not null," + "    Token Token default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(false, true, false)),
				this.killCR("create table TestTabelle (" + "    Id bigint not null primary key default -1,"
						+ "    Referenz bigint default -1 references DomainScheme (DomainScheme)," + "    Deleted int,"
						+ "    Description varchar(100) not null," + "    Token varchar(20) default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(false, false, true)),
				this.killCR("create table TestTabelle (" + "    Id bigint not null primary key default -1,"
						+ "    Referenz bigint default -1," + "    Deleted int,"
						+ "    Description varchar(100) not null," + "    Token varchar(20) default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(true, true, true)),
				this.killCR("create table TestTabelle (" + "    Id Ident not null primary key default -1,"
						+ "    Referenz Ident default -1 references DomainScheme (DomainScheme),"
						+ "    Deleted Boolean," + "    Description Description not null,"
						+ "    Token Token default '':o)'');"));
	}

	/** Test der Methode makeCreateStatement(boolean, boolean, boolean, String). */
	public void testMakeCreateStatementBooleanBooleanBooleanString() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		assertEquals(this.killCR(t.makeCreateStatement(false, false, false, null)),
				this.killCR("create table TestTabelle (" + "    Id bigint not null primary key default -1,"
						+ "    Referenz bigint default -1," + "    Deleted int,"
						+ "    Description varchar(100) not null," + "    Token varchar(20) default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(true, false, false, null)),
				this.killCR("create table TestTabelle (" + "    Id Ident not null primary key default -1,"
						+ "    Referenz Ident default -1," + "    Deleted Boolean,"
						+ "    Description Description not null," + "    Token Token default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(false, true, false, null)),
				this.killCR("create table TestTabelle (" + "    Id bigint not null primary key default -1,"
						+ "    Referenz bigint default -1 references DomainScheme (DomainScheme)," + "    Deleted int,"
						+ "    Description varchar(100) not null," + "    Token varchar(20) default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(false, false, true, null)),
				this.killCR("create table TestTabelle (" + "    Id bigint not null primary key default -1,"
						+ "    Referenz bigint default -1," + "    Deleted int,"
						+ "    Description varchar(100) not null," + "    Token varchar(20) default '':o)'');"));
		assertEquals(this.killCR(t.makeCreateStatement(true, true, true, null)),
				this.killCR("create table TestTabelle (" + "    Id Ident not null primary key default -1,"
						+ "    Referenz Ident default -1 references DomainScheme (DomainScheme),"
						+ "    Deleted Boolean," + "    Description Description not null,"
						+ "    Token Token default '':o)'');"));
	}

	/** Test der Methode makeCreateStatement(boolean, boolean, boolean, String) mit Quotes. */
	public void testMakeCreateStatementBooleanBooleanBooleanStringQuoted() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		assertEquals(this.killCR(t.makeCreateStatement(false, false, false, "'")),
				this.killCR("create table 'TestTabelle' (" + "    'Id' bigint not null primary key default -1,"
						+ "    'Referenz' bigint default -1," + "    'Deleted' int,"
						+ "    'Description' varchar(100) not null," + "    'Token' varchar(20) default '':o)'');"));
	}

	/** Test der Methode makeInsertStatementCounted(). */
	public void testMakeInsertStatement() {
		Tabelle t = (Tabelle) this.dm.getTabelle("TestTabelle");
		assertNotNull(t);
		assertEquals(t.makeInsertStatementCounted(), "insert into TestTabelle (Id, Referenz, "
				+ "Deleted, Description, Token) values ($1$, $2$, $3$, $4$, $5$)");
	}

}
