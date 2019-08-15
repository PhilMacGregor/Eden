package cz.macgregor.eden.core.logic.entities;

import javax.swing.ImageIcon;

import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

/**
 * entity with a determined position in the field.
 * 
 * @author MacGregor
 *
 */
public class EntityWithPosition extends Entity {
	/**
	 * top offset for the entities. No entities will be displayed higher than
	 * this.
	 */
	private static final int Y_OFFSET_TOP = -15;
	/**
	 * bottom offset for the entities. No entities will be displayed lower than
	 * this.
	 */
	private static final int Y_OFFSET_BOTTOM = 0;

	/** x position of the entity relative to the field. */
	private int coordsX;
	/** y position of the entity relative to the field. */
	private int coordsY;

	/**
	 * constructor.
	 * 
	 * @param type
	 *            type
	 * @param x
	 *            x
	 * @param y
	 *            y
	 */
	public EntityWithPosition(EntityType type, int x, int y) {
		super(type);
		this.coordsX = x;
		this.coordsY = y;
	}

	/**
	 * constructor setting a random position.
	 * 
	 * @param type
	 *            type
	 */
	public EntityWithPosition(EntityType type) {
		super(type);
		ImageIcon img = type.getImage();
		int x = Utils.randomInt(0, Const.TILE_WIDTH - img.getIconWidth());
		int y = Utils.randomInt(Y_OFFSET_TOP, Const.TILE_HEIGHT - img.getIconHeight() - Y_OFFSET_BOTTOM);

		this.coordsX = x;
		this.coordsY = y;
	}

	/**
	 * getter.
	 * 
	 * @return the coordsX
	 */
	public int getCoordsX() {
		return coordsX;
	}

	/**
	 * setter.
	 * 
	 * @param coordsX
	 *            the coordsX to set
	 */
	public void setCoordsX(int coordsX) {
		this.coordsX = coordsX;
	}

	/**
	 * getter.
	 * 
	 * @return the coordsY
	 */
	public int getCoordsY() {
		return coordsY;
	}

	/**
	 * setter.
	 * 
	 * @param coordsY
	 *            the coordsY to set
	 */
	public void setCoordsY(int coordsY) {
		this.coordsY = coordsY;
	}

}
