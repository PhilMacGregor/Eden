package cz.macgregor.eden.core.logic.actions.field;

import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Utils;

@ActionInfo(name = "createMountains", trigger = TriggerType.CREATE)
public class CreateMountainsAction implements FieldAction {

	private static final int	MOUNTS_MIN	= 2;
	private static final int	MOUNTS_MAX	= 5;

	@Override
	public void doAction(Field field) {
		for (int i = 0; i < Utils.randomInt(MOUNTS_MIN, MOUNTS_MAX); i++) {
			field.addEntity(new EntityWithPosition(EntityType.MOUNTAIN));
		}
	}

}
