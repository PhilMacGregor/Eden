package cz.macgregor.eden.core.logic;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;
import cz.macgregor.eden.core.logic.actions.HasAction;
import cz.macgregor.eden.core.logic.actions.Identifier;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.tiles.Field;

public class MapObjectFactory {
	private static MapObjectFactory instance;

	private final Creator<Field>	fieldCreator;
	private final Creator<Entity>	entityCreator;

	public static Field createField(Identifier<Field> type) {
		return getInstance().fieldCreator.create(type);
	}

	public static Entity createEntity(Identifier<Entity> type) {
		Entity prototype = getInstance().entityCreator.create(type);

		return prototype;
	}

	private static HasAction createNew(HasAction obj) {
		addDefaultActions(obj);
		ActionHolder.activateTrigger(TriggerType.CREATE, obj);
		return obj;
	}

	public static void addDefaultActions(HasAction obj) {
		ActionEntry[] actions = ActionHolder.getDefaultActions(obj.getType());
		if (actions != null) {
			for (ActionEntry holder : actions) {
				obj.addAction(holder);
			}
		}
	}

	private static MapObjectFactory getInstance() {
		if (instance == null) {
			instance = new MapObjectFactory();
		}

		return instance;
	}

	private MapObjectFactory() {
		this.fieldCreator = (type) -> {
			return (Field) createNew(new Field(type));
		};
		this.entityCreator = (type) -> {
			return (Entity) createNew(new Entity(type));
		};
	}

	private interface Creator<T extends HasAction> {
		T create(Identifier<T> type);
	}
}
