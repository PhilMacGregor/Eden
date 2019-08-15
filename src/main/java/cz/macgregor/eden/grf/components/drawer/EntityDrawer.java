package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.util.List;

import cz.macgregor.eden.core.logic.entities.Entity;

/**
 * a drawer to draw a list of entities on the field.
 * 
 * @author MacGregor
 *
 */
public interface EntityDrawer {
	/**
	 * draw the entities.
	 * 
	 * @param g
	 *            graphics
	 * @param ents
	 *            entities
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	void draw(Graphics g, List<Entity> ents, int x, int y);
}
