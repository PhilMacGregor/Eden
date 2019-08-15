package cz.macgregor.eden.core.logic.actions.field;

import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.actions.Updates;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Utils;

/**
 * Action called on creating a mountain field, adding a random amounts if
 * mountains to it.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "createMountains", trigger = TriggerType.CREATE)
public class CreateMountainsAction implements FieldAction {

  /** minimum amount to be created. */
  private static final int MOUNTS_MIN = 2;
  /** maximum amount to be created. */
  private static final int MOUNTS_MAX = 5;

  @Override
  public Updates doAction(Field field) {
    Updates updates = new Updates();
    for (int i = 0; i < Utils.randomInt(MOUNTS_MIN, MOUNTS_MAX); i++) {
      updates.addEntities(field, new EntityWithPosition(EntityType.MOUNTAIN));
    }
    return updates;
  }

}
