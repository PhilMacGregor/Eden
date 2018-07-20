package cz.macgregor.eden.core.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.patterns.DiamondPattern;
import cz.macgregor.eden.core.logic.patterns.Pattern;
import cz.macgregor.eden.core.logic.patterns.RectanglePattern;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.FieldInfo;
import cz.macgregor.eden.grf.components.top.ValueIndicators;

/**
 * the game map.
 * 
 * @author MacGregor
 *
 */
public class GameMap {
	/** constant for getting neighbor field with or without the origin point. */
	public static final boolean WITH_ORIGIN = true;
	/** constant for getting neighbor field with or without the origin point. */
	public static final boolean WITHOUT_ORIGIN = false;

	/** constant for the use of rectangle pattern. */
	public static final int PATTERN_RECTANGLE = 0;
	/** constant for the use of diamond pattern. */
	public static final int PATTERN_DIAMOND = 1;

	/** map generator. */
	private MapGenerator mapGenerator;
	/** the actual map. Contains the field placeholders mapped to the points. */
	private final Map<Point, FieldInfo> map;

	/** minimum coords a field has been created at. */
	private Point minCoords;
	/** maximum coords a field has been created at. */
	private Point maxCoords;

	/**
	 * constructor.
	 */
	public GameMap() {
		this.map = new HashMap<>();
	}

	public Map<Point, FieldInfo> getMap() {
		return map;
	}

	/**
	 * get a field at given coords. If the field was not generated, return an
	 * empty FieldInfo.
	 * 
	 * @param coords
	 *            coords
	 * @return fieldInfo
	 */
	public FieldInfo get(Point coords) {
		if (map.containsKey(coords)) {
			return map.get(coords);
		} else {
			FieldInfo newFld = new FieldInfo(coords);
			map.put(coords, newFld);
			return newFld;
		}

	}

	/**
	 * convenience method for getting the field with int args.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return get(new Point(x, y))
	 */
	public FieldInfo get(int x, int y) {
		return get(new Point(x, y));
	}

	/**
	 * put a field at given coords to the map. Checks for minimum and maximum
	 * coords that have been accessed to avoid some NPE at certain situation.
	 * 
	 * @param coords
	 *            coords
	 * @param field
	 *            field to put
	 * @return the field
	 */
	public Field put(Point coords, Field field) {
		// System.out.println("adding " + field.getType() + " to " + coords);
		checkMaxAndMinCoords(coords);
		get(coords).setField(field);
		field.setParent(this);

		ValueIndicators.MAP.update(1);

		return field;
	}

	/**
	 * convenience method for putting the field with int args.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @param field
	 *            field
	 * @return put(new Point(x, y), field)
	 */
	public Field put(int x, int y, Field field) {
		Point coords = new Point(x, y);
		return put(coords, field);
	}

	/**
	 * sets the minimum and maximum coords from the given coords when needed.
	 * 
	 * @param coords
	 *            coords
	 */
	private void checkMaxAndMinCoords(Point coords) {
		if (this.minCoords == null) {
			this.minCoords = new Point(coords);
		} else {
			if (coords.x < minCoords.x) {
				minCoords.x = coords.x;
			}
			if (coords.y < minCoords.y) {
				minCoords.y = coords.y;
			}
		}

		if (this.maxCoords == null) {
			this.maxCoords = new Point(coords);
		} else {
			if (coords.x > maxCoords.x) {
				maxCoords.x = coords.x;
			}
			if (coords.y > maxCoords.y) {
				maxCoords.y = coords.y;
			}
		}
	}

	/**
	 * creates a list of points applying a given pattern. Then return all fields
	 * from those points.
	 * 
	 * @param pattern
	 *            pattern
	 * @param from
	 *            origin point
	 * @param to
	 *            to
	 * @return found fields
	 */
	public List<FieldInfo> getFieldsByPattern(Pattern pattern, Point from, Point to) {
		List<FieldInfo> ret = new ArrayList<>();
		List<Point> points = pattern.applyPattern(from, to);

		for (Point pt : points) {
			FieldInfo fld = get(pt);
			ret.add(fld);
		}

		return ret;

	}

	/**
	 * for a given point, return all its neighbor points.
	 * 
	 * @param origin
	 *            the point
	 * @param includeOrigin
	 *            if true, also return the origin itself
	 * @return list of points neighboring to the origin
	 */
	public List<Point> getNeighbourPoints(Point origin, boolean includeOrigin) {
		Point[] points = new Point[Direction.values().length];
		Arrays.fill(points, null);

		if (includeOrigin) {
			points[Direction.ORIGIN.index()] = origin;
		}

		points[Direction.NORTH.index()] = nextPointByCoords(origin, Direction.NORTH.dir());
		points[Direction.SOUTH.index()] = nextPointByCoords(origin, Direction.SOUTH.dir());
		points[Direction.EAST.index()] = nextPointByCoords(origin, Direction.EAST.dir());
		points[Direction.WEST.index()] = nextPointByCoords(origin, Direction.WEST.dir());

		return Arrays.asList(points);
	}

	/**
	 * get neighbors by pattern with the origin point as a center.
	 * 
	 * @param origin
	 *            origin
	 * @param includeOrigin
	 *            if true, also return the origin itself
	 * @param range
	 *            range for the pattern
	 * @param pattern
	 *            pattern code
	 * @return neighbors by pattern
	 */
	public List<Point> getNeighbourPoints(Point origin, boolean includeOrigin, int range, int pattern) {
		Pattern patternToUse = null;
		switch (pattern) {
		case PATTERN_DIAMOND:
			patternToUse = new DiamondPattern();
			break;
		case PATTERN_RECTANGLE:
			patternToUse = new RectanglePattern();
			break;
		default:
			return null;
		}

		List<Point> points = patternToUse.applyPattern(origin, new Point(range, range));
		if (!includeOrigin) {
			points.remove(origin);
		}

		return points;
	}

	/**
	 * for a given point, return all its neighbor fields.
	 * 
	 * @param origin
	 *            the point
	 * @param includeOrigin
	 *            if true, also return the origin itself
	 * @return list of fields neighboring to the origin
	 */
	public List<FieldInfo> getNeighbours(Point origin, boolean includeOrigin) {
		List<Point> neighborPoints = getNeighbourPoints(origin, includeOrigin);
		List<FieldInfo> returnList = new LinkedList<>();

		for (int i = 0; i < neighborPoints.size(); i++) {
			Point pt = neighborPoints.get(i);
			if (pt != null) {
				returnList.add(i, this.get(pt));
			}
		}

		return returnList;
	}

	/**
	 * return the origin moved by the offset.
	 * 
	 * @param origin
	 *            origin
	 * @param offset
	 *            offset
	 * @return new Point(origin.x + offset.x, origin.y + offset.y)
	 */
	public Point nextPointByCoords(Point origin, Point offset) {
		return nextPointByCoords(origin, offset.x, offset.y);
	}

	/**
	 * return the origin moved by the offset.
	 * 
	 * @param origin
	 *            origin
	 * @param x
	 *            offset x
	 * @param y
	 *            offset y
	 * @return new Point(origin.x + x, origin.y + y)
	 */
	public Point nextPointByCoords(Point origin, int x, int y) {
		return new Point(origin.x + x, origin.y + y);
	}

	/**
	 * getter.
	 * 
	 * @return map generator
	 */
	public MapGenerator getMapGenerator() {
		return mapGenerator;
	}

	/**
	 * setter.
	 * 
	 * @param mapGenerator
	 *            map generator
	 */
	public void setMapGenerator(MapGenerator mapGenerator) {
		this.mapGenerator = mapGenerator;
	}

}
