package cz.macgregor.eden.core.logic.entities;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;

public class EntityFactory {
	private static EntityFactory instance;

	private Map<EntityType, ActionEntry[]> defaultActions;

	public static Entity newEntity(EntityType type) {
		return getInstance().createNew(type);
	}

	private static EntityFactory getInstance() {
		if (instance == null) {
			try {
				instance = new EntityFactory();
			} catch (Exception e) {
				throw new RuntimeException("failed to initialize EntityFactory.", e);
			}

		}
		return instance;
	}

	private Entity createNew(EntityType type) {
		Entity ent = new Entity(type);

		ActionEntry[] actions = defaultActions.get(type);
		if (actions != null) {
			for (ActionEntry holder : actions) {
				ent.addAction(holder);
			}
		}

		return ent;
	}

	private EntityFactory() throws IllegalArgumentException, IllegalAccessException {
		this.defaultActions = new HashMap<>();

		Class<?> clazz = EntityType.class;
		for (Field fld : clazz.getFields()) {
			Subscriber subscriberInfo = fld.getAnnotation(Subscriber.class);

			if (fld.getType().equals(EntityType.class) && subscriberInfo != null) {

				String[] actions = subscriberInfo.value();
				List<ActionEntry> entries = new ArrayList<>();
				for (String action : actions) {
					entries.add(ActionHolder.byName(action));
				}

				EntityType eType = (EntityType) fld.get(null);
				defaultActions.put(eType, (ActionEntry[]) entries.toArray(new ActionEntry[entries.size()]));
			}
		}

	}

}
