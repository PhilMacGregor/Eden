package cz.macgregor.eden.core.logic;

import java.awt.Point;

public enum Direction {

	ORIGIN(new Point(0, 0), 0), NORTH(new Point(0, 1), 1), EAST(new Point(1, 0), 2), SOUTH(new Point(0, -1),
	    3), WEST(new Point(-1, 0), 4);

	private final Point	direction;
	private final int		index;

	private Direction(Point direction, int index) {
		this.direction = direction;
		this.index = index;
	}

	/**
	 * @return the direction
	 */
	public Point dir() {
		return direction;
	}

	/**
	 * @return the index
	 */
	public int index() {
		return index;
	}

	public static Direction findByIndex(int index) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].index == index) {
				return values()[i];
			}
		}
		return null;
		
	}

}
