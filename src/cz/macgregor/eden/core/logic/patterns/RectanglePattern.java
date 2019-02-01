package cz.macgregor.eden.core.logic.patterns;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Rctangle-shaped pattern with the center on the origin point.
 * 
 * @author MacGregor
 *
 */
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

      Point pt = new Point(origin.x + x + offset.x * direction.x, origin.y + y + offset.y * direction.y);
      points.add(pt);

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
