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

public class GameMap {
	public static final boolean WITH_ORIGIN = true;
	public static final boolean	WITHOUT_ORIGIN	= false;
	
	public static final int	PATTERN_RECTANGLE	= 0;
	public static final int	PATTERN_DIAMOND	= 1;
	
	private MapGenerator mapGenerator;

	private final Map<Point, FieldInfo> map;
	
	private Point minCoords;
	
	private Point maxCoords;

	public GameMap() {
		this.map = new HashMap<>();
	}
	
	public Map<Point, FieldInfo> getMap() {
		return map;
	}

	public FieldInfo get(Point coords) {
		if (map.containsKey(coords)) {
			return map.get(coords);
		} else {
			FieldInfo newFld = new FieldInfo(coords);
			map.put(coords, newFld);
			return newFld;
		}
		
	}

	public Field put(Point coords, Field field) {
		System.out.println("adding " + field.getType() + " to " + coords);
		checkMaxAndMinCoords(coords);
		get(coords).setField(field);
		field.setParent(this);

		ValueIndicators.MAP.update(1);

		return field;
	}
	
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

	public FieldInfo get(int x, int y) {
		return get(new Point(x, y));
	}
	
	public List<FieldInfo> getFieldsByPattern(Pattern pattern, Point from, Point to) {
		List<FieldInfo> ret = new ArrayList<>();
		List<Point> points = pattern.applyPattern(from, to);
		
		for (Point pt : points) {
			FieldInfo fld = get(pt);
			ret.add(fld);
		}
		
		return ret;
		
	}

	public Field put(int x, int y, Field field) {
		Point coords = new Point(x, y);
		return put(coords, field);
	}
	
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

	public Point nextPointByCoords(Point origin, Point offset) {
		return nextPointByCoords(origin, offset.x, offset.y);
	}
	
	public Point nextPointByCoords(Point origin, int x, int y) {
		return new Point(origin.x + x, origin.y + y);
	}

	public Point getMinCoords() {
		return minCoords;
	}

	public Point getMaxCoords() {
		return maxCoords;
	}

	public MapGenerator getMapGenerator() {
		return mapGenerator;
	}

	public void setMapGenerator(MapGenerator mapGenerator) {
		this.mapGenerator = mapGenerator;
	}
	
}
