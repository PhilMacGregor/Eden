package cz.macgregor.eden.core.logic.patterns;

import java.awt.Point;
import java.util.List;

/**
 * class using for selection multiple field in the map. Each child of this class
 * should return a list of fields by a specified pattern.
 * 
 * @author MacGregor
 *
 */
public abstract class Pattern {

	/**
	 * simplified method to apply the pattern without offset and in default
	 * direction.
	 * 
	 * @param origin
	 *            origin point
	 * @param range
	 *            size to be selected
	 * @return fields selected
	 */
	public List<Point> applyPattern(Point origin, Point range) {
		return applyPattern(origin, new Point(0, 0), range, new Point(1, 1));
	}

	/**
	 * method to select the fields by pattern.
	 * 
	 * @param origin
	 *            origin point
	 * @param offset
	 *            offset
	 * @param range
	 *            size to be selected
	 * @param direction
	 *            direction
	 * @return fields selected
	 */
	public abstract List<Point> applyPattern(Point origin, Point offset, Point range, Point direction);
}
