package cz.macgregor.eden.core.logic.actions.entity;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityUtils;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.Utils;

/**
 * at the end of each turn, every subscriber entity moves randomly on random
 * neighbor field.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "randomMove", trigger = TriggerType.TURN_END)
public class RandomMoveAction implements EntityAction {
	/** the entity can not be moved to these tile types. */
	private static final TileType[] FORBIDDEN_TYPES = new TileType[] { TileType.FOREST, TileType.MOUNTAINS,
			TileType.WATER };

	@Override
	public void doAction(Entity ent) {

		GameMap map = ent.getField().getParent();
		Point pos = ent.getField().getCoords();

		List<Point> neighbours = new ArrayList<>();
		for (Point pt : map.getNeighbourPoints(pos, false)) {
			if (pt == null) {
				continue;
			}

			boolean isValidNeighbour = true;
			Field f = map.get(pt).getField();

			if (f != null) {
				if (Arrays.asList(FORBIDDEN_TYPES).contains(f.getType())) {
					isValidNeighbour = false;
				}
			}

			if (isValidNeighbour) {
				neighbours.add(pt);
			}

		}

		if (neighbours.isEmpty()) {
			System.out.printf("The human is stuck at [%d; %d]: %s\n", pos.x, pos.y, ent.getType());
			return;
		}

		Point moveTo = neighbours.get(Utils.randomInt(0, neighbours.size()));

		if (map.get(moveTo).getField() == null) {
			map.getMapGenerator().generate(moveTo);
		}

		EntityUtils.moveEntity(ent, map.get(moveTo).getField());
	}

}
