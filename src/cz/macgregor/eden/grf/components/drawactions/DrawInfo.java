package cz.macgregor.eden.grf.components.drawactions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Const;

/**
 * he entity drawer used to draw the indformation about the field.
 * 
 * @author MacGregor
 *
 */
public class DrawInfo implements DrawAction {

	private boolean debug;

	/**
	 * constructor.
	 * 
	 * @param debug
	 *            whether the drawer should initially work in debug mode
	 */
	public DrawInfo(boolean debug) {
		this.setDebug(debug);
	}

	@Override
	public void draw(Graphics g, Field field, Point coords, Point index) {
		if (debug) {
			drawDebugInfo(g, field, coords, index);
		}

		if (field != null && field.isSelected()) {
			drawHighLight(g, Color.RED, coords.x, coords.y);
		}
	}

	/**
	 * if in debug mode, also highlight each field that has entites and display
	 * coordinates of each field.
	 * 
	 * @param g
	 *            graphics
	 * @param field
	 *            field
	 * @param coords
	 *            relative coordinates of the field
	 * @param index
	 *            absolute coordinates in the map
	 */
	private void drawDebugInfo(Graphics g, Field field, Point coords, Point index) {
		if (field != null) {
			if (field.hasEntites()) {
				drawHighLight(g, Color.CYAN, coords.x, coords.y);
			}
		}

		if (index.x == 0 || index.y == 0) {
			g.setColor(Color.WHITE);
		} else {
			g.setColor(Color.ORANGE);
		}

		if (field == null) {
			g.setColor(Color.GRAY);
		}

		displayCoords(g, coords.x, coords.y, index.x, index.y);
	}

	/**
	 * highlight a field - draw a rectangle around it.
	 * 
	 * @param g
	 *            graphics
	 * @param color
	 *            color of the highlight
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	private void drawHighLight(Graphics g, Color color, int x, int y) {
		g.setColor(color);
		g.drawRect(x, y, Const.TILE_WIDTH - 1, Const.TILE_HEIGHT - 1);
	}

	/**
	 * display coordinates of the field.
	 * 
	 * @param g
	 *            graphics
	 * @param x
	 *            relative x coordinate of the field
	 * @param y
	 *            relative y coordinate of the field
	 * @param indexX
	 *            absolute x coordinate in the map, the number to display
	 * @param indexY
	 *            absolute y coordinate in the map, the number to display
	 */
	private void displayCoords(Graphics g, int x, int y, int indexX, int indexY) {
		g.drawString(String.format("[%d; %d]", indexX, indexY), x, y + 10);
	}

	/**
	 * is in debug mode.
	 * 
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * set debug mode.
	 * 
	 * @param debug
	 *            the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
