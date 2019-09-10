/*
 * DO NOT REMOVE !!!
 * 
 * Version.java
 *
 * 11.07.2016
 *
 * (c) by O.Lieshoff
 *
 */

package gengen;

import javax.swing.JOptionPane;

/**
 * Eine Klasse mit der Versionsnummer der Software.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 11.07.2016 - Hinzugef&uuml,gt.
 */

public class Version {

	public static final Version INSTANCE = new Version();

	/*
	 * Generiert eine Instanz der Versionsklasse mit Default-Parametern.
	 */
	private Version() {
		super();
	}

	/**
	 * Liefert die Nummer der Version zur Applikation.
	 *
	 * @return Die Versionsnummer zur Applikation.
	 */
	public String getVersion() {
		return "1.12.2";
	}

	@Override
	public String toString() {
		return this.getVersion();
	}

	public static void main(String[] args) {
		System.out.println("GenGen version is " + INSTANCE.getVersion());
		new Thread(new Runnable() {
			public void run() {
				JOptionPane.showMessageDialog(null, "Version of GenGen is: " + INSTANCE.getVersion(), "GenGen version",
						JOptionPane.INFORMATION_MESSAGE);
			}
		}).start();
	}

}
