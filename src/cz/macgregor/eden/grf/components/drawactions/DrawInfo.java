package cz.macgregor.eden.grf.components.drawactions;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Const;

public class DrawInfo implements DrawAction {
	
	private boolean debug;
	
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
	
	private void drawHighLight(Graphics g, Color color, int x, int y) {
		g.setColor(color);
		g.drawRect(x, y, Const.TILE_WIDTH - 1, Const.TILE_HEIGHT - 1);
	}
	
	private void displayCoords(Graphics g, int x, int y, int indexX, int indexY) {
		g.drawString(String.format("[%d; %d]", indexX, indexY), x, y + 10);
	}

	/**
	 * @return the debug
	 */
	public boolean isDebug() {
		return debug;
	}

	/**
	 * @param debug the debug to set
	 */
	public void setDebug(boolean debug) {
		this.debug = debug;
	}

}
