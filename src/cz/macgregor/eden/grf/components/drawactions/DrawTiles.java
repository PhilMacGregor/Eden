package cz.macgregor.eden.grf.components.drawactions;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.TileType;

public class DrawTiles implements DrawAction {

	@Override
	public void draw(Graphics g, Field field, Point coords, Point index) {
		Image image;

		if (field == null) {
			image = TileType.UNKNOWN.getIcon().getImage();
		} else if (!field.isVisible()) {
			image = TileType.INVISIBLE.getIcon().getImage();
		} else {
			image = field.getType().getIcon().getImage();
		}

		g.drawImage(image, coords.x, coords.y, null);
	}

}
