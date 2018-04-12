package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.util.List;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.entities.Entity;

public class SimpleDrawer implements EntityDrawer {
	private final int offsetX;
	private final int offsetY;
	private final int step;

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
