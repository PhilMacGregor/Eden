package cz.macgregor.eden.core.logic;

import java.awt.Point;

/**
 * enum holding the vector for the four directions. used for field selection
 * from the map.
 * 
 * @author MacGregor
 *
 */
public enum Direction {
	/** origin point where am I looking up. */
	ORIGIN(new Point(0, 0), 0),
	/** north from origin. */
	NORTH(new Point(0, 1), 1),
	/** east from origin. */
	EAST(new Point(1, 0), 2),
	/** south from origin. */
	SOUTH(new Point(0, -1), 3),
	/** west from origin. */
	WEST(new Point(-1, 0), 4);

	/** direction vector. */
	private final Point direction;
	/** index in direction list. */
	private final int index;

	/**
	 * constructor.
	 * 
	 * @param direction
	 *            direction vector
	 * @param index
	 *            index in direction list
	 */
	private Direction(Point direction, int index) {
		this.direction = direction;
		this.index = index;
	}

	/**
	 * return a point resembling the vector of the direction.
	 * 
	 * @return the direction
	 */
	public Point dir() {
		return direction;
	}

	/**
	 * return the index in the direction list.
	 * 
	 * @return the index
	 */
	public int index() {
		return index;
	}

	/**
	 * find the direction by index.
	 * 
	 * @param index
	 *            index
	 * @return direction
	 */
	public static Direction findByIndex(int index) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].index == index) {
				return values()[i];
			}
		}
		return null;

	}

}
