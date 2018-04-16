package cz.macgregor.eden.core.logic.patterns;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * pattern selecting a diamond-shaped list of fields.
 * 
 * @author MacGregor
 *
 */
public class DiamondPattern extends Pattern {

	@Override
	public List<Point> applyPattern(Point origin, Point offset, Point range, Point direction) {
		List<Point> points = new ArrayList<>();

		int x = Math.abs(range.x);
		int y = Math.abs(range.y);
		int toX = -x;
		int toY = -y;
		int fromX = x;
		int maxRange = Math.max(x, y);

		while (x >= toX && y >= toY) {

			if (Math.abs(x) + Math.abs(y) <= maxRange) {
				points.add(new Point(x + offset.x * direction.x, y + offset.y * direction.y));
			}

			if (x == toX) {
				x = fromX;
				y--;
			} else {
				x--;
			}
		}

		return points;
	}

}
