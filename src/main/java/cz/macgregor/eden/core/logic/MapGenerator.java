package cz.macgregor.eden.core.logic;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cz.macgregor.eden.core.logic.actions.Identifier;
import cz.macgregor.eden.core.logic.patterns.Pattern;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.FieldInfo;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

/**
 * class used for procedural generating of the map.
 * 
 * @author MacGregor
 *
 */
public class MapGenerator {
	/**
	 * The map generator determines probability to generate a new field of a
	 * random tile type according to Const.MAP_GENERATION_DISTORTION_RATE. This
	 * is a two size array where the first number determines a maximum index for
	 * random field to generate.
	 */
	private static final int RANDOM_FIELD_INDEX = 0;
	/**
	 * the second index from Const.MAP_GENERATION_DISTORTION_RATE. Determines a
	 * probability of just copying a neighbor field rather than creating a new
	 * random one.
	 */
	private static final int COPY_FIELD_INDEX = 1;

	/** game map to work with. */
	private final GameMap map;
	/**
	 * current direction to determine next field to be generated in initial map
	 * generation.
	 */
	private Direction direction;

	/**
	 * constructor.
	 * 
	 * @param map
	 *            map
	 */
	public MapGenerator(GameMap map) {
		this.map = map;
		map.setMapGenerator(this);
		this.direction = Direction.NORTH;
	}

	/**
	 * find a next direction (turn right usually).
	 * 
	 * @param dir
	 *            current direction
	 * @return new direction
	 */
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

	/**
	 * generate certain number of new fields going spiral way from the origin.
	 * 
	 * @param origin
	 *            origin point
	 * @param nrOfSteps
	 *            number of fields to be checked/generated
	 * @return origin point
	 */
	public Point generate(Point origin, int nrOfSteps) {
		while (nrOfSteps > 0) {
			origin = generate(origin);
			nrOfSteps--;
		}

		return origin;
	}

	/**
	 * generate a single new field. Then return next point to be used for the
	 * generation.
	 * 
	 * @param origin
	 *            origin point
	 * @return next point to be used for generation
	 */
	public Point generate(Point origin) {
		Field field = map.get(origin).getField();
		if (field == null) {
			map.put(origin, newField(origin));
		}

		return nextEmptyPoint(origin);
	}

	/**
	 * find a next point where no field has been generated yet based on the
	 * direction.
	 * 
	 * @param pt
	 *            point
	 * @return next empty point
	 */
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

	/**
	 * fill a given pattern with new randomly generated fields.
	 * 
	 * @param pattern
	 *            pattern
	 * @param origin
	 *            origin
	 * @param range
	 *            range
	 */
	public void fillPattern(Pattern pattern, Point origin, Point range) {
		fillPattern(pattern, origin, range, null);
	}

	/**
	 * fill a pattern with a field of given type.
	 * 
	 * @param pattern
	 *            pattern
	 * @param origin
	 *            origin
	 * @param range
	 *            range
	 * @param field
	 *            field which type is going to be used (if null, generate
	 *            random)
	 */
	public void fillPattern(Pattern pattern, Point origin, Point range, Field field) {
		List<Point> pointsToFill = pattern.applyPattern(origin, range);

		for (Point pt : pointsToFill) {
			if (map.get(pt).getField() == null) {
				map.put(pt, field != null ? MapObjectFactory.createField(field.getType()) : newField(pt));
			}
		}
	}

	/**
	 * create a new random field based on neighbours of the given point.
	 * 
	 * @param pointToSearch
	 *            point to lookup
	 * @return generated field
	 */
	private Field newField(Point pointToSearch) {

		List<Identifier<Field>> candidates = new ArrayList<>();

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

		return MapObjectFactory.createField(candidates.get(Utils.randomInt(0, candidates.size())));
	}

}
