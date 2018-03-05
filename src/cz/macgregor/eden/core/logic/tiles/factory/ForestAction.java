package cz.macgregor.eden.core.logic.tiles.factory;

import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Utils;

public class ForestAction extends FieldAction {
	private static final int STARTING_TREES_MIN = 3;
	private static final int STARTING_TREES_MAX = 50;

	public ForestAction(Field field) {
		super(field);
	}

	@Override
	public void createAction() {
		for (int i = 0; i < Utils.randomInt(STARTING_TREES_MIN, STARTING_TREES_MAX); i++) {
			field.addEntity(new EntityWithPosition(EntityType.PINETREE));
		}
	}

	@Override
	public void turnAction() {
		// TODO Auto-generated method stub
	}

}
