package cz.macgregor.eden.grf.components.drawactions;

import java.awt.Graphics;
import java.awt.Point;

import cz.macgregor.eden.core.logic.tiles.Field;

/**
 * implementation of this interfaces draws a single field content.
 * 
 * @author MacGregor
 *
 */
public interface DrawAction {
	/**
	 * draw a content of a single field.
	 * 
	 * @param g
	 *            graphics
	 * @param field
	 *            field
	 * @param coords
	 *            coordinates on the label
	 * @param index
	 *            coordinates from the game map
	 */
	void draw(Graphics g, Field field, Point coords, Point index);
}
