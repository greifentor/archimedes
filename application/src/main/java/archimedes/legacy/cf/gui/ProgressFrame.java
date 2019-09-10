/*
 * ProgressFrame.java
 *
 * 01.04.2018
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.cf.gui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.*;

import org.apache.log4j.*;

/**
 * A progress frame which shows a progress bar for the code generation steps and one for the
 * processed objects.
 *
 * @author O. Lieshoff
 *
 * @changed OLI 01.04.2018 - Added.
 */

public class ProgressFrame extends JFrame implements WindowListener {

    private static final int HGAP = 3;
    private static final int VGAP = 3;

    private static Logger log = Logger.getLogger(ProgressFrame.class);

    private JLabel labelGenerators = new JLabel();
    private JLabel labelObjects = new JLabel();
    private JLabel labelSteps = new JLabel();
    private JProgressBar progressBarGenerators = null;
    private JProgressBar progressBarObjects = null;
    private JProgressBar progressBarSteps = null;

    /**
     * Creates a progress dialog which shows a progress bar for steps and processed objects.
     *
     * @param caller A window which acts a caller for the progress frame. This window will not
     *         be locked but the progress frame will be kept in front of the window. Pass null
     *         here if no window is known as caller.
     * @param title A title for the progress frame.
     * @param steps The number of steps to process.
     */
    public ProgressFrame(Window caller, String title, int steps) {
        super(title);
        log.debug("opening progress frame.");
        this.progressBarGenerators = new JProgressBar(0, 0);
        this.progressBarObjects = new JProgressBar(0, 0);
        this.progressBarSteps = new JProgressBar(0, steps);
        JPanel p = createMainPanel();
        setContentPane(p);
        pack();
        centerOnScreen(caller);
        setVisible(true);
        log.debug("opened progress frame.");
    }

    private JPanel createMainPanel() {
        JPanel p = new JPanel(new GridLayout(4, 1, HGAP, VGAP));
        this.progressBarSteps.setStringPainted(true);
        this.progressBarObjects.setStringPainted(true);
        p.add(this.labelSteps);
        p.add(this.progressBarSteps);
        p.add(this.labelGenerators);
        p.add(this.progressBarGenerators);
        p.add(this.labelObjects);
        p.add(this.progressBarObjects);
        p.setBorder(new EmptyBorder(VGAP, HGAP, VGAP, HGAP));
        return p;
    }

    private void centerOnScreen(Window caller) {
        Rectangle r = null;
        if (caller != null) {
            r = caller.getBounds();
        } else {
            Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
            r = new Rectangle(0, 0, (int) d.getWidth(), (int) d.getHeight());
        }
        Dimension d = this.getPreferredSize();
        d.setSize(d.getWidth() * 2, d.getHeight());
        this.setSize(d);
        this.setBounds((r.width / 2) - (this.getWidth() / 2) + r.x, (r.height / 2) -
                (this.getHeight() / 2) + r.y, this.getWidth(), this.getHeight());
    }

    /**
     * Closes the progress frame.
     */
    public void close() {
        this.setVisible(false);
        this.dispose();
    }

    /**
     * Increments the progress for the generators. 
     */
    public void incrementGenerators() {
        incrementProgressBar(this.progressBarGenerators);
    }

    private void incrementProgressBar(JProgressBar progressBar) {
        int n = progressBar.getValue()+1;
        progressBar.setValue(n);
        progressBar.setString(String.valueOf(n));
        log.debug("incremented progress bar: " + progressBar);
    }

    /**
     * Increments the progress for the objects.
     */
    public void incrementObjects() {
        incrementProgressBar(this.progressBarObjects);
    }

    /**
     * Increments the progress for the steps.
     */
    public void incrementSteps() {
        incrementProgressBar(this.progressBarSteps);
    }

    /**
     * Resets the generators progress bar to value "0" and sets a new maximum value as passed.
     *
     * @param maxValue A new maximum value for the generators progress bar.
     */
    public void resetGenerators(int maxValue) {
        resetProgressBar(this.progressBarGenerators, maxValue);
    }

    private void resetProgressBar(JProgressBar progressBar, int maxValue) {
        progressBar.setValue(0);
        progressBar.setMaximum(maxValue);
        progressBar.setString(String.valueOf(0));
        log.debug("reset progress bar: " + progressBar + ", maxValue: " + maxValue);
    }

    /**
     * Resets the objects progress bar to value "0" and sets a new maximum value as passed.
     *
     * @param maxValue A new maximum value for the objects progress bar.
     */
    public void resetObjects(int maxValue) {
        resetProgressBar(this.progressBarObjects, maxValue);
    }

    /**
     * Sets the passed comment as new comment for the generators progress bar. 
     * 
     * @param comment The new comment for the generators progress bar.
     */
    public void setCommentGenerators(String comment) {
        setComment(this.labelGenerators, comment);
    }

    private void setComment(JLabel label, String comment) {
        label.setText(comment);
        log.debug("set comment " + label + ", comment: " + comment);
    }

    /**
     * Sets the passed comment as new comment for the objects progress bar. 
     * 
     * @param comment The new comment for the objects progress bar.
     */
    public void setCommentObjects(String comment) {
        setComment(this.labelObjects, comment);
    }

    /**
     * Sets the passed comment as new comment for the steps progress bar. 
     * 
     * @param comment The new comment for the steps progress bar.
     */
    public void setCommentSteps(String comment) {
        setComment(this.labelSteps, comment);
    }

    @Override public void windowActivated(WindowEvent e) {
    }

    @Override public void windowClosed(WindowEvent e) {
    }

    @Override public void windowClosing(WindowEvent e) {
    }

    @Override public void windowDeactivated(WindowEvent e) {
        this.toFront();
        this.requestFocus();
    }

    @Override public void windowDeiconified(WindowEvent e) {
    }

    @Override public void windowIconified(WindowEvent e) {
    }

    @Override public void windowOpened(WindowEvent e) {
    }

}