/*
 * FrameArchimedes.java
 *
 * 20.03.2004
 *
 * (c) by ollie
 *
 */

package archimedes.legacy.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.EventObject;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import archimedes.legacy.Archimedes;
import archimedes.legacy.acf.CodeGeneratorException;
import archimedes.legacy.acf.ReadyToGenerateChecker;
import archimedes.legacy.acf.checker.ModelChecker;
import archimedes.legacy.acf.checker.ModelCheckerMessage;
import archimedes.legacy.acf.checker.ModelCheckerMessage.Level;
import archimedes.legacy.acf.checker.ModelCheckerThread;
import archimedes.legacy.acf.checker.ModelCheckerThreadObserver;
import archimedes.legacy.acf.event.CodeFactoryEvent;
import archimedes.legacy.acf.event.CodeFactoryEventType;
import archimedes.legacy.acf.event.CodeFactoryListener;
import archimedes.legacy.acf.gui.GUIBundleBuilder;
import archimedes.legacy.acf.gui.checker.ModelCheckerMessageListDialog;
import archimedes.legacy.acf.gui.checker.ModelCheckerMessageListFrame;
import archimedes.legacy.acf.gui.checker.ModelCheckerMessageListFrameListener;
import archimedes.legacy.app.ApplicationUtil;
import archimedes.legacy.connections.DatabaseConnection;
import archimedes.legacy.gui.comparision.DataModelComparison;
import archimedes.legacy.gui.configuration.BaseConfiguration;
import archimedes.legacy.gui.configuration.BaseConfigurationFrame;
import archimedes.legacy.gui.configuration.BaseConfigurationFrameEvent;
import archimedes.legacy.gui.connections.ConnectFrame;
import archimedes.legacy.gui.connections.ConnectionsMainFrame;
import archimedes.legacy.gui.diagram.ComponentDiagramListener;
import archimedes.legacy.gui.diagram.ComponentDiagramm;
import archimedes.legacy.gui.diagram.DiagramGUIObjectCreator;
import archimedes.legacy.gui.diagram.GUIObjectTypes;
import archimedes.legacy.gui.indices.ComplexIndicesAdministrationFrame;
import archimedes.legacy.gui.table.TableModelFrame;
import archimedes.legacy.model.CodeFactory;
import archimedes.legacy.model.ColumnModel;
import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.DiagramSaveMode;
import archimedes.legacy.model.DiagrammModel;
import archimedes.legacy.model.DiagrammModelListener;
import archimedes.legacy.model.DomainModel;
import archimedes.legacy.model.MainViewModel;
import archimedes.legacy.model.OptionType;
import archimedes.legacy.model.PredeterminedOptionProvider;
import archimedes.legacy.model.SequenceModel;
import archimedes.legacy.model.SimpleIndexMetaData;
import archimedes.legacy.model.StereotypeModel;
import archimedes.legacy.model.TabellenModel;
import archimedes.legacy.model.TableModel;
import archimedes.legacy.model.UniqueMetaData;
import archimedes.legacy.model.UserInformation;
import archimedes.legacy.model.ViewModel;
import archimedes.legacy.model.events.DataModelListener;
import archimedes.legacy.model.events.TableChangedEvent;
import archimedes.legacy.model.gui.GUIDiagramModelListener;
import archimedes.legacy.model.gui.GUIObjectModel;
import archimedes.legacy.model.gui.GUIViewModel;
import archimedes.legacy.scheme.ArchimedesObjectFactory;
import archimedes.legacy.scheme.DefaultIndexListCleaner;
import archimedes.legacy.scheme.DefaultUserInformation;
import archimedes.legacy.scheme.Diagramm;
import archimedes.legacy.scheme.Domain;
import archimedes.legacy.scheme.ReferenceImportRecord;
import archimedes.legacy.scheme.Sequence;
import archimedes.legacy.scheme.Stereotype;
import archimedes.legacy.scheme.Tabelle;
import archimedes.legacy.scheme.View;
import archimedes.legacy.scheme.xml.DiagramXMLBuilder;
import archimedes.legacy.scheme.xml.ModelXMLReader;
import archimedes.legacy.sql.generator.ScriptGenerator;
import archimedes.legacy.transfer.DefaultCopyAndPasteController;
import archimedes.legacy.util.VersionIncrementer;
import archimedes.legacy.util.VersionStringBuilder;
import baccara.gui.GUIBundle;
import baccara.gui.generics.EditorFrameEvent;
import baccara.gui.generics.EditorFrameEventType;
import baccara.gui.generics.EditorFrameListener;
import corent.base.Constants;
import corent.base.Direction;
import corent.base.Semaphore;
import corent.base.SortedVector;
import corent.base.StrUtil;
import corent.db.ConnectionManager;
import corent.db.DBExec;
import corent.db.JDBCDataSourceRecord;
import corent.djinn.FrameEditorDjinn;
import corent.djinn.FrameSelectionDjinn;
import corent.djinn.FrameSelectionEditorDjinn;
import corent.djinn.SelectionDjinnAdapter;
import corent.event.GlobalEventManager;
import corent.event.GlobalListener;
import corent.files.ExtensionFileFilter;
import corent.files.Inifile;
import corent.files.StructuredTextFile;
import corent.gui.COUtil;
import corent.gui.DefaultFrameTextViewerComponentFactory;
import corent.gui.ExtendedColor;
import corent.gui.FrameTextViewer;
import corent.gui.JDialogThrowable;
import corent.gui.JFrameWithInifile;
import corent.gui.PropertyRessourceManager;
import corent.util.MemoryMonitor;
import de.ollie.archimedes.alexandrian.gui.codegenerator.CodeGeneratorStarter;
import gengen.generator.AbstractCodeGenerator;
import gengen.generator.CodeGenerator;

/**
 * Diese Klasse bietet das Hauptfenster der Archimedes-Applikation.
 * 
 * @author ollie
 * 
 * @changed OLI 30.09.2007 - Entfernung des Aufrufs einer aufgehobenen Methode auf dem Konstruktor.
 * @changed OLI 27.11.2007 - Erweiterung um die Methode <TT>raiseAltered()</TT>.
 * @changed OLI 01.01.2008 - Anpassungen an die Erweiterung der Signatur der paint-Methode der Klasse ComponentDiagramm.
 * @changed OLI 09.01.2008 - Korrektur der Index-Datengewinnung in der Methode
 *          <TT>getIndexMetadata(JDBCDataSourceRecord)</TT> (private).
 * @changed OLI 16.08.2008 - Erweiterung der Schreibroutine in der Form, das eine Datei f&uuml;r die Pflege des
 *          Datenmodells und eine kleinere zur Nutzung in der Applikationsschicht geschrieben werden.
 * @changed OLI 22.09.2008 - Austausch der Tilde im CodeGeneratoraufruf durch den Inhalt der System-Property
 *          "user.home". Ich bilde mir ein, hierdurch eine bessere Konfigurierbarkeit zu erreichen.
 * @changed OLI 02.10.2009 - Verbesserung des Fehlerhandlings beim Bau der Updatescripte und beim Import von
 *          Datenmodellen.
 */

public class FrameArchimedes extends JFrameWithInifile implements ActionListener,
		ComponentDiagramListener<GUIObjectTypes>, DataModelListener, DiagramComponentPanelListener,
		DiagrammModelListener, EditorFrameListener<EditorFrameEvent<?, ? extends Window>>, GlobalListener,
		GUIDiagramModelListener, ModelCheckerMessageListFrameListener, ModelCheckerThreadObserver, CodeFactoryListener {

	private static final Logger LOG = Logger.getLogger(FrameArchimedes.class);
	private static final String RES_TABLE_FRAME_EDIT_TITLE = "TableFrame.edit.title";
	private static final String RES_WARNING_MESSAGES_ERRORS_DETECTED = "warning.messages.errors.detected.label";
	private static final String RES_WARNING_MESSAGES_NO_WARNINGS = "warning.messages.no.warning.label";
	private static final String RES_WARNING_MESSAGES_WARNINGS_DETECTED = "warning.messages.warnings.detected.label";

	private boolean serverMode = false;
	/* Der ActionListener f&uuml;r das Laden durch DateiCaches. */
	private ActionListener actionListenerDateiCache = null;
	/* Referenz auf die das Diagramm anzeigende Komponente. */
	// private ComponentDiagramm component = null;
	private DiagramComponentPanel<GUIObjectTypes> component = null;
	/*
	 * Referenz auf das Diagramm, das mit der Applikation bearbeitet werden soll.
	 */
	private DiagrammModel diagramm = null;
	/* Referenz auf ein eventuell ge&ouml;ffnetes &Uuml;bersichtsfenster. */
	private FrameOverview fov = null;
	private GUIBundle guiBundle = null;
	/*
	 * Der Men&uuml;punkt zum Zu-/Abschalten der Referenzanzeige im aktuellen View.
	 */
	private JCheckBoxMenuItem menuitemswitchrelationtoview = null;
	/*
	 * Der Men&uuml;punkt zum Zu-/Abschalten der technischen Felder im aktuellen View.
	 */
	private JCheckBoxMenuItem menuitemswitchtechnicalfieldstoview = null;
	/*
	 * Der Men&uuml;punkt zum Zu-/Abschalten der transienten Felder im aktuellen View.
	 */
	private JCheckBoxMenuItem menuitemswitchtransientfieldstoview = null;
	/*
	 * Dieser Label zeigt die aktuelle Position der Maus &uuml;ber der Diagramm-Komponente an.
	 */
	// private JLabel labelPosition = new JLabel("(0,0)");
	/* Referenz auf das Datei-Men&uuml; zwecks Dateinamencache-Erweiterung. */
	private JMenu dateimenu = null;
	/* Referenz auf das View-Men&uuml; zwecks View-Erweiterung. */
	private JMenu viewmenu = null;
	/* Cache der Namen der letzten gespeicherten Dateien. */
	private JMenuItem[] dateinamencache = new JMenuItem[20];
	/*
	 * MenuItem, das die Aktion zum Einf&uuml;gen einer Tabelle in das Datenmodell ausl&ouml;st.
	 */
	private JMenuItem menuitemneutabelle = null;
	/*
	 * MenuItem, das die Aktion zum Einf&uuml;gen einer Standardtabelle in das Datenmodell ausl&ouml;st.
	 */
	private JMenuItem menuitemneustandardtabelle = null;
	/*
	 * MenuItem, das die Aktion zum Einf&uuml;gen einer Tabelle in eine View ausl&ouml;st.
	 */
	private JMenuItem menuitemtabletoview = null;
	/* Semaphore zur Absicherung der Speicherns und des Beendens. */
	private Semaphore semSpeichern = new Semaphore();
	/* Der Dateiname des aktuell bearbeiteten Diagramms. */
	private String dateiname = "unbenannt.ads";
	/* Der Pfad, in dem die Diagramme gespeichert werden sollen. */
	private String datenpfad = ".";
	/*
	 * Die Benutzerinformationen des Benutzer der Installation, z. B. fuer Generatoren.
	 */
	private UserInformation userInformation = null;
	private DiagramGUIObjectCreator guiObjectCreator = null;
	// private UserPing userPing = null;
	private ModelCheckerMessage[] lastModelCheckerMessages = null;

	private JMenuItem menuItemFileSave = null;
	private JMenu menuGenerate = null;
	private ModelCheckerMessageListFrame modelCheckerMessageListFrame = null;

	/**
	 * Erzeugt einen neuen FrameArchimedes anhand der &uuml;bergebenen Parameter.
	 * 
	 * @param guiBundle  A bundle with GUI information.
	 * @param serverMode Set this flag if the application is started in server mode.
	 * 
	 * @changed OLI 30.09.2007 - Ersetzen des Aufrufs der aufgehobenen Methode <TT>setRasterwidth(int)</TT> des
	 *          <TT>component</TT>-Objekts gegen den der Methode <TT>setRasterWidth(int)</TT>.
	 */
	public FrameArchimedes(GUIBundle guiBundle, boolean serverMode) {
		super("", guiBundle.getInifile());
		this.serverMode = serverMode;
		GlobalEventManager.AddGlobalListener(this);
		this.guiBundle = guiBundle;
		int i = 0;
		JMenuItem menuItem = null;
		if (ini.readBool("MemoryMonitor", "Show", false)) {
			MemoryMonitor.showMemoryMonitor();
		}
		this.updateHeadline();
		this.diagramm = Archimedes.Factory.createDiagramm();
		this.diagramm.addDataModelListener(this);
		this.diagramm.addDiagrammModelListener(this);
		this.diagramm.addGUIDiagramModelListener(this);
		this.diagramm.getViews().add((GUIViewModel) Archimedes.Factory.createMainView("Main",
				"Diese Sicht beinhaltet alle Tabellen des Schemas", true));
		this.guiObjectCreator = new DiagramGUIObjectCreator(this.diagramm);
		this.component = new DiagramComponentPanel<GUIObjectTypes>((GUIViewModel) this.diagramm.getViews().get(0),
				ComponentDiagramm.DOTSPERPAGEX * ComponentDiagramm.PAGESPERROW,
				ComponentDiagramm.DOTSPERPAGEY * ComponentDiagramm.PAGESPERCOLUMN, this.diagramm, this.guiObjectCreator,
				this, new ArchimedesStatusBarRenderer(), new DefaultCopyAndPasteController());
		this.component.updateWarnings(this.guiBundle.getResourceText(RES_WARNING_MESSAGES_NO_WARNINGS));
		this.component.addDiagramComponentPanelListener(this);
		this.datenpfad = this.getInifile().readStr("Datei", "Default", this.datenpfad);
		this.component.setRasterWidth(this.getInifile().readInt("Diagram", "Raster", 50));
		this.component.setSmartPosRadius(this.getInifile().readInt("Diagram", "SmartPosRadius", 100));
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				doBeenden();
			}

			@Override
			public void windowClosed(WindowEvent e) {
				doBeenden();
			}
		});
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = this.createMenu("menu.file", "fileopen");
		this.dateimenu = menu;
		menu.add(this.createMenuItem("menu.file.item.open", "fileopen", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDateiOeffnen();
			}
		}));
		this.menuItemFileSave = this.createMenuItem("menu.file.item.save", "filesave", this);
		menu.add(this.menuItemFileSave);
		menu.add(this.createMenuItem("menu.file.item.save.as", "save_as", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDateiSpeichernUnter();
			}
		}));
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.file.item.print", "fileprint", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDateiDrucken();
			}
		}));
		menu.add(this.createMenuItem("menu.file.item.export.graphic", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDateiGrafikexport();
			}
		}));
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.file.item.quit", "exit", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBeenden();
			}
		}));
		menu.add(new JSeparator());
		int j = 0;
		int anzahl = ini.readInt("Dateicache", "Anzahl", 0);
		this.actionListenerDateiCache = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JMenuItem item = (JMenuItem) e.getSource();
				String s = item.getText();
				doDateiOeffnen(s);
			}
		};
		for (i = 0; i < anzahl; i++) {
			String s = ini.readStr("Dateicache", "Datei" + i, null);
			if (s != null) {
				j++;
				menuItem = new JMenuItem("" /* + j + " " */ + s);
				menuItem.addActionListener(this.actionListenerDateiCache);
				menu.add(menuItem);
				this.dateinamencache[j - 1] = menuItem;
			}
		}
		menuBar.add(menu);
		menu = this.createMenu("menu.new", "filenew");
		menu.add(this.createMenuItem("menu.new.item.table", "tablenew", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doNeuTabelle(false);
			}
		}));
		menu.add(this.createMenuItem("menu.new.item.table.standard", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doNeuTabelle(true);
			}
		}));
		menu.add(this.createMenuItem("menu.new.item.read.template", "tableimport", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doNeuTemplateEinlesen();
			}
		}));
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.new.item.create.domains", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doDomainsAnlegen();
			}
		}));
		menuBar.add(menu);
		menu = this.createMenu("menu.edit", "edit");
		menu.add(this.createMenuItem("menu.edit.item.domains", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenDomains();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.sequences", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doEditSequences();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.complex.indices", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenKomplexeIndices();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.stereotypes", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenStereotype();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.views", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenViews();
			}
		}));
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.edit.item.diagramm.parameters", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenDiagrammparameter();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.database.connections", "database", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenDatenbankverbindungen();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.base.configuration", "configure", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenBenutzerParameter();
			}
		}));
		menu.add(this.createMenuItem("menu.edit.item.new.version", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				VersionIncrementer.State rc = new VersionIncrementer().increment(diagramm);
				if (rc == VersionIncrementer.State.SCRIPTS_CLEANED_ONLY) {
					Object version = JOptionPane.showInputDialog(null,
							StrUtil.ToHTML("Version konnte nicht erh&ouml;ht werden!"),
							StrUtil.ToHTML("Problem: Versionserh&ouml;hung"), JOptionPane.QUESTION_MESSAGE, null, null,
							diagramm.getVersion());
					if (version != null) {
						diagramm.setVersion(String.valueOf(version));
					}
				}
				raiseAltered();
				component.doRepaint();
			}
		}));
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.edit.item.create.update.script", "generate", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenUpdateScriptErstellenNeu();
			}
		}));
		menu.addSeparator();
		menu.add(this.createMenuItem("menu.edit.item.compare.models", "compare", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doBearbeitenDatenmodellVergleichen();
			}
		}));
		menuBar.add(menu);
		menu = this.createMenu("menu.zoom", "zoom");
		for (i = 1; i < 11; i++) {
			menuItem = new JMenuItem(
					this.guiBundle.getResourceText("menu.zoom.item.zoom.title").replace("{0}", String.valueOf(i * 10)));
			menuItem.setMnemonic(new Integer(i).toString().charAt(0));
			if (i == 10) {
				menuItem.setMnemonic('0');
			}
			class ZoomActionListener implements ActionListener {
				private double faktor = 1.0;

				public ZoomActionListener(double faktor) {
					super();
					this.faktor = faktor;
				}

				@Override
				public void actionPerformed(ActionEvent e) {
					doZoom(this.faktor);
				}
			}
			;
			menuItem.addActionListener(new ZoomActionListener(0.1d * (double) i));
			menu.add(menuItem);
		}
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.zoom.item.overview", "overview", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doOpenOverview();
			}
		}));
		menu.add(new JSeparator());
		menu.add(this.createMenuItem("menu.zoom.item.find.table", "find", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doTabelleFinden();
			}
		}));
		menuBar.add(menu);
		this.menuGenerate = this.createMenu("menu.generate", "generate");
		this.menuGenerate.add(this.createMenuItem("menu.generate.item.generate", "generate", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doGenerateCode();
			}
		}));
		this.menuGenerate.add(this.createMenuItem("menu.generate.item.generator.options", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doGeneratorOptionen();
			}
		}));
		menuBar.add(this.menuGenerate);
		menu = this.createMenu("menu.exports", "exports");
		menu.add(this.createMenuItem("menu.exports.item.domains", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doExportDomainsIni();
			}
		}));
		menuBar.add(menu);
		this.viewmenu = this.createMenu("menu.views", "views");
		this.menuitemtabletoview = this.createMenuItem("menu.views.item.add.table", "tableinsert",
				new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						doViewsEinfuegen();
					}
				});
		this.menuitemswitchrelationtoview = new JCheckBoxMenuItem(
				this.guiBundle.getResourceText("menu.views.item.show.relations.title"),
				((ViewModel) this.component.getView()).isShowReferencedColumns());
		this.menuitemswitchrelationtoview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((ViewModel) component.getView())
						.setShowReferencedColumns(((JCheckBoxMenuItem) e.getSource()).getState());
				raiseAltered();
				doRepaint();
			}
		});
		this.menuitemswitchtechnicalfieldstoview = new JCheckBoxMenuItem(
				this.guiBundle.getResourceText("menu.views.item.hide.technical.fields.title"),
				((ViewModel) this.component.getView()).isHideTechnicalFields());
		this.menuitemswitchtechnicalfieldstoview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((ViewModel) component.getView())
						.setHideTechnicalFields(((JCheckBoxMenuItem) e.getSource()).getState());
				raiseAltered();
				doRepaint();
			}
		});
		this.menuitemswitchtransientfieldstoview = new JCheckBoxMenuItem(
				this.guiBundle.getResourceText("menu.views.item.hide.transient.fields.title"),
				((ViewModel) this.component.getView()).isHideTransientFields());
		this.menuitemswitchtransientfieldstoview.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				((ViewModel) component.getView())
						.setHideTransientFields(((JCheckBoxMenuItem) e.getSource()).getState());
				raiseAltered();
				doRepaint();
			}
		});
		this.updateViewMenu(viewmenu, this.diagramm.getViews());
		menuBar.add(viewmenu);
		menu = this.createMenu("menu.tools", "tools");
		menu.add(this.createMenuItem("menu.tools.item.remove.reference.angles", null, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				component.removeAllAnglesFromRelations();
			}
		}));
		menu = this.createMenu("menu.info", "clipboard");
		menu.add(this.createMenuItem("menu.info.item.versionsToClipboard", "copy", new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				doInfoVersionsToClipboard();
			}
		}));
		menuBar.add(menu);
		this.setJMenuBar(menuBar);
		JPanel panelStatus = new JPanel(new GridLayout(1, 5, Constants.HGAP, Constants.VGAP));
		panelStatus.setBorder(new EmptyBorder(Constants.HGAP, Constants.VGAP, Constants.HGAP, Constants.VGAP));
		// TODO panelStatus.add(this.labelPosition);
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(this.component);
		this.setContentPane(panel);
		this.setVisible(true);
		this.createUserInformations(ini);
	}

	private JMenu createMenu(String resourceId, String imageId) {
		JMenu m = new JMenu(this.guiBundle.getResourceText(resourceId + ".title"));
		String mnemonic = this.guiBundle.getResourceText(resourceId + ".mnemonic");
		if ((mnemonic != null) && !mnemonic.isEmpty()) {
			m.setMnemonic(mnemonic.charAt(0));
		}
		if (imageId != null) {
			m.setIcon(this.guiBundle.getImageProvider().getImageIcon(imageId));
		}
		return m;
	}

	private JMenuItem createMenuItem(String resourceId, String imageId, ActionListener al) {
		JMenuItem m = new JMenuItem(this.guiBundle.getResourceText(resourceId + ".title"));
		String mnemonic = this.guiBundle.getResourceText(resourceId + ".mnemonic");
		if ((mnemonic != null) && !mnemonic.isEmpty()) {
			m.setMnemonic(mnemonic.charAt(0));
		}
		if (imageId != null) {
			m.setIcon(this.guiBundle.getImageProvider().getImageIcon(imageId));
		}
		if (al != null) {
			m.addActionListener(al);
		}
		return m;
	}

	private void createUserInformations(Inifile ini) {
		this.userInformation = new DefaultUserInformation(System.getProperty("archimedes.user.name", "UNKNOWN"),
				System.getProperty("archimedes.user.token", "N/A"),
				System.getProperty("archimedes.user.company", "UNKNOWN"));
	}

	/**
	 * Diese Methode wird ausgef&uuml;hrt, wenn der Benutzer den Men&uuml;punkt &Ouml;ffnen w&auml;hlt.
	 */
	public void doDateiOeffnen() {
		if (this.diagramm.isAltered()) {
			int option = JOptionPane.showConfirmDialog(null,
					StrUtil.FromHTML("Das Diagramm " + "ist ge&auml;ndert worden!\nM&ouml;chten Sie es speichern?"),
					"Diagramm nicht" + " gespeichert", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				if ((this.dateiname.length() == 0) || (this.dateiname.equalsIgnoreCase("unbenannt.ads"))) {
					this.doDateiSpeichernUnter();
				} else {
					System.out.println("writing " + this.dateiname + " ...");
					this.doDateiSpeichern(this.dateiname, true);
				}
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		String dn = "";
		JFileChooser fc = new JFileChooser(datenpfad);
		fc.setAcceptAllFileFilterUsed(false);
		ExtensionFileFilter eff = new ExtensionFileFilter(new String[] { "ads", "xml" });
		fc.setFileFilter(eff);
		fc.setCurrentDirectory(new File(this.datenpfad));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			dn = fc.getSelectedFile().getAbsolutePath();
			this.doDateiOeffnen(dn, false);
		}
	}

	/**
	 * Diese Methode wird ausgef&uuml;hrt, wenn der Benutzer den Men&uuml;punkt &Ouml;ffnen w&auml;hlt.
	 * 
	 * @param dn Der Name der einzulesenden Datei.
	 */
	public void doDateiOeffnen(String dn) {
		this.doDateiOeffnen(dn, true);
	}

	/**
	 * This method is called if the user clicks the menu item "file / open".
	 * 
	 * @param fileName The name of the file to read.
	 * @param ask      If this flag is set in case of an altered data model, it will be asked for saving the current
	 *                 model.
	 * 
	 * @changed OLI 18.03.2016 - Comment to English. Introduced XML files.
	 */
	public void doDateiOeffnen(String fileName, boolean ask) {
		int i = 0;
		int leni = 0;
		if (ask && this.diagramm.isAltered()) {
			boolean errors = this.isErrorsFound(this.diagramm, false);
			int option = JOptionPane.showConfirmDialog(null,
					this.guiBundle.getResourceText((errors ? "archimedes.open.file.altered.warning.with.errors.text"
							: "archimedes.open.file.altered.warning.text")),
					this.guiBundle.getResourceText("archimedes.open.file.altered.warning.title"),
					JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				if ((this.dateiname.length() == 0) || (this.dateiname.equalsIgnoreCase("unbenannt.ads"))) {
					this.doDateiSpeichernUnter();
				} else {
					System.out.println("writing " + this.dateiname + " ...");
					this.doDateiSpeichern(this.dateiname, true);
				}
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		try {
			this.dateiname = fileName;
			if (fileName.toLowerCase().endsWith(".ads")) {
				StructuredTextFile stf = new StructuredTextFile(fileName);
				stf.setHTMLCoding(true);
				stf.load();
				this.diagramm = this.diagramm.createDiagramm(stf);
			} else if (fileName.toLowerCase().endsWith(".xml")) {
				ModelXMLReader reader = new ModelXMLReader(new ArchimedesObjectFactory());
				this.diagramm = (Diagramm) reader.read(fileName);
			}
			this.diagramm.addDataModelListener(this);
			this.diagramm.addDiagrammModelListener(this);
			this.diagramm.addGUIDiagramModelListener(this);
			this.guiObjectCreator.setDiagram(this.diagramm);
			this.component.setView((GUIViewModel) this.diagramm.getViews().get(0));
			this.component.setDiagram(this.diagramm);
			if (this.fov != null) {
				this.fov.setDiagramm(this.diagramm);
			}
			this.updateViewMenu(this.viewmenu, this.diagramm.getViews());
			this.diagramm.clearAltered();
			// this.updateUserPingModelName();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		this.updateHeadline();
		this.doDateinamenCacheAktualisieren(fileName);
		for (TableModel tm : this.diagramm.getTables()) {
			System.out.print("# ");
			for (int ii = 0, lenii = ((Tabelle) tm).getTabellenspaltenCount(); ii < lenii; ii++)
				System.out.print(((Tabelle) tm).getTabellenspalteAt(ii) + ", ");
			System.out.println();
		}
		this.startChecker();
	}

	private void startChecker() {
		String path = this.diagramm.getCodePfad().replace("~", System.getProperty("user.home"));
		Object cf = this.getCodeFactory(path);
		new ModelCheckerThread(this, this.diagramm, (cf instanceof CodeFactory ? (CodeFactory) cf : null));
	}

	/**
	 * Diese Methode wird ausgef&uuml;hrt, wenn der Benutzer den Men&uuml;punkt Speichern unter... w&auml;hlt.
	 */
	public void doDateiSpeichernUnter() {
		String dn = "";
		JFileChooser fc = new JFileChooser(datenpfad);
		fc.setAcceptAllFileFilterUsed(false);
		ExtensionFileFilter eff = new ExtensionFileFilter(new String[] { "ads", "xml" });
		fc.setFileFilter(eff);
		fc.setCurrentDirectory(new File(this.datenpfad));
		fc.setSelectedFile(new File(this.dateiname));
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			dn = fc.getSelectedFile().getAbsolutePath();
			this.doDateiSpeichern(dn, false);
		}
	}

	/**
	 * Stores the data model in the file system.
	 * 
	 * @param fileName     The name of the file which the data model is to store into.
	 * @param ignoreErrors Set this flag if a model with errors is to be stored also.
	 * 
	 * @changed OLI 16.08.2008 - There will be two different versions of the model stored: one is for editing the data
	 *          model, the other for using as application layer in Archimedes applications (this works for ADS files
	 *          only).
	 * @changed OLI 11.07.2016 - Refactored.
	 */
	public void doDateiSpeichern(String name, boolean ignoreErrors) {
		if (!ignoreErrors && this.isErrorsFound(this.diagramm, true)) {
			return;
		}
		try {
			this.semSpeichern.P();
			try {
				if (name.endsWith("ads")) {
					int option = JOptionPane.showConfirmDialog(null,
							this.guiBundle.getResourceText("archimedes.save.file.old.format.warning.text"),
							this.guiBundle.getResourceText("archimedes.save.file.old.format.warning.title"),
							JOptionPane.YES_NO_OPTION);
					if (option == JOptionPane.YES_OPTION) {
						StructuredTextFile stf = this.diagramm.toSTF(DiagramSaveMode.REGULAR);
						stf.setFilename(name);
						stf.save();
						stf = this.diagramm.toSTF(DiagramSaveMode.APPLICATION);
						stf.setFilename(name.concat("app"));
						stf.save(false);
						this.dateiname = name;
						this.diagramm.clearAltered();
					}
				} else {
					FileWriter fw = new FileWriter(name, false);
					BufferedWriter writer = new BufferedWriter(fw);
					writer.write(new DiagramXMLBuilder().buildXMLContentForDiagram(this.diagramm,
							Archimedes.PALETTE.getColors().toArray(new ExtendedColor[0])));
					writer.close();
					fw.close();
					this.dateiname = name;
					this.diagramm.clearAltered();
					LOG.info("Written data model to file: " + name);
				}
			} catch (Exception e) {
				e.printStackTrace();
				LOG.error("Error while writing date model to file: " + name);
				JOptionPane.showMessageDialog(null, this.guiBundle.getResourceText("archimedes.save.file.error.text"),
						this.guiBundle.getResourceText("archimedes.save.file.error.title"), JOptionPane.YES_OPTION);
			}
			this.updateHeadline();
			this.doDateinamenCacheAktualisieren(name);
		} catch (java.lang.InterruptedException ie) {
			ie.printStackTrace();
		}
		this.semSpeichern.V();
	}

	private boolean isErrorsFound(DataModel dm, boolean showErrors) {
		String path = this.diagramm.getCodePfad().replace("~", System.getProperty("user.home"));
		Object cfo = this.getCodeFactory(path);
		if (cfo instanceof CodeFactory) {
			CodeFactory cf = (CodeFactory) cfo;
			cf.setDataModel(this.diagramm);
			cf.setGUIBundle(GUIBundleBuilder.getGUIBundle(this.guiBundle, cf.getResourceBundleNames()));
			cf.setModelCheckerMessageListFrameListeners(this);
			List<ModelCheckerMessage> messages = new LinkedList<ModelCheckerMessage>();
			for (ModelChecker mc : cf.getModelCheckers()) {
				ModelCheckerMessage[] mcms = mc.check(dm);
				if (mcms.length > 0) {
					for (ModelCheckerMessage mcm : mcms) {
						if (mcm.getLevel() == Level.ERROR) {
							messages.add(mcm);
						}
					}
				}
			}
			if (messages.size() > 0) {
				if (showErrors) {
					new ModelCheckerMessageListDialog(this, this.guiBundle,
							messages.toArray(new ModelCheckerMessage[0]), false);
				}
				return true;
			}
		}
		return false;
	}

	private void doDateinamenCacheAktualisieren(String name) {
		int index = -1;
		int len = 0;
		for (len = 0; len < this.dateinamencache.length; len++) {
			if (this.dateinamencache[len] == null) {
				break;
			}
		}
		for (int i = 0; i < len; i++) {
			String s = this.dateinamencache[i].getText();
			// s = s.substring(2, s.length());
			if (s.equalsIgnoreCase(name)) {
				index = i;
				break;
			}
		}
		if (index > -1) {
			for (int i = index; i > 0; i--) {
				String s = this.dateinamencache[i - 1].getText();
				this.dateinamencache[i].setText(/* "" + (i+1) + " " + */s);
			}
			this.dateinamencache[0].setText(/* "1 " + */name);
			return;
		}
		if (len < this.dateinamencache.length) {
			this.dateinamencache[len] = new JMenuItem("");
			this.dateinamencache[len].addActionListener(this.actionListenerDateiCache);
			this.dateimenu.add(this.dateinamencache[len]);
		} else {
			len--;
		}
		if (len > 0) {
			for (int i = len; i > 0; i--) {
				String s = this.dateinamencache[i - 1].getText();
				this.dateinamencache[i].setText(/* "" + (i+1) + " " + */s);
			}
		}
		this.dateinamencache[0].setText(/* "" + 1 + " " + */name);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Datei / Drucken bet&auml;tigt hat.
	 */
	public void doDateiDrucken() {
		new DiagramPrinter().print(this, this.getInifile(), this.component,
				this.getInifile().readBool("Print", "PDF", false),
				this.getInifile().readStr("Print", "Printername", "normal_gray"),
				this.getInifile().readStr("Print", "Printfilename", "archimedes"));
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Datei / Grafikexport aufruft.
	 * 
	 * @changed OLI 01.01.2008 - Anpassung an die neue Signatur des paint-Aufrufes der ComponentDiagramm zur
	 *          Kennzeichnung von Ausdrucken oder Grafikexporten.<BR>
	 * 
	 */
	public void doDateiGrafikexport() {
		new GraphicExporter().export(this, this.dateiname, this.datenpfad, this.component);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn die Applikation beendet worden ist.
	 * 
	 * @changed OLI 13.05.2008 - Korrektur Umlaut (ge&auml;ndert ...).
	 * 
	 */
	public void doBeenden() {
		try {
			this.semSpeichern.P();
			boolean errorsFound = this.isErrorsFound(this.diagramm, false);
			if (this.diagramm.isAltered()) {
				int answer = JOptionPane.showConfirmDialog(null,
						StrUtil.FromHTML("Das Diagramm ist ge&auml;ndert worden!\n"
								+ (errorsFound ? "DAS MODELL ENTH&Auml;LT FEHLER!\n" : "") + "M&ouml;chten Sie es "
								+ (errorsFound ? "trotzdem " : "") + "speichern?"),
						"Diagramm nicht gespeichert", JOptionPane.YES_NO_CANCEL_OPTION);
				if (answer == JOptionPane.YES_OPTION) {
					this.semSpeichern.V();
					if ((this.dateiname.length() == 0) || (this.dateiname.equalsIgnoreCase("unbenannt.ads"))) {
						this.doDateiSpeichernUnter();
					} else {
						this.doDateiSpeichern(this.dateiname, true);
					}
				} else if (answer == JOptionPane.CANCEL_OPTION) {
					this.semSpeichern.V();
					return;
				}
			}
			try {
				int len = 0;
				for (len = 0; len < this.dateinamencache.length; len++) {
					if (this.dateinamencache[len] == null) {
						break;
					}
					String s = this.dateinamencache[len].getText();
					// s = s.substring(2, s.length());
					this.ini.writeStr("Dateicache", "Datei" + len, s);
				}
				this.ini.writeInt("Dateicache", "Anzahl", len);
			} catch (IOException ioe) {
				ioe.printStackTrace();
				System.out.println("\n\nDateinamencache konnte nicht gespeichert werden!\n\n");
			}
			try {
				this.ini.save();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n\nInidatei konnte nicht gespeichert werden!\n\n");
			}
			this.setVisible(false);
			this.dispose();
			System.exit(0);
		} catch (java.lang.InterruptedException ie) {
			ie.printStackTrace();
		}
		this.semSpeichern.V();
	}

	private VersionStringBuilder versionStringBuilder = new VersionStringBuilder();

	/**
	 * This method will be called if the menu item "info / versions to clip board" is selected.
	 * 
	 * @changed OLI 18.04.2017 - Added.
	 */
	public void doInfoVersionsToClipboard() {
		Object cf = this.getCodeFactory("");
		String versions = this.versionStringBuilder.getVersions((cf instanceof CodeFactory ? (CodeFactory) cf : null));
		Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
		cb.setContents(new StringSelection(versions), null);
		LOG.info("sent versions to clipboard");
		JOptionPane.showMessageDialog(this, this.guiBundle.getResourceText("versions.copiedToClipboard.message"),
				this.guiBundle.getResourceText("versions.copiedToClipboard.title"), JOptionPane.INFORMATION_MESSAGE);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Neu / Tabelle bet&auml;tigt.
	 * 
	 * @param filled Wird diese Flagge gesetzt, so wird eine Tabelle mit den &uuml;blichen Standard-Einstellungen und
	 *               -Spalten generiert.
	 */
	public void doNeuTabelle(boolean filled) {
		this.component.setInsertMode(true);
		this.component.setStandardMode(filled);
		this.component.setGUIObjectToCreateIdentifier(GUIObjectTypes.TABLE);
	}

	/**
	 * Diese Methode &auml;ndert den Namen der Standard-Template-Datei.
	 */
	public void doNeuTemplateEinlesen() {
		String fn = "";
		JFileChooser fc = new JFileChooser(datenpfad);
		fc.setAcceptAllFileFilterUsed(false);
		ExtensionFileFilter eff = new ExtensionFileFilter(new String[] { "properties" });
		fc.setFileFilter(eff);
		fc.setCurrentDirectory(new File(this.datenpfad));
		int returnVal = fc.showOpenDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			fn = fc.getSelectedFile().getAbsolutePath();
			Archimedes.Factory.setTemplateFilename(fn);
			this.menuitemneustandardtabelle.setText("Standard-Tabelle (" + fn + ")");
		}
	}

	/**
	 * Diese Methode &ouml;ffnet ein zus&auml;tzliches Fenster mit einer 10%-Darstellung des Datenmodells.
	 * 
	 * @changed OLI 16.12.2008 - Hinzugef&uuml;gt.
	 *          <P>
	 * 
	 */
	public void doOpenOverview() {
		if (this.fov == null) {
			this.fov = new FrameOverview(StrUtil.FromHTML("&Uuml;bersicht"), this.getInifile(), this.diagramm, this);
		} else {
			this.fov.toFront();
			this.fov.setVisible(true);
		}
	}

	/**
	 * Diese Methode erzeugt oder aktualisiert (falls bereits vorhanden) einen Satz von Domains, der immer wieder gern
	 * genutzt wird :o)
	 */
	public void doDomainsAnlegen() {
		String dn = "";
		try {
			JFileChooser fc = new JFileChooser(datenpfad);
			fc.setAcceptAllFileFilterUsed(false);
			ExtensionFileFilter eff = new ExtensionFileFilter(new String[] { "ini" });
			fc.setFileFilter(eff);
			fc.setCurrentDirectory(new File("Archimedes-Domain-Export.ini"));
			int returnVal = fc.showOpenDialog(this);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				dn = fc.getSelectedFile().getAbsolutePath();
				Inifile ini = new Inifile(dn);
				ini.load();
				for (int i = 0, len = ini.readInt("Domains", "Number", 0); i < len; i++) {
					String name = ini.readStr("Domains", "Name" + i, "NEUEDOMAIN");
					int type = ini.readInt("Domains", "Type" + i, Types.INTEGER);
					int length = ini.readInt("Domains", "Length" + i, 0);
					int nks = ini.readInt("Domains", "Precision" + i, 0);
					String desc = ini.readStr("Domains", "Description" + i, "-");
					String initialValue = ini.readStr("Domains", "InitialValue" + i, "NULL");
					String parameters = ini.readStr("Domains", "Parameter" + i, "");
					this.createDomain(name, type, length, nks, desc, initialValue, parameters);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\n\nFehler beim Einlesen der Standarddomain-Datei -> " + dn + "!");
		}
	}

	private void createDomain(String name, int dt, int len, int nks, String beschr, String initialValue,
			String parameters) {
		DomainModel dom = null;
		try {
			dom = this.diagramm.getDomain(name);
		} catch (NoSuchElementException nsee) {
		}
		if (dom != null) {
			dom.setName(name);
			dom.setDataType(dt);
			dom.setLength(len);
			dom.setDecimalPlace(nks);
			dom.setInitialValue(initialValue);
			dom.setParameters(parameters);
		} else {
			dom = Archimedes.Factory.createDomain(name, dt, len, nks);
			dom.setInitialValue(initialValue);
			dom.setParameters(parameters);
			this.diagramm.addDomain(dom);
		}
		dom.setComment(beschr);
	}

	/** Diese Methode exportiert die Domains des Modells in eine Ini-Datei. */
	public void doExportDomainsIni() {
		String dn = "";
		JFileChooser fc = new JFileChooser(datenpfad);
		fc.setAcceptAllFileFilterUsed(false);
		ExtensionFileFilter eff = new ExtensionFileFilter(new String[] { "ini" });
		fc.setFileFilter(eff);
		fc.setCurrentDirectory(new File(this.datenpfad));
		fc.setSelectedFile(new File("Archimedes-Domain-Export.ini"));
		int returnVal = fc.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			dn = fc.getSelectedFile().getAbsolutePath();
			try {
				Inifile ini = new Inifile(dn);
				Vector<?> vd = this.diagramm.getDomains();
				ini.writeInt("Domains", "Number", vd.size());
				for (int i = 0, len = vd.size(); i < len; i++) {
					DomainModel dom = (DomainModel) vd.elementAt(i);
					ini.writeStr("Domains", "Name" + i, dom.getName());
					ini.writeInt("Domains", "Type" + i, dom.getDataType());
					ini.writeInt("Domains", "Length" + i, dom.getLength());
					ini.writeInt("Domains", "Precision" + i, dom.getDecimalPlace());
					ini.writeStr("Domains", "Description" + i, dom.getComment());
					ini.writeStr("Domains", "Parameter" + i, dom.getParameters());
					ini.writeStr("Domains", "InitialValue" + i, dom.getInitialValue());
				}
				ini.save();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("\n\nFehler beim Schreiben der Domain-Export-Datei -> " + dn + "!");
			}
		}
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Bearbeiten / Domains bet&auml;tigt.
	 */
	public void doBearbeitenDomains() {
		final DiagrammModel dm = this.diagramm;
		FrameSelectionEditorDjinn fsd = new FrameSelectionEditorDjinn("Auswahl Domain", dm.getDomainsReference(),
				(Domain) dm.createDomain(), this.getInifile(), true) {
			@Override
			public boolean permitDelete(Object obj) {
				TableModel[] tables = dm.getTables();
				for (TableModel tm : tables) {
					for (ColumnModel cm : tm.getColumns()) {
						if (cm.getDomain() == obj) {
							JOptionPane.showMessageDialog(null,
									StrUtil.FromHTML("Die Domain " + "wird durch " + cm.getFullName() + " genutzt!\n"
											+ "L&ouml;schen nicht m&ouml;glich!"),
									"Referenzproblem!", JOptionPane.YES_OPTION);
							System.out.println("Domain wird benutzt durch " + cm.getFullName());
							return false;
						}
					}
				}
				return true;
			}
		};
		fsd.addSelectionDjinnListener(new SelectionDjinnAdapter() {
			@Override
			public void selectionUpdated() {
				diagramm.raiseAltered();
				component.repaint();
			};
		});
	}

	/**
	 * This method will be called if the user selects the edit / sequences menu item.
	 */
	public void doEditSequences() {
		final DiagrammModel dm = this.diagramm;
		FrameSelectionEditorDjinn fsd = new FrameSelectionEditorDjinn(
				this.guiBundle.getResourceText("sequences.maintenance.title"), dm.getSequencesByReference(),
				(Sequence) dm.createSequence(), this.getInifile(), true) {
			@Override
			public boolean permitDelete(Object obj) {
				for (TableModel tm : dm.getTables()) {
					for (ColumnModel cm : tm.getColumns()) {
						if (cm.getSequenceForKeyGeneration() == obj) {
							JOptionPane.showMessageDialog(null,
									guiBundle.getResourceText("sequences.maintenance.permit.delete.text")
											.replace("$NAME$", ((SequenceModel) obj).getName())
											.replace("$COLUMNNAME$", cm.getFullName()),
									guiBundle.getResourceText("sequences.maintenance.permit.delete.title"),
									JOptionPane.YES_OPTION);
							return false;
						}
					}
				}
				return true;
			}
		};
		fsd.addSelectionDjinnListener(new SelectionDjinnAdapter() {
			@Override
			public void selectionUpdated() {
				diagramm.raiseAltered();
			};
		});
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Bearbeiten / Komplexe Indices aufruft.
	 */
	public void doBearbeitenKomplexeIndices() {
		new ComplexIndicesAdministrationFrame("Complex index administration", this.getInifile(), this.diagramm,
				this.diagramm.getTabellen(), this.diagramm);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Bearbeiten / Stereotype bet&auml;tigt.
	 */
	public void doBearbeitenStereotype() {
		final DiagrammModel dm = this.diagramm;
		FrameSelectionEditorDjinn fsd = new FrameSelectionEditorDjinn("Auswahl Stereotypen",
				dm.getStereotypeReference(), (Stereotype) dm.createStereotype(), this.getInifile(), true) {
			@Override
			public boolean permitDelete(Object obj) {
				for (TableModel tm : dm.getTables()) {
					for (StereotypeModel stm : tm.getStereotypes()) {
						if (stm == obj) {
							JOptionPane.showMessageDialog(null,
									StrUtil.FromHTML("Die " + "Stereotype wird durch " + tm.getName() + " genutzt!\n"
											+ "L&ouml;schen nicht m&ouml;glich!"),
									"Referenzproblem!", JOptionPane.YES_OPTION);
							System.out.println("Stereotype wird benutzt durch " + tm.getName());
							return false;
						}
					}
				}
				return true;
			}
		};
		fsd.addSelectionDjinnListener(new SelectionDjinnAdapter() {
			@Override
			public void selectionUpdated() {
				diagramm.raiseAltered();
				component.repaint();
			};
		});
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Bearbeiten / Views bet&auml;tigt.
	 */
	public void doBearbeitenViews() {
		final DiagrammModel dm = this.diagramm;
		FrameSelectionEditorDjinn fsd = new FrameSelectionEditorDjinn("Auswahl Views", (Vector<?>) dm.getViews(),
				(View) Archimedes.Factory.createView(), this.getInifile(), true) {
			@Override
			public boolean permitDelete(Object obj) {
				return true;
			}
		};
		fsd.addSelectionDjinnListener(new SelectionDjinnAdapter() {
			@Override
			public void selectionUpdated() {
				diagramm.raiseAltered();
				component.repaint();
				updateViewMenu(viewmenu, diagramm.getViews());
			};
		});
	}

	/**
	 * @changed OLI 25.11.2014 - Added.
	 */
	public void doBearbeitenBenutzerParameter() {
		BaseConfiguration bc = new BaseConfiguration();
		bc.setCompany(System.getProperty("archimedes.user.company", ""));
		bc.setDBName(System.getProperty("archimedes.user.database.name", ""));
		bc.setDBServerName(System.getProperty("archimedes.user.database.server.name", ""));
		bc.setDBUserName(System.getProperty("archimedes.user.database.user.name", ""));
		bc.setLanguage(System.getProperty("archimedes.user.language", ""));
		bc.setUserName(System.getProperty("archimedes.user.name", ""));
		bc.setUserToken(System.getProperty("archimedes.user.token", ""));
		BaseConfigurationFrame bcf = new BaseConfigurationFrame(bc,
				this.guiBundle.getResourceText("BaseconfigurationFrame.title"), this.guiBundle);
		bcf.addEditorFrameListener(this);
		bcf.setVisible(true);
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Bearbeiten / Diagrammparameter bet&auml;tigt.
	 */
	public void doBearbeitenDiagrammparameter() {
		this.setPredeterminedOptionProviderForDiagram();
		new FrameEditorDjinn("Diagrammparameter", this.diagramm, false, this.getInifile()) {
			@Override
			public void doChanged(boolean saveOnly) {
				diagramm.raiseAltered();
				component.repaint();
				// updateUserPingModelName();
			}
		};
	}

	private void setPredeterminedOptionProviderForDiagram() {
		Object cf = this.getCodeFactory("");
		if (cf instanceof PredeterminedOptionProvider) {
			this.diagramm.setPredeterminedOptionProvider((PredeterminedOptionProvider) cf);
		} else {
			this.diagramm.setPredeterminedOptionProvider(null);
		}
	}

	/**
	 * Sets the passed user ping as new user ping for the frame.
	 * 
	 * @param userPing The new user ping for the frame.
	 * 
	 * @changed OLI 22.03.2016 - Added. / public void setUserPing(UserPing userPing) { this.userPing = userPing; }
	 * 
	 *          /** Starts the database connections maintenance.
	 * 
	 * @changed OLI 20.01.2015 - Added.
	 */
	public void doBearbeitenDatenbankverbindungen() {
		new ConnectionsMainFrame(this, this.guiBundle, this.ini, this.diagramm);
	}

	/**
	 * Starts a comparision with another data model.
	 * 
	 * @changed OLI 19.02.2016 - Added.
	 */
	public void doBearbeitenDatenmodellVergleichen() {
		new DataModelComparison(this.diagramm, this.getInifile());
	}

	/**
	 * Creates a SQL update script for a selected database connection (new way).
	 * 
	 * @changed OLI 16.12.2015 - Added.
	 */
	public void doBearbeitenUpdateScriptErstellenNeu() {
		DatabaseConnection dc = this.diagramm.getDatabaseConnection(this.getInifile().readStr("Database-Connections",
				"Update-Preselection-" + this.diagramm.getName(), null));
		DatabaseConnectionRecord dcr = new DatabaseConnectionRecord(dc, this.diagramm.getDatabaseConnections(), "");
		ConnectFrame cf = new ConnectFrame(dcr, this.guiBundle);
		cf.addEditorFrameListener(new EditorFrameListener<EditorFrameEvent<DatabaseConnectionRecord, ConnectFrame>>() {
			@Override
			public void eventFired(EditorFrameEvent<DatabaseConnectionRecord, ConnectFrame> event) {
				if (event.getEventType() == EditorFrameEventType.OK) {
					try {
						DatabaseConnectionRecord dcr = (DatabaseConnectionRecord) event.getEditedObject();
						if ((dcr != null) && (dcr.getDatabaseConnection() != null)) {
							getInifile().writeStr("Database-Connections", "Update-Preselection-" + diagramm.getName(),
									dcr.getDatabaseConnection().getName());
						}
						createSQLUpdateScript(dcr);
					} catch (Exception e) {
						new JDialogThrowable(e,
								guiBundle.getResourceText("archimedes.ConnectFrame.error.connection.failed.title"),
								getInifile(), new PropertyRessourceManager());
					}
				}
			}
		});
		cf.setVisible(true);
	}

	private void createSQLUpdateScript(DatabaseConnectionRecord dcr) throws Exception {
		String script = new ScriptGenerator().generate(this.diagramm, dcr, this.userInformation);
		if (script != null) {
			Vector<String> s = new Vector<String>();
			s.add(script);
			new FrameTextViewer(s, DefaultFrameTextViewerComponentFactory.INSTANCE, getInifile(),
					"SQL Update Script (new)", datenpfad);
		}
	}

	/** Frischt den Frame auf. */
	public void doRepaint() {
		this.repaint();
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Zoom / Zoom n% bet&auml;tigt.
	 * 
	 * @param factor Der neue Zoomfaktor.
	 */
	public void doZoom(double factor) {
		this.component.setZoomFactor(factor);
		this.component.doRepaint();
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Zoom / Tabelle finden anclickt.
	 * 
	 * @changed OLI 27.02.2009 - Die &Auml;nderungsflagge wird nach der Tabellenauswahl nicht mehr gesetzt (ist ja auch
	 *          nicht sinnvoll).
	 *          <P>
	 * 
	 */
	public void doTabelleFinden() {
		SortedVector<TableModel> sv = new SortedVector<TableModel>();
		for (TableModel tm : this.diagramm.getTables()) {
			sv.add(tm);
		}
		new FrameSelectionDjinn("Tabellenauswahl", sv, true, this.getInifile()) {
			@Override
			public void doSelected(Vector values) {
				if (values.size() > 0) {
					TabellenModel tm = (TabellenModel) values.elementAt(0);
					component.setDiagramViewToObject(tm.getName());
				}
				// OLI 27.02.2009 - Herausnahme wegen Sinnlosigkeit.
				// diagramm.raiseAltered();
			}
		};
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Benutzer den Men&uuml;punkt Genrieren / Codegeneratoroptionen
	 * (&Uuml;bersicht) anclickt.
	 */
	public void doGeneratorOptionen() {
		new DialogTableGeneratorOptions(this, this.getInifile(), this.diagramm);
	}

	/**
	 * This Method is called if the user clicks on the menu item "Generate" / "Code".
	 */
	public void doGenerateCode() {
		new CodeGeneratorStarter(this.diagramm.getCodeFactoryClassName(),
				this.diagramm.getCodePfad().replace("~", System.getProperty("user.home")), this.diagramm).start();
	}

	private Object getCodeFactory(String path) {
		CodeFactory cf = null;
		CodeGenerator cg = null;
		String cfcn = this.diagramm.getCodeFactoryClassName();
		try {
			if ((cfcn == null) || (cfcn.length() == 0)) {
				cfcn = this.ini.readStr("CodeGenerator", "Class", "CODEFACTORYCLASS");
			}
			if (cfcn.startsWith("gengen:")) {
				try {
					cfcn = cfcn.substring(7, cfcn.length());
					cg = (CodeGenerator) Class.forName(cfcn).newInstance();
					if (cg instanceof AbstractCodeGenerator) {
						((AbstractCodeGenerator) cg).setBasePath(path);
					}
					return cg;
				} catch (Exception e) {
					new JDialogThrowable(e,
							"Klasse " + cfcn + " kann nicht als Code-Generator " + "instanziert werden!",
							this.getInifile(), new PropertyRessourceManager());
				}
			}
			cf = Archimedes.Factory.createCodeFactory(cfcn);
			cf.setDataModel(this.diagramm);
			cf.setGUIBundle(GUIBundleBuilder.getGUIBundle(this.guiBundle, cf.getResourceBundleNames()));
			cf.setModelCheckerMessageListFrameListeners(this);
		} catch (Exception e) {
			LOG.error("Error while creating code generator/factory for class: " + cfcn);
		}
		return cf;
	}

	/**
	 * Generiert eine neue Kopfzeile f&uuml;r das Hauptfenster der Anwendung.
	 */
	public void updateHeadline() {
		this.setTitle("Archimedes (Version " + Archimedes.GetVersion() + ")  -  " + this.dateiname
				+ ((this.diagramm != null) && this.diagramm.isAltered() ? StrUtil.FromHTML(" (Ge&auml;ndert)") : ""));
	}

	/**
	 * Liefert eine Liste mit den Index-Metadaten aus der angegebenen Datenbank.
	 * 
	 * @param dsr        Die Daten zur Verbindung mit der Datenbank.
	 * @param schemaName Ein Schemaname, falls das Modell auf ein spezielles Schema der Datenbank abgestimmt werden
	 *                   soll. Soll das Defaultschema genutzt werden, kann der Name leer &uuml;bergeben werden.
	 * @return Eine Liste mit den Index-Metadaten aus der angegebenen Datenbank.
	 * 
	 * @changed OLI 01.06.2009 - Hinzugef&uuml;gt.
	 * 
	 * @todo OLI - Methode nach ApplicationUtil oder so &uuml;bertragen.
	 * 
	 */
	public static SortedVector<SimpleIndexMetaData> GetIndexMetaData(JDBCDataSourceRecord dsr, String schemaName) {
		SortedVector<SimpleIndexMetaData> svt = new SortedVector<SimpleIndexMetaData>();
		try {
			Connection c = ConnectionManager.GetConnection(dsr);
			svt = ApplicationUtil.GetIndexMetaData(c, schemaName);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return svt;
	}

	/* Holt die Index-Metadaten aus der angegebenen Datenbank. */
	private SortedVector<SimpleIndexMetaData> getIndexMetadata(JDBCDataSourceRecord dsr, String schemaName) {
		return GetIndexMetaData(dsr, schemaName);
	}

	/**
	 * Liefert eine Liste mit den Unique-Metadaten aus der angegebenen Datenbank.
	 * 
	 * @param dsr        Die Daten zur Verbindung mit der Datenbank.
	 * @param schemaName Ein Schemaname, falls das Modell auf ein spezielles Schema der Datenbank abgestimmt werden
	 *                   soll. Soll das Defaultschema genutzt werden, kann der Name leer &uuml;bergeben werden.
	 * @return Eine Liste mit den Unique-Metadaten aus der angegebenen Datenbank.
	 * 
	 * @changed OLI 011.0.6.2013 - Hinzugef&uuml;gt.
	 * 
	 */
	public static SortedVector<UniqueMetaData> GetUniqueMetaData(JDBCDataSourceRecord dsr, String schemaName) {
		SortedVector<UniqueMetaData> svt = new SortedVector<UniqueMetaData>();
		try {
			Connection c = ConnectionManager.GetConnection(dsr);
			svt = ApplicationUtil.GetUniqueMetaData(c, schemaName);
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return svt;
	}

	/* Holt die Referenz-Metadaten aus der angegebenen Datenbank. */
	private SortedVector getReferenzMetadata(JDBCDataSourceRecord dsr) {
		SortedVector svt = new SortedVector();
		try {
			Connection c = ConnectionManager.GetConnection(dsr);
			Hashtable psparam = new Hashtable();
			int max = 0;
			ResultSet rs = null;
			SortedVector svtn0 = new SortedVector();
			SortedVector svtn1 = new SortedVector();
			String pktn = null;
			String pkcn = null;
			String fktn = null;
			String fkcn = null;
			String s = null;
			psparam.put("max", 100);
			psparam.put("now", 1);
			rs = c.getMetaData().getTables(null, null, "%", new String[] { "TABLE" });
			while (rs.next()) {
				s = rs.getString("TABLE_NAME");
				if (s != null) {
					svtn0.addElement(s);
					svtn1.addElement(s);
				}
			}
			DBExec.CloseQuery(rs);
			c.close();
			max = svtn0.size() * svtn0.size();
			psparam.put("max", max);
			try {
				Class.forName(dsr.getDriver());
				c = DriverManager.getConnection(dsr.getDBName(), dsr.getUser(), dsr.getPassword());
			} catch (ClassNotFoundException cnf) {
				cnf.printStackTrace();
			}
			for (int i = 0, leni = svtn0.size(); i < leni; i++) {
				System.out.println("\n" + svtn0.elementAt(i) + " (mem " + Runtime.getRuntime().freeMemory() + " of "
						+ Runtime.getRuntime().totalMemory() + ")");
				for (int j = 0, lenj = svtn0.size(); j < lenj; j++) {
					try {
						rs = c.getMetaData().getCrossReference(null, null, svtn0.elementAt(i).toString(), null, null,
								svtn1.elementAt(j).toString());
						while (rs.next()) {
							pktn = rs.getString("PKTABLE_NAME");
							pkcn = rs.getString("PKCOLUMN_NAME");
							fktn = rs.getString("FKTABLE_NAME");
							fkcn = rs.getString("FKCOLUMN_NAME");
							System.out.println("    " + fktn + "." + fkcn + " > " + pktn + "." + pkcn);
							svt.addElement(new ReferenceImportRecord(fktn, fkcn, pktn, pkcn));
						}
						DBExec.CloseQuery(rs);
						System.out.print(
								"\b\b\b\b\b\b\b\b\b\b\b\b\b\b\b" + StrUtil.PumpUp("" + --max, " ", 10, Direction.LEFT)
										+ StrUtil.PumpUp("" + j, " ", 5, Direction.LEFT));
						if (Long.getLong("archimedes.gui.FrameArchimedes.import.delay", 250) > 0) {
							Thread th = new Thread() {
								@Override
								public void run() {
									try {
										Thread.sleep(Long.getLong("archimedes.gui.FrameArchimedes.import.delay", 250));
									} catch (Exception e) {
									}
								}
							};
							th.start();
							th.join();
						}
					} catch (SQLException sqle) {
						try {
							c.close();
						} catch (Exception e1) {
						}
						System.out.println("\nwaiting (" + j + ")");
						Thread th = new Thread() {
							@Override
							public void run() {
								try {
									Thread.sleep(Long.getLong("archimedes.gui.FrameArchimedes.import.timeout", 90000));
								} catch (Exception e) {
								}
							}
						};
						th.start();
						th.join();
						System.out.println("restarting");
						j--;
						try {
							Class.forName(dsr.getDriver());
							c = DriverManager.getConnection(dsr.getDBName(), dsr.getUser(), dsr.getPassword());
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				}
				System.gc();
			}
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return svt;
	}

	/**
	 * Updates the model name in the user ping.
	 * 
	 * @param modelName The new model name for the user ping.
	 * 
	 * @changed OLI 22.03.2016 - Added. / public void updateUserPingModelName() { if ((this.diagramm != null) &&
	 *          (this.userPing != null)) { this.userPing.setCurrentModelName(this.diagramm.getName()); } }
	 * 
	 *          /** Aktualisiert das View-Men&uuml; anhand der Viewliste des &uuml;bergebenen Diagramms.
	 * 
	 * @param menu  Referenz auf das Men&uuml;, dessen Inhalt aus der Viewliste gebildet werden soll.
	 * @param views Die Liste der Views, die in das Men&uuml; &uuml;bernommen werden sollen.
	 */
	public void updateViewMenu(JMenu menu, java.util.List<GUIViewModel> views) {
		menu.removeAll();
		menu.add(this.menuitemtabletoview);
		menu.add(new JSeparator());
		menu.add(this.menuitemswitchrelationtoview);
		menu.add(this.menuitemswitchtechnicalfieldstoview);
		menu.add(this.menuitemswitchtransientfieldstoview);
		menu.add(new JSeparator());
		for (int i = 0, len = views.size(); i < len; i++) {
			JMenuItem menuItem = new JMenuItem(new ViewAction(views.get(i)));
			menuItem.setText(views.get(i).getName());
			menu.add(menuItem);
		}
	}

	/**
	 * Updates the warning message.
	 * 
	 * @param message The message which is to update.
	 * @param warning Set this flag if the message notifies a warning or an error.
	 * 
	 * @changed OLI 18.05.2016 - Added.
	 */
	public void updateWarnings(String message, boolean warning) {
		this.component.updateWarnings(message);
		if (warning) {
			this.component.setWarningLabelForeGround(Color.RED);
		} else {
			this.component.setWarningLabelForeGround(Color.BLACK);
		}
	}

	/* Klasse f&uuml;r die View-Men&uuml;punkte. */
	class ViewAction extends AbstractAction {
		private GUIViewModel view = null;

		public ViewAction(GUIViewModel view) {
			super();
			this.view = view;
		}

		@Override
		public String toString() {
			return (this.view != null ? this.view.getName() : "<null>");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (this.view instanceof MainViewModel) {
				menuitemneustandardtabelle.setEnabled(true);
				menuitemneutabelle.setEnabled(true);
			} else {
				menuitemneustandardtabelle.setEnabled(false);
				menuitemneutabelle.setEnabled(false);
			}
			doChangeView(this.view);
		}
	}

	/**
	 * Aktualisiert die Komponente zum Anzeigen des Diagramms auf den angegebenen View.
	 * 
	 * @param view Der View, den die Komponente anzeigen soll.
	 */
	public void doChangeView(GUIViewModel view) {
		this.component.setView(view);
		this.component.doRepaint();
	}

	/**
	 * Diese Methode wird aufgerufen, wenn der Meb&uuml;punkt Views / Tabelle einf&uuml;gen anclickt.
	 */
	public void doViewsEinfuegen() {
		corentx.util.SortedVector<TableModel> sv = new corentx.util.SortedVector<TableModel>();
		for (TableModel tm : this.diagramm.getTables()) {
			sv.add(tm);
		}
		new FrameSelectionDjinn("Tabellenauswahl", sv, false, this.getInifile()) {
			@Override
			public void doSelected(Vector values) {
				for (int i = 0, len = values.size(); i < len; i++) {
					TabellenModel tm = (TabellenModel) values.elementAt(i);
					if (!tm.getViews().contains(component.getView())) {
						tm.setXY((GUIViewModel) component.getView(), tm.getX(diagramm.getViews().get(0)),
								tm.getY(diagramm.getViews().get(0)));
					}
					diagramm.raiseAltered();
					component.doRepaint();
				}
			}
		};
	}

	/**
	 * Shows a monitor with the running Archimedes instances.
	 * 
	 * @changed OLI 15.03.2016 - Added. / public void doServerMonitor() { new UserMonitorFrame(this.guiBundle); }
	 * 
	 *          /** Setzt die Ge&auml;ndert-Flagge des aktuellen Diagramms.
	 * 
	 * @changed OLI 27.11.2007 - Hinzugef&uuml;gt.
	 *          <P>
	 * 
	 */
	public void raiseAltered() {
		this.diagramm.raiseAltered();
	}

	/**
	 * @changed OLI 26.11.2014 - Added.
	 */
	@Override
	public void eventFired(EditorFrameEvent<?, ? extends Window> e) {
		if (e instanceof BaseConfigurationFrameEvent) {
			BaseConfigurationFrameEvent bcfe = (BaseConfigurationFrameEvent) e;
			if (bcfe.getEventType() == EditorFrameEventType.OK) {
				BaseConfiguration bc = bcfe.getEditedObject();
				String propfn = corentx.io.FileUtil.completePath(System.getProperty("user.home"))
						.concat(".archimedes.properties");
				System.setProperty("archimedes.user.company", bc.getCompany());
				System.getProperty("archimedes.user.database.name", bc.getDBName());
				System.getProperty("archimedes.user.database.server.name", bc.getDBServerName());
				System.getProperty("archimedes.user.database.user.name", bc.getDBUserName());
				System.setProperty("archimedes.user.language", bc.getLanguage());
				System.setProperty("archimedes.user.name", bc.getUserName());
				System.setProperty("archimedes.user.token", bc.getUserToken());
				Properties p = new Properties();
				p.setProperty("archimedes.user.company", bc.getCompany());
				p.setProperty("archimedes.user.database.name", bc.getDBName());
				p.setProperty("archimedes.user.database.server.name", bc.getDBServerName());
				p.setProperty("archimedes.user.database.user.name", bc.getDBUserName());
				p.setProperty("archimedes.user.language", bc.getLanguage());
				p.setProperty("archimedes.user.name", bc.getUserName());
				p.setProperty("archimedes.user.token", bc.getUserToken());
				try {
					corentx.io.FileUtil.writeProperties(p, propfn, "Archimedes base " + "configurations");
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				BaseConfigurationFrame bcf = bcfe.getSource();
				bcf.removeEditorFrameListener(this);
				bcf.dispose();
				this.raiseAltered();
			}
		}
	}

	/**
	 * Liefert die Benutzerinformationen der Applikation.
	 * 
	 * @return Die Benutzerinformationen der Applikation.
	 * 
	 * @changed OLI 24.02.2012 - Hinzugef&uuml;gt.
	 */
	public UserInformation getUserInformation() {
		return this.userInformation;
	}

	/**
	 * @changed OLI 14.05.2013 - Added.
	 */
	@Override
	public void objectDoubleClicked(GUIObjectModel object, ComponentDiagramm<GUIObjectTypes> component, int buttonId) {
		if (buttonId == 1) {
			this.editTable(object, component);
		} else {
			if (object instanceof TableModel) {
				new TableModelFrame(this.guiBundle, (TableModel) object, this);
			}
		}
	}

	private void editTable(GUIObjectModel object, final ComponentDiagramm component) {
		this.setPredeterminedOptionProviderForDiagram();
		final TabellenModel tab = (TabellenModel) object;
		new FrameEditorDjinn(
				StrUtil.FromHTML(this.guiBundle.getResourceText(RES_TABLE_FRAME_EDIT_TITLE, object.getName())), tab,
				ini) {
			@Override
			public void doChanged(boolean saveOnly) {
				diagramm.raiseAltered();
				component.doRepaint();
			}

			@Override
			public void doDeleted() {
				deleteTable(tab);
			}
		};
	}

	public void deleteTable(TableModel tab) {
		ColumnModel[] rcms = tab.getDataModel().getReferencers(tab);
		if (!(component.getView() instanceof MainViewModel)) {
			String[] options = new String[] { "View", "Modell" };
			if (options[JOptionPane.showOptionDialog(null,
					StrUtil.FromHTML(
							"Soll die " + "Tabelle nur aus dem View\noder aus dem Modell gel&ouml;scht " + "werden?"),
					StrUtil.FromHTML("R&uuml;ckfrage L&ouml;schen"), JOptionPane.DEFAULT_OPTION,
					JOptionPane.QUESTION_MESSAGE, null, options, options[0])].equals("View")) {
				tab.removeFromView((ViewModel) component.getView());
				component.doRepaint();
				return;
			}
		}
		if (rcms.length > 0) {
			JOptionPane
					.showMessageDialog(null,
							StrUtil.FromHTML("Die Tabellen wird " + "durch " + rcms[0].getFullName()
									+ " referenziert!\n" + "L&ouml;schen nicht m&ouml;glich!"),
							"Referenzproblem!", JOptionPane.YES_OPTION);
			System.out.println("Wird Referenziert durch " + rcms[0].getFullName());
			return;
		}
		tab.getDataModel().removeTable(tab);
		new DefaultIndexListCleaner().clean(tab.getDataModel());
		raiseAltered();
		component.doRepaint();
	}

	/**
	 * @changed OLI 23.05.2013 - Added.
	 */
	@Override
	public void diagrammModelChanged() {
		this.stateChanged(this.diagramm.isAltered());
	}

	/**
	 * @changed OLI 23.05.2013 - Added.
	 */
	@Override
	public void stateChanged(boolean newState) {
		this.startChecker();
		this.updateHeadline();
		if (this.fov != null) {
			this.fov.updateView();
			this.component.doRepaint();
		}
	}

	/**
	 * @changed OLI 23.10.2013 - Added.
	 */
	@Override
	public void tableChanged(TableChangedEvent e) {
		LOG.debug("table changed event detected: " + e);
		this.raiseAltered();
		this.diagrammModelChanged();
	}

	/**
	 * @changed OLI 01.10.2015 - Added.
	 */
	@Override
	public boolean accept(EventObject e) {
		return (e.getSource() instanceof JTextField);
	}

	/**
	 * @changed OLI 11.07.2016 - Added.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.menuItemFileSave) {
			this.doDateiSpeichern(this.dateiname, false);
		}
	}

	/**
	 * @changed OLI 01.10.2015 - Added.
	 */
	@Override
	public void eventDispatched(EventObject e) {
		if (e.getSource() instanceof JTextField) {
			String co = COUtil.GetFullContext((Component) e.getSource());
			OptionType ot = this.getOptionType(co);
			if (ot != null) {
				JTextField tf = (JTextField) e.getSource();
				if (e instanceof MouseEvent) {
					MouseEvent me = (MouseEvent) e;
					if (me.getModifiersEx() == MouseEvent.BUTTON3_DOWN_MASK) {
						this.setPredeterminedOptionProviderForDiagram();
						new OptionsPopupMenu(this.diagramm.getPredeterminedOptionProvider(), ot, me.getX(), me.getY(),
								tf, OptionsSelectionMode.INSERT);
					}
				} else if (e instanceof KeyEvent) {
					KeyEvent ke = (KeyEvent) e;
					if ((ke.getKeyCode() == KeyEvent.VK_S) && ke.isControlDown()) {
						this.setPredeterminedOptionProviderForDiagram();
						new OptionsPopupMenu(this.diagramm.getPredeterminedOptionProvider(), ot, tf.getX(), tf.getY(),
								tf, OptionsSelectionMode.INSERT);
					}
				}
			}
		}
	}

	private OptionType getOptionType(String co) {
		if (co.endsWith("Tabellenspalte.Parameter")) {
			return OptionType.COLUMN;
		} else if (co.endsWith("Domain.Parameter")) {
			return OptionType.DOMAIN;
		} else if (co.endsWith("Panel.Parameter")) {
			return OptionType.PANEL;
		}
		return null;
	}

	/**
	 * @changed OLI 18.05.2016 - Added.
	 */
	@Override
	public void eventDetected(DiagramComponentPanelEvent event) {
		if ((event.getClickCount() == 2) && (this.lastModelCheckerMessages != null)) {
			if (this.modelCheckerMessageListFrame == null) {
				this.modelCheckerMessageListFrame = new ModelCheckerMessageListFrame(this.guiBundle,
						this.lastModelCheckerMessages, false);
				this.modelCheckerMessageListFrame.addBaccaraFrameListener(new ModelCheckerMessageListFrameListener() {
					@Override
					public void eventDetected(Event event) {
						if (event.getMessage().getObject() instanceof Tabelle) {
							editTable((GUIObjectModel) event.getMessage().getObject(), component.getComponentDiagram());
						}
					}
				});
			} else {
				this.modelCheckerMessageListFrame.setVisible(true);
				this.modelCheckerMessageListFrame.toFront();
				this.modelCheckerMessageListFrame.updateMessages(this.lastModelCheckerMessages);
			}
		}
	}

	/**
	 * @changed OLI 18.05.2016 - Added.
	 */
	@Override
	public void notifyModelCheckerResult(ModelCheckerMessage[] mcms) {
		if (mcms.length > 0) {
			int errorCount = this.getMessageCount(mcms, ModelCheckerMessage.Level.ERROR);
			int warnCount = this.getMessageCount(mcms, ModelCheckerMessage.Level.WARNING);
			String s = "";
			boolean warning = false;
			if (errorCount > 0) {
				s += this.guiBundle.getResourceText(RES_WARNING_MESSAGES_ERRORS_DETECTED, errorCount);
				warning = true;
			}
			if (warnCount > 0) {
				s += (s.length() > 0 ? ", " : "")
						+ this.guiBundle.getResourceText(RES_WARNING_MESSAGES_WARNINGS_DETECTED, warnCount);
				warning = true;
			}
			this.updateWarnings(s, warning);
		} else {
			this.updateWarnings(this.guiBundle.getResourceText(RES_WARNING_MESSAGES_NO_WARNINGS), false);
		}
		String path = this.diagramm.getCodePfad().replace("~", System.getProperty("user.home"));
		Object cf = this.getCodeFactory(path);
		if (cf instanceof ReadyToGenerateChecker) {
			this.menuGenerate.setEnabled(((ReadyToGenerateChecker) cf).isReadyToGenerate(this.diagramm));
		}
		this.lastModelCheckerMessages = mcms;
		if (this.modelCheckerMessageListFrame != null) {
			this.modelCheckerMessageListFrame.updateMessages(mcms);
		}
	}

	private int getMessageCount(ModelCheckerMessage[] mcms, ModelCheckerMessage.Level level) {
		int c = 0;
		for (ModelCheckerMessage mcm : mcms) {
			if (mcm.getLevel() == level) {
				c++;
			}
		}
		return c;
	}

	/**
	 * @changed OLI 15.06.2016 - Added.
	 */
	@Override
	public void eventDetected(Event e) {
		if (e.getType() == Event.Type.MESSAGE_SELECTED) {
			editTable((GUIObjectModel) e.getMessage().getObject(), this.component.getComponentDiagram());
		}
	}

	/**
	 * @changed OLI 07.12.2016 - Added.
	 */
	@Override
	public void eventFired(CodeFactoryEvent cfe) {
		if (cfe.getType() == CodeFactoryEventType.GENERATION_FINISHED) {
			cfe.getFactory().removeCodeFactoryListener(this);
		}
	}

	/**
	 * @changed OLI 07.12.2016 - Added.
	 */
	@Override
	public void exceptionDetected(CodeFactory cf, Throwable e) {
		String name = "n/a";
		String userMessage = null;
		if (e instanceof CodeGeneratorException) {
			CodeGeneratorException cge = (CodeGeneratorException) e;
			name = cge.getCodeFactoryName();
			userMessage = cf.getGUIBundle().getResourceText(cge.getMessageResourceId(), cge.getAdditionalParameters());
		} else {
			e.printStackTrace();
		}
		new JDialogThrowable(e,
				(userMessage != null ? userMessage : "Error while running code " + "generator " + name + "!"),
				this.getInifile(),
				(this.guiBundle.getResourceManager() instanceof PropertyRessourceManager
						? (PropertyRessourceManager) this.guiBundle.getResourceManager()
						: new PropertyRessourceManager()));
	}

} // 2556