package cz.macgregor.eden.core.logic.tiles.factory;

import cz.macgregor.eden.core.logic.tiles.Field;

public class NullAction extends FieldAction {

	public NullAction(Field field) {
		super(field);
	}

	@Override
	public void createAction() {
		// do nothing

	}

	@Override
	public void turnAction() {
		// do nothing
	}

}
