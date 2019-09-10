/*
 * ArtifactGeneratorListConfigurationDialog.java
 *
 * 16.09.2015
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;


import archimedes.cf.*;
import archimedes.legacy.cf.ArtifactGenerator;
import archimedes.legacy.cf.ObjectsToGenerateCollection;
import archimedes.legacy.model.DataModel;
import archimedes.legacy.model.DomainModel;
import archimedes.legacy.model.TableModel;
import archimedes.model.*;

import baccara.gui.*;

import corent.gui.*;

import gengen.*;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;


/**
 * A modal dialog to configure the code generators.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 16.10.2013 - Added.
 * @changed OLI 16.09.2015 - Moved to "Archimedes".
 * @changed OLI 03.04.2018 - Copied and made ready for the artifact generator logic. 
 */

public class ArtifactGeneratorListConfigurationDialog extends JDialogWithInifile
        implements ActionListener {

    public enum ClosingState {CANCEL, GENERATE};

    private ArtifactGenerator<?>[] artifactGenerators = null;
    private ArtifactGeneratorListTableModel artifactGeneratorListTableModel = null;
    private JButton buttonCancel = null;
    private JButton buttonConfig = null;
    private JButton buttonGenerate = null;
    private JButton buttonSelectDomainsAll = null;
    private JButton buttonSelectDomainsNone = null;
    private JButton buttonSelectGeneratorsAll = null;
    private JButton buttonSelectGeneratorsNone = null;
    private JButton buttonSelectTablesAll = null;
    private JButton buttonSelectTablesNone = null;
    private ClosingState closingState = null;
    private DataModel dataModel = null; 
    private DomainsTableModel domainsTableModel = null;
    private String configFilePath = null;
    private GUIBundle guiBundle = null;
    private IndividualPreferences ip = null;
    private JLabel labelPath = null;
    private TablesTableModel tablesTableModel = null;

    /**
     * Creates a new Dialog with the passed parameters.
     *
     * @param guiBundle A bundle with GUI information.
     * @param artifactGenerators A list of artifact generators which are configured by the
     *         dialog.
     * @param ip Individual preferences for code generation.
     * @param dataModel The data model which the code is to create for.
     * @param configFilePath The path of the file with the individual preferences.
     * @param codeFactoryVersion The version number of the code factory.
     */
    public ArtifactGeneratorListConfigurationDialog(GUIBundle guiBundle,
            ArtifactGenerator<?>[] artifactGenerators, IndividualPreferences ip,
            DataModel dataModel, String configFilePath, String codeFactoryVersion) {
        super((JFrame) null, guiBundle.getInifile());
        this.artifactGenerators = artifactGenerators;
        this.configFilePath = configFilePath;
        this.ip = ip;
        this.dataModel = dataModel;
        this.guiBundle = guiBundle;
        this.setTitle(this.guiBundle.getResourceText("artifact.generators.configuration.title"
                ).replace("$VERSION$", codeFactoryVersion));
        this.setLayout(new BorderLayout(this.guiBundle.getHGap(), this.guiBundle.getVGap()));
        JPanel main = new JPanel(new BorderLayout(this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        main.setBorder(new EmptyBorder(this.guiBundle.getVGap(), this.guiBundle.getHGap(),
                this.guiBundle.getVGap(), this.guiBundle.getHGap()));
        main.add(this.createPathLabelPanel(this.ip.getBaseCodePathes()), BorderLayout.NORTH);
        main.add(this.createButtonPanel(), BorderLayout.SOUTH);
        main.add(this.createTablePanel(), BorderLayout.CENTER);
        this.setContentPane(main);
        this.pack();
        this.setModal(true);
        this.setVisible(true);
    }

    private JPanel createPathLabelPanel(String basePath) {
        JLabel label = new JLabel(this.guiBundle.getResourceText(
                "artifact.generators.configuration.label.path.label"));
        this.labelPath = new JLabel(basePath);
        this.labelPath.setForeground(new Color(0, 0, 160));
        this.labelPath.setFont(new Font("SansSerif", Font.BOLD, 12));
        this.buttonConfig = this.createButton("config");
        JPanel p = new JPanel(new BorderLayout(this.guiBundle.getHGap(), this.guiBundle.getVGap(
                )));
        p.add(label, BorderLayout.WEST);
        p.add(this.labelPath, BorderLayout.CENTER);
        p.add(this.buttonConfig, BorderLayout.EAST);
        return p;
    }

    private JPanel createButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        this.buttonCancel = this.createButton("cancel");
        this.buttonGenerate = this.createButton("generate");
        p.add(buttonGenerate);
        p.add(new JLabel("    "));
        p.add(buttonCancel);
        return p;
    }

    private JButton createButton(String name) {
        JButton button = new JButton(this.guiBundle.getResourceText("artifact.generators."
                + "configuration.button." + name + ".label"));
        button.addActionListener(this);
        ImageIcon icon = this.guiBundle.getImageProvider().getImageIcon("button_" + name);
        if (icon != null) {
            button.setIcon(icon);
        }
        return button;
    }

    private JPanel createTablePanel() {
        JPanel p = new JPanel(new GridLayout(1, 2, this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        p.add(this.createObjectTablePanel());
        p.add(this.createGeneratorsTablePanel());
        return p;
    }

    private JPanel createObjectTablePanel() {
        JPanel p = new JPanel(this.guiBundle.createGridLayout(1, 1));
        JTabbedPane tp = new JTabbedPane();
        tp.add(this.guiBundle.getResourceText("artifact.objects.tab.tables.label"),
                createTablesTablePanel());
        tp.add(this.guiBundle.getResourceText("artifact.objects.tab.domains.label"),
                createDomainsTablePanel());
        p.add(tp);
        return p;
    }

    private JPanel createTablesTablePanel() {
        this.tablesTableModel = new TablesTableModel(this.dataModel.getTables(), this.guiBundle
                );
        JTable tableTables = new JTableWithInifile(this.tablesTableModel,
                this.guiBundle.getInifile(), "TablesConfigurationTable");
        JPanel panelTables = new JPanel(new BorderLayout(this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        panelTables.setBorder(new LineBorder(Color.darkGray, 1));
        panelTables.add(new JScrollPane(tableTables), BorderLayout.CENTER);
        panelTables.add(this.createTablesButtonPanel(), BorderLayout.SOUTH);
        return panelTables;
    }

    private JPanel createGeneratorsTablePanel() {
        this.artifactGeneratorListTableModel = new ArtifactGeneratorListTableModel(
                this.artifactGenerators, this.guiBundle);
        JTable tableGenerators = new JTableWithInifile(this.artifactGeneratorListTableModel,
                this.guiBundle.getInifile(), "ArtifactGeneratorConfigurationTable");
        tableGenerators.setDefaultRenderer(String.class, new TableGeneratorsCellRenderer(
                this.artifactGenerators));
        JPanel panelGenerators = new JPanel(new BorderLayout(this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        panelGenerators.setBorder(new LineBorder(Color.darkGray, 1));
        panelGenerators.add(new JScrollPane(tableGenerators), BorderLayout.CENTER);
        panelGenerators.add(this.createGeneratorButtonPanel(), BorderLayout.SOUTH);
        return panelGenerators;
    }

    private JPanel createTablesButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        this.buttonSelectTablesAll = this.createButton("selectAll");
        this.buttonSelectTablesNone = this.createButton("selectNothing");
        p.add(buttonSelectTablesAll);
        p.add(buttonSelectTablesNone);
        return p;
    }

    private JPanel createDomainsTablePanel() {
        this.domainsTableModel = new DomainsTableModel(this.dataModel.getAllDomains(),
                this.guiBundle);
        JTable tableDomains = new JTableWithInifile(domainsTableModel,
                this.guiBundle.getInifile(), "DomainsConfigurationTable");
        JPanel panelDomains = new JPanel(new BorderLayout(this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        panelDomains.setBorder(new LineBorder(Color.darkGray, 1));
        panelDomains.add(new JScrollPane(tableDomains), BorderLayout.CENTER);
        panelDomains.add(this.createDomainsButtonPanel(), BorderLayout.SOUTH);
        return panelDomains;
    }

    private JPanel createDomainsButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        this.buttonSelectDomainsAll = this.createButton("selectAll");
        this.buttonSelectDomainsNone = this.createButton("selectNothing");
        p.add(buttonSelectDomainsAll);
        p.add(buttonSelectDomainsNone);
        return p;
    }

    private JPanel createGeneratorButtonPanel() {
        JPanel p = new JPanel(new FlowLayout(FlowLayout.RIGHT, this.guiBundle.getHGap(),
                this.guiBundle.getVGap()));
        this.buttonSelectGeneratorsAll = this.createButton("selectAll");
        this.buttonSelectGeneratorsNone = this.createButton("selectNothing");
        p.add(buttonSelectGeneratorsAll);
        p.add(buttonSelectGeneratorsNone);
        return p;
    }

    @Override public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.buttonCancel) {
            this.closeDialog(ClosingState.CANCEL);
        } else if (e.getSource() == this.buttonConfig) {
            new IndividualPreferencesDialog(this, this.guiBundle, this.ip, this.configFilePath);
            this.updateView();
        } else if (e.getSource() == this.buttonGenerate) {
            this.closeDialog(ClosingState.GENERATE);
        } else if ((e.getSource() == this.buttonSelectDomainsAll)
                || (e.getSource() == this.buttonSelectDomainsNone)) {
            this.domainsTableModel.setSelectedAll(e.getSource() == this.buttonSelectDomainsAll);
            this.repaint();
        } else if ((e.getSource() == this.buttonSelectGeneratorsAll)
                || (e.getSource() == this.buttonSelectGeneratorsNone)) {
            this.artifactGeneratorListTableModel.setSelectedAll(
                    e.getSource() == this.buttonSelectGeneratorsAll);
            this.repaint();
        } else if ((e.getSource() == this.buttonSelectTablesAll)
                || (e.getSource() == this.buttonSelectTablesNone)) {
            this.tablesTableModel.setSelectedAll(e.getSource() == this.buttonSelectTablesAll);
            this.repaint();
        }
    }

    private void closeDialog(ClosingState closingState) {
        this.closingState = closingState;
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Returns the closing state of the dialog.
     *
     * @return The closing state of the dialog.
     */
    public ClosingState getClosingState() {
        return this.closingState;
    }

    /**
     * Returns a container with all objects which the code is to generate for (e. g. tables,
     * domains and code generators).
     *
     * @return A container with all objects which the code is to generate for (e. g. tables,
     *         domains and code generators).
     */
    public ObjectsToGenerateCollection getObjectToGenerateCodeForCollection() {
        ObjectsToGenerateCollection c = new ObjectsToGenerateCollection();
        for (DomainModel domain : this.domainsTableModel.getSelectedObjects()) {
            c.add(domain);
        }
        for (TableModel table : this.tablesTableModel.getSelectedObjects()) {
            c.add(table);
        }
        for (ArtifactGenerator<?> generator :
                this.artifactGeneratorListTableModel.getSelectedObjects()) {
            c.add(generator);
        }
        return c;
    }

    /**
     * Updates the view.
     */
    public void updateView() {
        this.labelPath.setText(this.ip.getBaseCodePathes());
    }

}