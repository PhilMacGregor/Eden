package cz.macgregor.eden.core.logic.entities;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.tiles.Field;

/**
 * utility class providing other functions for entities.
 * 
 * @author MacGregor
 *
 */
public final class EntityUtils {

	/**
	 * move the entity. Can also move the entities with moveble = false.
	 * 
	 * @param ent
	 *            entity
	 * @param target
	 *            target field
	 */
	public static void moveEntity(Entity ent, Field target) {
		ActionHolder.activateTrigger(TriggerType.BEFORE_MOVE, ent);
		ent.getField().removeEntity(ent);
		target.addEntity(ent);
		ActionHolder.activateTrigger(TriggerType.AFTER_MOVE, ent);
	}

}
