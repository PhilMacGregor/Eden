package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.entities.Entity;

/**
 * Drawer that draws entities with position fixed to the center, sides or
 * corners of a field.
 * 
 * @author MacGregor
 *
 */
public class SimpleDrawer implements EntityDrawer {
	/** x offset for entities to be drawn at. */
	private final int offsetX;
	/** y offset for entities to be drawn at. */
	private final int offsetY;
	/**
	 * when drawing multiple entities, draw them this amount of pixels from each
	 * other.
	 */
	private final int step;

	/**
	 * constructor.
	 * 
	 * @param beginOffsetX
	 *            offset x
	 * @param beginOffsetY
	 *            offset y
	 * @param offsetAdd
	 *            step
	 */
	public SimpleDrawer(int beginOffsetX, int beginOffsetY, int offsetAdd) {
		this.offsetX = beginOffsetX;
		this.offsetY = beginOffsetY;
		this.step = offsetAdd;
	}

	@Override
	public void draw(Graphics g, List<Entity> ents, int x, int y) {

		int offset = 0;
		for (Entity ent : ents) {
			ImageIcon icon = ent.getType().getImage();

			int offsetXToDisplay = x + offsetX + offset - (offsetX != 0 ? icon.getIconWidth() : 0);
			int offsetYToDisplay = y + offsetY - (offsetY != 0 ? icon.getIconHeight() : 0);

			g.drawImage(icon.getImage(), offsetXToDisplay, offsetYToDisplay, null);
			offset += step;
		}

		offset = 0;

	}

}
