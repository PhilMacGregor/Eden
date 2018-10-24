package cz.macgregor.eden.core.logic.actions.field;

import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.actions.Updates;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Utils;

/**
 * Action called on creating a forest field, adding a random amounts if trees to
 * it.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "createTrees", trigger = TriggerType.CREATE)
public class CreateTreesAction implements FieldAction {
  /** minimum amount to be created. */
  private static final int STARTING_TREES_MIN = 3;
  /** maximum amount to be created. */
  private static final int STARTING_TREES_MAX = 50;

  @Override
  public Updates doAction(Field fld) {
    Updates updates = new Updates();
    for (int i = 0; i < Utils.randomInt(STARTING_TREES_MIN, STARTING_TREES_MAX); i++) {
      updates.addEntities(fld, new EntityWithPosition(EntityType.PINETREE));
    }
    return updates;
  }

}
