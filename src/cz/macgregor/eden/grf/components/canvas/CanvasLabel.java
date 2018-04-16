package cz.macgregor.eden.grf.components.canvas;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.entities.DrawTarget;
import cz.macgregor.eden.core.logic.entities.DrawTarget.Direction;
import cz.macgregor.eden.core.logic.patterns.StandardPattern;
import cz.macgregor.eden.core.logic.tiles.FieldInfo;
import cz.macgregor.eden.grf.components.drawactions.DrawAction;
import cz.macgregor.eden.grf.components.drawactions.DrawEntities;
import cz.macgregor.eden.grf.components.drawactions.DrawInfo;
import cz.macgregor.eden.grf.components.drawactions.DrawTiles;
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;
import cz.macgregor.eden.grf.listener.CanvasMouseListener;
import cz.macgregor.eden.util.Const;

/**
 * JLabel used to display map around a given focus point.
 * 
 * @author MacGregor
 *
 */
public class CanvasLabel extends JLabel {
	private static final long serialVersionUID = 1L;

	/**
	 * this will be set true after initialisation to prevent errors trying to
	 * paint uninitialised map.
	 */
	private boolean ready;

	/** map to paint. */
	private GameMap map;
	/**
	 * focus point. Only display the map around this point. Ignore other map
	 * content.
	 */
	private Point focusPoint;

	/** canvas size in a count of map fields. */
	private final Point canvasSize;

	/**
	 * list of draw actions to be used. Each DrawAction represents a single
	 * layer to be drawn.
	 */
	private List<DrawAction> drawActions;

	/** draw action to display debug info. */
	private DrawInfo drawInfoAction;

	/**
	 * constructor.
	 * 
	 * @param canvasWidth
	 *            canvas width in a count of map fields
	 * @param canvasHeight
	 *            canvas height in a count of map fields
	 * @param drawers
	 *            entity drawers used for drawing entities
	 */
	public CanvasLabel(int canvasWidth, int canvasHeight, Map<Direction, EntityDrawer> drawers) {
		this.ready = false;

		this.canvasSize = new Point(canvasWidth, canvasHeight);

		this.drawInfoAction = new DrawInfo(false);

		this.drawActions = new ArrayList<>();
		drawActions.add(new DrawTiles());
		drawActions.add(new DrawEntities(DrawTarget.LAYER_GROUND, drawers));
		drawActions.add(new DrawEntities(DrawTarget.LAYER_STRUCT, drawers));
		drawActions.add(new DrawEntities(DrawTarget.LAYER_BIO, drawers));
		drawActions.add(new DrawEntities(DrawTarget.LAYER_CLOUDS, drawers));
		drawActions.add(drawInfoAction);

		this.addMouseListener(new CanvasMouseListener(this));
	}

	public Point getFocusPoint() {
		return focusPoint;
	}

	public Point getCanvasSize() {
		return canvasSize;
	}

	public GameMap getMap() {
		return map;
	}

	/**
	 * repaint the map.
	 * 
	 * @param map
	 *            map to paint
	 * @param focusPoint
	 *            focus point
	 * @param debug
	 *            debug mode
	 */
	public void repaint(GameMap map, Point focusPoint, boolean debug) {
		this.ready = true;
		this.map = map;
		this.focusPoint = focusPoint;

		this.drawInfoAction.setDebug(debug);

		this.repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (!ready) {
			return;
		}

		int fromX = focusPoint.x - (canvasSize.x / 2);
		int fromY = focusPoint.y - (canvasSize.y / 2);
		int toX = fromX + canvasSize.x;
		int toY = fromY + canvasSize.y;

		Point from = new Point(fromX, fromY);
		Point to = new Point(toX, toY);

		List<FieldInfo> fields = map.getFieldsByPattern(new StandardPattern(), from, to);

		for (DrawAction action : drawActions) {
			paintMap(g, from, to, fields, action);
		}

	}

	/**
	 * method to paint the map, call once for every draw action from the
	 * drawActions.
	 * 
	 * @param g
	 *            graphics
	 * @param from
	 *            from point for the map
	 * @param to
	 *            to point from the map
	 * @param fields
	 *            fields to be drawn
	 * @param action
	 *            used draw action
	 */
	private void paintMap(Graphics g, Point from, Point to, List<FieldInfo> fields, DrawAction action) {

		for (FieldInfo fld : fields) {
			Point index = fld.getPosition();
			Point coords = new Point((index.x - from.x) * Const.TILE_WIDTH, (index.y - from.y) * Const.TILE_HEIGHT);

			action.draw(g, fld.getField(), coords, index);
		}

	}

}