package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.entities.Entity;

/**
 * Drawer to draw a list of entities on the field centered.
 * 
 * @author MacGregor
 *
 */
public class CenterDrawer implements EntityDrawer {
	/** X offset to draw relative from the center. */
	private final int offsetX;
	/** Y offset to draw relative from the center. */
	private final int offsetY;
	/** when drawing multiple entities, distance between them. */
	private final int step;

	/**
	 * constructor.
	 * 
	 * @param beginOffsetX
	 *            x offset
	 * @param beginOffsetY
	 *            y offset
	 * @param offsetAdd
	 *            step
	 */
	public CenterDrawer(int beginOffsetX, int beginOffsetY, int offsetAdd) {
		this.offsetX = beginOffsetX;
		this.offsetY = beginOffsetY;
		this.step = Math.abs(offsetAdd);
	}

	@Override
	public void draw(Graphics g, List<Entity> ents, int x, int y) {

		int lastIconWidth = ents.get(ents.size() - 1).getType().getImage().getIconWidth();

		int widthToDisplay = step * ents.size() + lastIconWidth;
		int heightToDisplay = 0;

		for (Entity ent : ents) {
			ImageIcon icon = ent.getType().getImage();
			if (icon.getIconHeight() > heightToDisplay) {
				heightToDisplay = icon.getIconHeight();
			}
		}

		int offset = 0;
		for (Entity ent : ents) {
			ImageIcon icon = ent.getType().getImage();

			int offsetXToDisplay = x + offsetX + offset - (widthToDisplay / 2);
			int offsetYToDisplay = y + offsetY - (heightToDisplay / 2);

			g.drawImage(icon.getImage(), offsetXToDisplay, offsetYToDisplay, null);
			offset += step;
		}

	}

}
