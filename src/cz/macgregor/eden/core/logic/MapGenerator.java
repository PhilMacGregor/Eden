package cz.macgregor.eden.core.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cz.macgregor.eden.core.logic.patterns.Pattern;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.FieldInfo;
import cz.macgregor.eden.core.logic.tiles.TileFactory;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

public class MapGenerator {
	private static final int	RANDOM_FIELD_INDEX	= 0;
	private static final int	COPY_FIELD_INDEX	= 1;

	private final GameMap map;

	private Direction direction;

	public MapGenerator(GameMap map) {
		this.map = map;
		map.setMapGenerator(this);
		this.direction = Direction.NORTH;
	}

	private Direction nextDirection(Direction dir) {

		if (dir == Direction.ORIGIN) {
			return dir;
		}

		int index = dir.index();
		if (index == Direction.WEST.index()) {
			index = Direction.NORTH.index();
		} else {
			index++;
		}

		return Direction.findByIndex(index);
	}

	public Point generate(Point origin, int nrOfSteps) {
		while (nrOfSteps > 0) {
			origin = generate(origin);
			nrOfSteps--;
		}

		return origin;
	}

	public Point generate(Point origin) {
		Field field = map.get(origin).getField();
		if (field == null) {
			map.put(origin, newField(origin));
		}

		return nextEmptyPoint(origin);
	}

	private Point nextEmptyPoint(Point pt) {
		Direction nextDir = nextDirection(direction);
		Point pointInNextDir = map.nextPointByCoords(pt, nextDir.dir());
		
		Point ret;
		
		Field fieldNextTo = map.get(pointInNextDir).getField();
		
		if (fieldNextTo == null) {
			// if nextDirection is empty, switch direction and return the point.
			this.direction = nextDir;
			ret = pointInNextDir;

		} else {
			// else proceed in old direction
			ret = map.nextPointByCoords(pt, direction.dir());

		}
		
		return ret;

	}

	public void fillPattern(Pattern pattern, Point origin, Point range) {
		fillPattern(pattern, origin, range, null);
	}

	public void fillPattern(Pattern pattern, Point origin, Point range, Field field) {
		List<Point> pointsToFill = pattern.applyPattern(origin, range);

		for (Point pt : pointsToFill) {
			if (map.get(pt).getField() == null) {
				map.put(pt, field != null ? TileFactory.newField(field.getType()) : newField(pt));
			}
		}
	}

	private Field newField(Point pointToSearch) {

		List<TileType> candidates = new ArrayList<>();

		List<FieldInfo> neighbours = map.getNeighbours(pointToSearch, GameMap.WITH_ORIGIN);
		for (FieldInfo fld : neighbours) {
			if (fld.getField() == null || fld.getField().getType() == TileType.ORIGIN) {

				for (int i = 0; i < Const.MAP_GENERATION_DISTORTION_RATE[RANDOM_FIELD_INDEX]; i++) {
					candidates.add(TileType.randomType());
				}

			} else {

				for (int i = 0; i < Const.MAP_GENERATION_DISTORTION_RATE[COPY_FIELD_INDEX]; i++) {
					candidates.add(fld.getField().getType());
				}

			}
		}

		return TileFactory.newField(candidates.get(Utils.randomInt(0, candidates.size())));
	}

}
