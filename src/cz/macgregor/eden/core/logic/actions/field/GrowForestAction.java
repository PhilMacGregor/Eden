package cz.macgregor.eden.core.logic.actions.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.FieldAction;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.Utils;

@ActionInfo(name = "growForest", trigger = TriggerType.TURN_END)
public class GrowForestAction implements FieldAction {
	private static final int TREES_TO_TRANSFORM = 10;
	private static final int TREES_MAX = 20;
	private static final int TREES_SPREAD = 15;
	private static final int NEWBORN_PROBABILITY = 5;

	private static final TileType[] FORBIDDEN_TYPES = new TileType[]{TileType.WATER, TileType.DESERT, TileType.ORIGIN, TileType.MOUNTAINS};
	private static final EntityType[] OBSTACLES = new EntityType[]{EntityType.ADAM, EntityType.EVE, EntityType.BUILDING};
	
	@Override
	public void doAction(Field fld) {
		GameMap map = fld.getParent();
		
		int trees = countTrees(fld);
		int doBirth = Utils.randomInt(0, NEWBORN_PROBABILITY);
		
		if (doBirth >= NEWBORN_PROBABILITY - 1) {
			List<Point> targets = map.getNeighbourPoints(fld.getCoords(), true);
			List<Field> targetFields = new ArrayList<>();
			
			for (Point target : targets) {
				Field targetField = map.get(target).getField();
				if ((!fld.equals(targetField)) && trees < TREES_SPREAD) {
					continue;
				} else if (targetField == null) {
					continue;
				} else if (Arrays.asList(FORBIDDEN_TYPES).contains(targetField.getType())) {
					if (targetField.getType() == TileType.DESERT) {
						targetField.setType(TileType.MEADOW);
					}
					continue;
				} else if (countTrees(targetField) > TREES_MAX) {
					continue;
				} else {
					boolean hasObstacle = false;
					for (Entity ent : targetField.getEntities()) {
						if (Arrays.asList(OBSTACLES).contains(ent.getType())) {
							hasObstacle = true;
						}
					}
					
					if (hasObstacle) {
						continue;
					}
				}
				 
				targetFields.add(targetField);
			}
			
			if (targetFields.isEmpty()) {
				return;
			}
			
			Field growField = targetFields.get(Utils.randomInt(0, targetFields.size()));
			growField.addEntity(new EntityWithPosition(EntityType.PINETREE));
			if (!(growField.getType() == TileType.FOREST) && (countTrees(growField) > TREES_TO_TRANSFORM)) {
				growField.setType(TileType.FOREST);
				growField.addAction(ActionHolder.byName("growForest"));
			}
		}
		
	}
	
	private int countTrees(Field fld) {
		int trees = 0;

		for (Entity ent : fld.getEntities()) {
			if (ent.getType() == EntityType.PINETREE) {
				trees++;
			}
		}
		
		return trees;
	}

}
