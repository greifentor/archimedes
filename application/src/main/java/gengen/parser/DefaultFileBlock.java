/*
 * DefaultFileBlock.java
 *
 * 22.04.2010
 *
 * (c) by O.Lieshoff
 *
 */

package gengen.parser;

import corentx.util.Utl;

// import org.apache.log4j.*;

/**
 * Defaultimplementierung zum Interfaces <TT>FileBlock</TT>.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
 */

public class DefaultFileBlock implements FileBlock {

	/* Referenz auf den f&uuml;r die Klasse zust&auml;ndigen Logger. */
	// private static Logger log = Logger.getLogger(DefaultFileBlock.class);

	private String content = null;
	private String type = null;

	/**
	 * Generiert ein DefaultFileBlock-Objekt mit den angegebenen Parametern.
	 *
	 * @param type    Der Typ des FileBlocks.
	 * @param content Der Inhalt des FileBlocks.
	 * @throws IllegalArgumentException Falls einer der beiden Parameter als <TT>null</TT>-Referenz &uuml;bergeben wird.
	 * @precondition type != <TT>null</TT>
	 * @precondition content != <TT>null</TT>
	 *
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public DefaultFileBlock(String type, String content) throws IllegalArgumentException {
		super();
		this.setContent(content);
		this.setType(type);
	}

	@Override
	/**
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public boolean equals(Object o) {
		DefaultFileBlock dfb = null;
		if (!(o instanceof DefaultFileBlock)) {
			return false;
		}
		dfb = (DefaultFileBlock) o;
		return Utl.equals(this.getContent(), dfb.getContent()) && Utl.equals(this.getType(), dfb.getType());
	}

	@Override
	/**
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public String toString() {
		return this.getType().concat(" (").concat(this.getContent()).concat(")");
	}

	/* Implementierung des Interfaces FileBlock. */

	@Override
	/**
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public String getContent() {
		return this.content;
	}

	@Override
	/**
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public String getType() {
		return this.type;
	}

	@Override
	/**
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void setContent(String c) throws IllegalArgumentException {
		if (c == null) {
			throw new IllegalArgumentException("content can not be null.");
		}
		this.content = c;
	}

	@Override
	/**
	 * @changed OLI 22.04.2010 - Hinzugef&uuml;gt.
	 */
	public void setType(String t) throws IllegalArgumentException {
		if (t == null) {
			throw new IllegalArgumentException("type can not be null.");
		}
		this.type = t;
	}

}
