package cz.macgregor.eden.core.logic.tiles;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.tiles.factory.FieldAction;

/**
 * class representing a single field from the game map.
 * 
 * @author MacGregor
 *
 */
public class Field {
	
	private GameMap parent;

	/** type of the field. */
	private TileType type;
	
	/**
	 * is visible (do show this field on the map. Otherwise, show only an
	 * "invisible" tile.
	 */
	private boolean visible;
	/** is selected (for example by a listener). */
	private boolean selected;
	/** entities with positions on the field to be displayed on. */
	private List<Entity> entities;
	/**
	 * props of the field used if any of the props is different from default
	 * TileType.props.
	 */
	private TileProps props;

	/**
	 * actions on field creation and turn end.
	 */
	private FieldAction fieldAction;
	
	/**
	 * position where the field is located at.
	 */
	private FieldInfo position;

	/**
	 * constructor.
	 * 
	 * @param type
	 *            field type
	 */
	public Field(TileType type) {
		this.type = type;
		this.visible = true;
		this.selected = false;
		this.entities = new ArrayList<>();
	}

	/**
	 * getter.
	 * 
	 * @return field type
	 */
	public TileType getType() {
		return type;
	}

	/**
	 * setter.
	 * 
	 * @param type
	 *            field type
	 */
	public void setType(TileType type) {
		this.type = type;
	}

	/**
	 * getter.
	 * 
	 * @return field coords
	 */
	public Point getCoords() {
		return position.getPosition();
	}

	/**
	 * getter.
	 * 
	 * @return map with entities
	 */
	public List<Entity> getEntities() {
		return entities;
	}

	/**
	 * setter.
	 * 
	 * @param entities
	 *            map with entities
	 */
	public void setEntities(List<Entity> entities) {
		this.entities = entities;
	}

	/**
	 * @return true if the field contains any entity, false otherwise
	 * @see java.util.List#isEmpty()
	 */
	public boolean hasEntites() {
		return !this.entities.isEmpty();
	}

	/**
	 * add an entity to the field.
	 * 
	 * @param ent
	 *            entity
	 * @return entities.add(ent);
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean addEntity(Entity ent) {
		ent.setField(this);
		return this.entities.add(ent);
	}
	
	public void removeEntity(Entity ent) {
		if (this.entities.contains(ent)) {
			ent.setField(null);
			this.entities.remove(ent);
		}
		
	}

	/**
	 * add a list of entities to the field.
	 * 
	 * @param ents
	 *            entites
	 * @return entities.addAll(ents);
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addEntities(Collection<? extends Entity> ents) {
		for (Entity ent : ents) {
			ent.setField(this);
		}
		return this.entities.addAll(ents);
	}
	
	public List<Entity> getMovableEntities() {
		List<Entity> ents = new ArrayList<>();
		for (Entity ent : this.entities) {
			if (ent.getType().isMovable()) {
				ents.add(ent);
			}
		}
		
		return ents;
	}

	/**
	 * getter.
	 * 
	 * @return is the field visible
	 */
	public boolean isVisible() {
		return visible;
	}

	/**
	 * setter.
	 * 
	 * @return should the field be visible
	 */
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * @param selected
	 *            the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * @return the props
	 */
	public TileProps getProps() {
		return props;
	}

	/**
	 * @param props
	 *            the props to set
	 */
	public void setProps(TileProps props) {
		this.props = props;
	}

	/**
	 * @return field action
	 */
	public FieldAction getFieldAction() {
		return fieldAction;
	}

	/**
	 * @param fieldAction field action
	 */
	public void setFieldAction(FieldAction fieldAction) {
		this.fieldAction = fieldAction;
	}
	
	/**
	 * @return the position
	 */
	public FieldInfo getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(FieldInfo position) {
		this.position = position;
	}

	public GameMap getParent() {
		return parent;
	}

	public void setParent(GameMap parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Field [type=" + type + ", coords=" + position.getPosition() + ", visible=" + visible + ", selected=" + selected
				+ ", entities=" + entities + ", props=" + props + "]";
	}

}