package cz.macgregor.eden.core.logic.tiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Subscriber;

public class TileFactory {
	private static final InstanceHolder INSTANCE = new InstanceHolder();
	
	private final Map<TileType, ActionEntry[]> defaultActions;

	private TileFactory() throws IllegalArgumentException, IllegalAccessException {
		this.defaultActions = new HashMap<>();

		Class<?> clazz = TileType.class;
		for (java.lang.reflect.Field fld : clazz.getFields()) {
			Subscriber subscriberInfo = fld.getAnnotation(Subscriber.class);

			if (fld.getType().equals(TileType.class) && subscriberInfo != null) {

				String[] actions = subscriberInfo.value();
				List<ActionEntry> entries = new ArrayList<>();
				for (String action : actions) {
					entries.add(ActionHolder.byName(action));
				}

				TileType eType = (TileType) fld.get(null);
				defaultActions.put(eType, entries.toArray(new ActionEntry[entries.size()]));
			}
		}
	}
	
	public static TileFactory getInstance() {
		return INSTANCE.getInstance();
	}
	
	public static Field newField(TileType type) {
		return getInstance().createField(type);
	}
	
	public Field createField(TileType type) {
		Field fld = new Field(type);
		addDefaultActions(fld);
		ActionHolder.activateTrigger(TriggerType.CREATE, fld);

		return fld;
	}
	
	private void addDefaultActions(Field fld) {
		ActionEntry[] actions = defaultActions.get(fld.getType());
		if (actions != null) {
			for (ActionEntry holder : actions) {
				fld.addAction(holder);
			}
		}
	}
	
	private static class InstanceHolder {
		private final TileFactory instance;
		
		private InstanceHolder() {
			try {
				this.instance = new TileFactory();
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException("failed to initialize EntityFactory.", e);
			}
		}

		public TileFactory getInstance() {
			return instance;
		}
		
	}
	
}
