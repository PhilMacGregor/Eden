package cz.macgregor.eden.core.logic.tiles;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;

import cz.macgregor.eden.core.logic.tiles.factory.FieldAction;
import cz.macgregor.eden.core.logic.tiles.factory.ForestAction;
import cz.macgregor.eden.core.logic.tiles.factory.MountainAction;
import cz.macgregor.eden.core.logic.tiles.factory.NullAction;

public class TileFactory {
	private static final InstanceHolder INSTANCE = new InstanceHolder();
	
	private Map<TileType, Class<?>> fieldActions;

	private TileFactory() {
		this.fieldActions = new HashMap<>();
		fieldActions.put(TileType.DESERT, NullAction.class);
		fieldActions.put(TileType.FOREST, ForestAction.class);
		fieldActions.put(TileType.GRASS, NullAction.class);
		fieldActions.put(TileType.MEADOW, NullAction.class);
		fieldActions.put(TileType.MOUNTAINS, MountainAction.class);
		fieldActions.put(TileType.ORIGIN, NullAction.class);
		fieldActions.put(TileType.WATER, NullAction.class);
	}
	
	public static TileFactory getInstance() {
		return INSTANCE.getInstance();
	}
	
	public static Field newField(TileType type) {
		return getInstance().createField(type);
	}
	
	public Field createField(TileType type) {
		Field fld = new Field(type);
		FieldAction fldAction = null;
		
		try {
			Class<?> fldActionType = fieldActions.get(type);
			Constructor<?> actionConstructor = fldActionType.getConstructor(Field.class);
			fldAction = (FieldAction) actionConstructor.newInstance(fld);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		fld.setFieldAction(fldAction);
		
		fld.getFieldAction().createAction();
		return fld;
	}
	
	private static class InstanceHolder {
		private final TileFactory instance;
		
		private InstanceHolder() {
			this.instance = new TileFactory();
		}

		public TileFactory getInstance() {
			return instance;
		}
		
	}
	
}
