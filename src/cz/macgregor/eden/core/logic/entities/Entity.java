package cz.macgregor.eden.core.logic.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.actions.Action;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;
import cz.macgregor.eden.core.logic.actions.HasAction;
import cz.macgregor.eden.core.logic.actions.Identifier;
import cz.macgregor.eden.core.logic.tiles.Field;

public class Entity extends HasAction {
	private Field field;
	
	private final Map<String, String> entityProps;
	
	private List<Action<HasAction>> actions;

	public Entity(Identifier<Entity> type) {
		super(type);
		this.entityProps = new HashMap<>();
	}
	
	public void setProp(String key, String value) {
		entityProps.put(key, value);
	}
	
	public String getProp(String key) {
		return entityProps.get(key);
	}
	
	public boolean hasProp(String key) {
		return entityProps.containsKey(key);
	}
	
	public void removeAction(ActionEntry action) {
		this.actions.remove(action.getAction());
		action.removeSubscriber(this);
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
	}
	
	@Override
	public EntityType getType() {
		return (EntityType) super.getType();
	}

	@Override
	public String toString() {
		return getType() + ": " + super.toString();
	}

}
