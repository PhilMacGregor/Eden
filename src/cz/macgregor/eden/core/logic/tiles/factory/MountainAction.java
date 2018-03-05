package cz.macgregor.eden.core.logic.tiles.factory;

import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Utils;

public class MountainAction extends FieldAction {
	
	public MountainAction(Field field) {
		super(field);
	}

	private static final int MOUNTS_MIN = 2;
	private static final int MOUNTS_MAX = 5;
	
	@Override
	public void createAction() {
		for (int i = 0; i < Utils.randomInt(MOUNTS_MIN, MOUNTS_MAX); i++) {
			field.addEntity(new EntityWithPosition(EntityType.MOUNTAIN));
		}
	}

	@Override
	public void turnAction() {
		// TODO Auto-generated method stub

	}

}
