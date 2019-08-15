package cz.macgregor.eden.core.logic.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import cz.macgregor.eden.core.logic.actions.Action;
import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;
import cz.macgregor.eden.core.logic.actions.HasAction;
import cz.macgregor.eden.core.logic.actions.Identifier;
import cz.macgregor.eden.core.logic.tiles.Field;

/**
 * instance of this class represents a single entity.
 * 
 * @author MacGregor
 *
 */
public class Entity extends HasAction {
  /** field that the entity is located at. */
  private Field field;
  /** entity properties. */
  private final Map<String, Object> entityProps;

  /**
   * constructor.
   * 
   * @param type
   *          entity type (works only with EntityType)
   */
  public Entity(Identifier<Entity> type) {
    super(type);
    this.entityProps = new HashMap<>();
  }

  /**
   * set entity property.
   * 
   * @param key
   *          key
   * @param value
   *          value
   */
  public void setProp(String key, Object value) {
    entityProps.put(key, value);
  }

  public Object getProp(String key) {
    return entityProps.get(key);
  }

  public String getPropAsString(String key) {
    return Objects.toString(getProp(key), "");
  }

  /**
   * check if the entity has a property.
   * 
   * @param key
   *          key
   * @return has property
   */
  public boolean hasProp(String key) {
    return entityProps.containsKey(key);
  }

  /**
   * unsubscribe this entity from an action.
   * 
   * @param action
   *          action
   */
  public void removeAction(ActionEntry action) {
    this.actions.remove(action.getAction());
    action.removeSubscriber(this);
  }

  /**
   * remove the entity from the map and unregister all its actions.
   */
  public void kill() {
    if (this.field != null) {
      this.field.removeEntity(this);
    }
    for (Action<HasAction> action : this.actions) {
      ActionHolder.byClass(action.getClass()).removeSubscriber(this);
    }
    this.actions.clear();
  }

  /**
   * getter.
   * 
   * @return field
   */
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
