package cz.macgregor.eden.core.logic.entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.actions.Action;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;
import cz.macgregor.eden.core.logic.tiles.Field;

public class Entity {
	private final EntityType type;
	
	private Field field;
	
	private Map<String, String> entityProps;
	
	private List<Action> actions;

	public Entity(EntityType type) {
		this.type = type;
		this.entityProps = new HashMap<>();
		this.setActions(new ArrayList<>());
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

	public List<Action> getActions() {
		return actions;
	}

	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
	public void addAction(ActionEntry action) {
		this.actions.add(action.getAction());
		action.addSubscriber(this);
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

	public EntityType getType() {
		return type;
	}
	
}
