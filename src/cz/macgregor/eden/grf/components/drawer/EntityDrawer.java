package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.util.List;

import cz.macgregor.eden.core.logic.entities.Entity;

public interface EntityDrawer {
	void draw(Graphics g, List<Entity> ents, int x, int y);
}
