package cz.macgregor.eden.core.logic.entities;

import javax.swing.ImageIcon;

import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

public class EntityWithPosition extends Entity {
	private static final int Y_OFFSET_TOP = -15;
	private static final int Y_OFFSET_BOTTOM = 0;
	
	private int coordsX;
	private int coordsY;

	public EntityWithPosition(EntityType type, int x, int y) {
		super(type);
		this.coordsX = x;
		this.coordsY = y;
	}
	
	public EntityWithPosition(EntityType type) {
		super(type);
		ImageIcon img = type.getImage();
		int x = Utils.randomInt(0, Const.TILE_WIDTH - img.getIconWidth());
		int y = Utils.randomInt(Y_OFFSET_TOP, Const.TILE_HEIGHT - img.getIconHeight() - Y_OFFSET_BOTTOM);
		
		this.coordsX = x;
		this.coordsY = y;
	}

	/**
	 * @return the coordsX
	 */
	public int getCoordsX() {
		return coordsX;
	}

	/**
	 * @param coordsX the coordsX to set
	 */
	public void setCoordsX(int coordsX) {
		this.coordsX = coordsX;
	}

	/**
	 * @return the coordsY
	 */
	public int getCoordsY() {
		return coordsY;
	}

	/**
	 * @param coordsY the coordsY to set
	 */
	public void setCoordsY(int coordsY) {
		this.coordsY = coordsY;
	}
	
}
