package cz.macgregor.eden.core.logic.entities;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.tiles.Field;

public final class EntityUtils {

	public static void moveEntity(Entity ent, Field target) {
		ActionHolder.activateTrigger(TriggerType.BEFORE_MOVE, ent);
		ent.getField().removeEntity(ent);
		target.addEntity(ent);
		ActionHolder.activateTrigger(TriggerType.AFTER_MOVE, ent);
	}
	
}
