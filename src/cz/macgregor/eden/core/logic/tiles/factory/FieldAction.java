package cz.macgregor.eden.core.logic.tiles.factory;

import cz.macgregor.eden.core.logic.tiles.Field;

public abstract class FieldAction {
	
	protected Field field;

	public FieldAction(Field field) {
		this.field = field;
	}

	public abstract void createAction();
	
	public abstract void turnAction();
	
}
