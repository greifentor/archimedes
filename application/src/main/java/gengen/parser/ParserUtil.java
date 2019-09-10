/*
 * ParserUtil.java
 *
 * 23.04.2010
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.parser;

import java.util.List;
import java.util.Vector;

// import org.apache.log4j.*;

/**
 * Utility-Klasse mit Funktionen zum Parsen von Source-Codes.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 23.04.2010 - Hinzugef&uuml;gt.
 */

public class ParserUtil {

	/* Referenz auf den f&uuml;r die Klasse zust&auml;ndigen Logger. */
	// private static Logger log = Logger.getLogger(ParserUtil.class);

	/**
	 * Liefert eine Liste mit den FileBlock-Elementen eines Java-Quelltextdatei.
	 *
	 * @param sc Der in der zu parsenden Datei gespeicherte Java-Quelltext.
	 * @return Liste mit den FileBlocks der Java-Quelltextdatei.
	 * @throws IllegalArgumentException Falls der Quelltext als <TT>null</TT>-Referenz &uuml;bergeben wird.
	 * @precondition sc != <TT>null</TT>
	 *
	 * @changed OLI 23.04.2010 - Hinzugef&uuml;gt.
	 */
	public static List<FileBlock> getFileBlocks(String sc) throws IllegalArgumentException {
		char c = '\0';
		int i = 0;
		int leni = 0;
		List<FileBlock> lfb = new Vector<FileBlock>();
		String h = null;
		String s = "";
		if (sc == null) {
			throw new IllegalArgumentException("source code to parse can not be null.");
		}
		for (i = 0, leni = sc.length(); i < leni; i++) {
			c = sc.charAt(i);
			if (c == '*') {
				s = s.concat("" + c);
				if (s.endsWith("/**")) {
					h = s.substring(s.length() - 3, s.length());
					while (!h.endsWith("*/")) {
						c = sc.charAt(++i);
						h = h.concat("" + c);
					}
					lfb.add(new DefaultFileBlock("COMMENT", h));
					s = "";
				} else if (s.endsWith("/*")) {
					h = s.substring(s.length() - 2, s.length() - 1);
					while (!h.endsWith("*/")) {
						c = sc.charAt(++i);
						h = h.concat("" + c);
					}
					if (lfb.size() == 0) {
						lfb.add(new DefaultFileBlock("HEADER", h));
					} else {
						lfb.add(new DefaultFileBlock("COMMENT", h));
					}
					s = "";
				}
			} else if (c == '\n') {
				if ((s.length() == 0) || s.endsWith("\n")) {
					lfb.add(new DefaultFileBlock("EMPTYLINE", ""));
					s = "";
				}
			} else if (c == ' ') {
				if (s.endsWith("public class")) {
					h = s.substring(s.length() - 12, s.length());
					while (!h.endsWith("}")) {
						c = sc.charAt(++i);
						h = h.concat("" + c);
					}
					lfb.add(new DefaultFileBlock("IMPORT", h));
					s = "";
				} else if (s.endsWith("import")) {
					h = s.substring(s.length() - 6, s.length());
					while (!h.endsWith(";")) {
						c = sc.charAt(++i);
						h = h.concat("" + c);
					}
					lfb.add(new DefaultFileBlock("IMPORT", h));
					s = "";
				} else if (s.endsWith("package")) {
					h = s.substring(s.length() - 6, s.length());
					while (!h.endsWith(";")) {
						c = sc.charAt(++i);
						h = h.concat("" + c);
					}
					lfb.add(new DefaultFileBlock("PACKAGE", h));
					s = "";
				}
			} else {
				s = s.concat("" + c);
			}
		}
		return lfb;
	}

}
