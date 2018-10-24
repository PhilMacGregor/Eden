package cz.macgregor.eden.core.logic.actions.field;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.actions.Updates;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.util.Utils;

/**
 * action used for growing forests. The forest can grow each end of turn. New
 * trees may be created at the field. If the field contains high enough amount
 * of trees, they can also be created on adjacent fields. If its neighbor has
 * high enough amount of trees, its type is changed to the forest and it aquires
 * the growForest action too. Also, if the forest is adjacent to desert, it
 * transforms the desert to meadow.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "growForest", trigger = TriggerType.TURN_END)
public class GrowForestAction implements FieldAction {
  /** amount of trees needed for a field to transform to the forest. */
  private static final int TREES_TO_TRANSFORM = 10;
  /** maximum trees that can be generated on a single field. */
  private static final int TREES_MAX = 20;
  /** amount of trees to start spreading to neighbor fields. */
  private static final int TREES_SPREAD = 15;
  /** The probability of growing new tree is 1:NEWBORN_PROBABILITY. */
  private static final int NEWBORN_PROBABILITY = 5;

  /** tile types that can not be targeted by this action. */
  private static final TileType[] FORBIDDEN_TYPES = new TileType[] { TileType.WATER, TileType.DESERT, TileType.ORIGIN, TileType.MOUNTAINS };
  /**
   * if one of those entities is present at a field, the treec can not be
   * generated there.
   */
  private static final EntityType[] OBSTACLES = new EntityType[] { EntityType.ADAM, EntityType.EVE, EntityType.BUILDING };

  @Override
  public Updates doAction(Field fld) {
    GameMap map = fld.getParent();

    int trees = countTrees(fld);
    int doBirth = Utils.randomInt(0, NEWBORN_PROBABILITY);

    if (doBirth >= NEWBORN_PROBABILITY - 1) {
      /*
       * consider all field's neughbours as possible targets.
       */
      List<Point> targets = map.getNeighbourPoints(fld.getCoords(), true);
      List<Field> targetFields = new ArrayList<>();

      /*
       * for every possible target, check if a new tree could grow there.
       */
      Updates updates = new Updates();
      for (Point target : targets) {
        Field targetField = map.get(target).getField();
        if ((!fld.equals(targetField)) && trees < TREES_SPREAD) {
          continue;
        } else if (targetField == null) {
          continue;
        } else if (Arrays.asList(FORBIDDEN_TYPES).contains(targetField.getType())) {
          if (targetField.getType() == TileType.DESERT) {
            updates.changeField(targetField, TileType.MEADOW);
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
        return null;
      }

      /*
       * Now it's sure I want to add a new tree.
       */
      Field growField = targetFields.get(Utils.randomInt(0, targetFields.size()));

      updates.addEntities(growField, new EntityWithPosition(EntityType.PINETREE));

      if (!(growField.getType() == TileType.FOREST) && (countTrees(growField) > TREES_TO_TRANSFORM)) {
        updates.changeField(growField, TileType.FOREST);
      }
      return updates;
    }

    return null;

  }

  /**
   * count trees at a given field.
   * 
   * @param fld
   *          field
   * @return amount of trees
   */
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
