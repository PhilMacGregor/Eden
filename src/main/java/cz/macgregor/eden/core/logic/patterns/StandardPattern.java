package cz.macgregor.eden.core.logic.patterns;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * The rectangle-shaped pattern with the origin in its top-left corner.
 * 
 * @author MacGregor
 *
 */
public class StandardPattern extends Pattern {

	@Override
	public List<Point> applyPattern(Point from, Point offset, Point to, Point direction) {
		List<Point> points = new ArrayList<>();

		int indexX = from.x;
		int indexY = from.y;

		while (indexX <= to.x && indexY <= to.y) {

			points.add(new Point(indexX, indexY));

			if (indexX == to.x) {
				indexX = from.x;
				indexY++;
			} else {
				indexX++;
			}
		}

		return points;
	}

}
