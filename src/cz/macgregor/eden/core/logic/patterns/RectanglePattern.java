package cz.macgregor.eden.core.logic.patterns;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class RectanglePattern extends Pattern {

	@Override
	public List<Point> applyPattern(Point origin, Point offset, Point range, Point direction) {
		List<Point> points = new ArrayList<>();

		int x = Math.abs(range.x);
		int y = Math.abs(range.y);
		int toX = -x;
		int toY = -y;
		int fromX = x;

		while (x >= toX && y >= toY) {

			points.add(new Point(origin.x + x + offset.x * direction.x, origin.y + y + offset.y * direction.y));

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
