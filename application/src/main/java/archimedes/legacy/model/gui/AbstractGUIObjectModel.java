/*
 * AbstractGUIObjectModel.java
 *
 * 29.05.2013
 *
 * (c) by O.Lieshoff
 *
 */

package archimedes.legacy.model.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.GeneralPath;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Logger;

import archimedes.legacy.gui.PaintMode;
import archimedes.legacy.gui.diagram.ComponentDiagramm;
import archimedes.legacy.gui.diagram.CoordinateConverter;
import archimedes.legacy.gui.diagram.ShapeContainer;
import archimedes.legacy.model.StereotypeModel;
import corent.base.Direction;

/**
 * An abstract implementation of the <CODE>GUIObjectModel</CODE> interface.
 *
 * @author O.Lieshoff
 *
 * @changed OLI 29.05.2013 - Added by generalization of <CODE>familytree.scheme.gui.GUIPerson</CODE>
 */

abstract public class AbstractGUIObjectModel implements GUIObjectModel {

	static Logger log = Logger.getLogger(AbstractGUIObjectModel.class);

	protected List<GUIRelationModel> relations = new Vector<GUIRelationModel>();
	protected int height = 0;
	protected int id = 0;
	protected int width = 0;
	protected Hashtable<GUIViewModel, Integer> x = new Hashtable<GUIViewModel, Integer>();
	protected Hashtable<GUIViewModel, Integer> y = new Hashtable<GUIViewModel, Integer>();

	/**
	 * @changed OLI 31.05.2013 - Added.
	 */
	@Override
	public int compareTo(Object o) {
		return this.getName().compareTo(((GUIObjectModel) o).getName());
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public int getHeight() {
		return this.height;
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public GUIRelationModel[] getRelations() {
		return this.relations.toArray(new GUIRelationModel[0]);
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public StereotypeModel[] getStereotypes() {
		return new StereotypeModel[0];
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public int getWidth() {
		return this.width;
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public int getX(GUIViewModel view) {
		Integer it = this.x.get(view);
		if (it != null) {
			return it;
		}
		if (!Boolean.getBoolean("archimedes.scheme.Tabelle.suppress.table.out.of.bounds")) {
			System.out.println("Warnung: MIN_VALUE fuer x-Koordinate in Tabelle " + this.getName() + " angefordert!");
		}
		return Integer.MIN_VALUE;
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public int getY(GUIViewModel view) {
		Integer it = this.y.get(view);
		if (it != null) {
			return it;
		}
		if (!Boolean.getBoolean("archimedes.scheme.Tabelle.suppress.table.out.of.bounds")) {
			System.out.println("Warnung: MIN_VALUE fuer x-Koordinate in Tabelle " + this.getName() + " angefordert!");
		}
		return Integer.MIN_VALUE;
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public boolean isDeprecated() {
		return false;
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public boolean isInView(GUIViewModel view) {
		for (Enumeration<GUIViewModel> e = this.x.keys(); e.hasMoreElements();) {
			if (view.equals(e.nextElement())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public ShapeContainer[] paintRelations(CoordinateConverter cd, GUIViewModel view, Graphics g, PaintMode pntm) {
		List<ShapeContainer> v = new Vector<ShapeContainer>();
		for (GUIRelationModel r : this.getRelations()) {
			Graphics2D g2d = (Graphics2D) g;
			GeneralPath gp = new GeneralPath();
			Point p = null;
			Point[] points = r.getShapePointsForView(view);
			int hthalf = ComponentDiagramm.HIT_TOLERANCE / 2;
			if (pntm == PaintMode.EDITOR) {
				g2d.setColor(new Color(0, 153, 102));
				for (int j = 0; j < points.length; j++) {
					p = (Point) points[j];
					g2d.drawArc((int) cd.convert(p.getX() - hthalf), (int) cd.convert(p.getY() - hthalf),
							cd.convert(ComponentDiagramm.HIT_TOLERANCE), cd.convert(ComponentDiagramm.HIT_TOLERANCE), 0,
							360);
				}
			}
			g2d.setColor(Color.black);
			if (points.length > 0) {
				p = (Point) points[0];
				int x = (int) p.getX() - 3;
				int y = (int) p.getY() - 3;
				if (this.getDirection(view, r) == Direction.LEFT) {
					x -= 3;
				}
				if (this.getDirection(view, r) == Direction.RIGHT) {
					x += 3;
				}
				if (this.getDirection(view, r) == Direction.UP) {
					y -= 3;
				}
				if (this.getDirection(view, r) == Direction.DOWN) {
					y += 3;
				}
				g.fillOval(cd.convert(x), cd.convert(y), cd.convert(7), cd.convert(7));
				gp.moveTo((float) cd.convert(p.getX()), (float) cd.convert(p.getY()));
				for (int j = 1; j < points.length; j++) {
					p = (Point) points[j];
					gp.lineTo((float) cd.convert(p.getX()), (float) cd.convert(p.getY()));
				}
			}
			// if (ts.isNotNull()) {
			g2d.setStroke(new BasicStroke(1));
			/*
			 * } else { // CHANGE FOR ADOPTION LATER g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_SQUARE,
			 * BasicStroke.JOIN_MITER, 1.0f, new float[] {8.0f, 12.0f}, 0.0f) ); }
			 */
			g2d.draw(gp);
			g2d.setStroke(new BasicStroke());
			v.add(new ShapeContainer(gp, r));
		}
		return v.toArray(new ShapeContainer[0]);
	}

	private Direction getDirection(GUIViewModel view, GUIRelationModel r) {
		return r.getDirection(view, (this == r.getReferencedObject() ? 0 : 1));
	}

	/**
	 * @changed OLI 29.05.2013 - Got from <CODE>familytree.scheme.gui.GUIPerson</CODE>.
	 */
	@Override
	public void setXY(GUIViewModel view, int x, int y) {
		this.x.put(view, x);
		this.y.put(view, y);
	}

}