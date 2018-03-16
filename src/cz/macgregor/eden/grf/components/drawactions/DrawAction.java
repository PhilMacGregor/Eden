package cz.macgregor.eden.grf.components.drawactions;

import java.awt.Graphics;
import java.awt.Point;

import cz.macgregor.eden.core.logic.tiles.Field;

public interface DrawAction {
	void draw(Graphics g, Field field, Point coords, Point index);
}
