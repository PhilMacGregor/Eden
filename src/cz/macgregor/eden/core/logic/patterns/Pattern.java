package cz.macgregor.eden.core.logic.patterns;

import java.awt.Point;
import java.util.List;

public abstract class Pattern {

	public List<Point> applyPattern(Point origin, Point range) {
		return applyPattern(origin, new Point(0, 0), range, new Point(1, 1));
	}

	public abstract List<Point> applyPattern(Point origin, Point offset, Point range, Point direction);
}
