package cz.macgregor.eden.core.logic.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.TileType;

public class Updates {
  private Map<Field, List<Entity>> entitiesToAdd;

  private List<Entity> entitiesToKill;

  private Map<Field, TileType> fieldsToChange;

  private Map<HasAction, List<String>> actionsToAdd;

  private Map<HasAction, List<String>> actionsToRemove;

  /**
   * @param entitiesToAdd
   * @param entitiesToKill
   * @param fieldsToChange
   * @param actionsToAdd
   * @param actionsToRemove
   */
  public Updates() {
    this.entitiesToAdd = new HashMap<>();
    this.entitiesToKill = new ArrayList<>();
    this.fieldsToChange = new HashMap<>();
    this.actionsToAdd = new HashMap<>();
    this.actionsToRemove = new HashMap<>();
  }

  public void addEntities(Field field, Entity... entities) {
    List<Entity> ents = entitiesToAdd.get(field);
    if (ents == null) {
      entitiesToAdd.put(field, new ArrayList<>());
    }
    entitiesToAdd.get(field).addAll(Arrays.asList(entities));
  }

  public void killEntities(Entity... entities) {
    entitiesToKill.addAll(Arrays.asList(entities));
  }

  public void changeField(Field oldField, TileType newField) {
    fieldsToChange.put(oldField, newField);
  }

  public void addActions(HasAction obj, String... actions) {
    List<String> acts = actionsToAdd.get(obj);
    if (acts == null) {
      actionsToAdd.put(obj, new ArrayList<>());
    }
    actionsToAdd.get(obj).addAll(Arrays.asList(actions));
  }

  public void removeActions(HasAction obj, String... actions) {
    List<String> acts = actionsToRemove.get(obj);
    if (acts == null) {
      actionsToRemove.put(obj, new ArrayList<>());
    }
    actionsToRemove.get(obj).addAll(Arrays.asList(actions));
  }

  /**
   * @return the entitiesToAdd
   */
  public Map<Field, List<Entity>> getEntitiesToAdd() {
    return this.entitiesToAdd;
  }

  /**
   * @return the entitiesToKill
   */
  public List<Entity> getEntitiesToKill() {
    return this.entitiesToKill;
  }

  /**
   * @return the fieldsToChange
   */
  public Map<Field, TileType> getFieldsToChange() {
    return this.fieldsToChange;
  }

  /**
   * @return the actionsToAdd
   */
  public Map<HasAction, List<String>> getActionsToAdd() {
    return this.actionsToAdd;
  }

  /**
   * @return the actionsToRemove
   */
  public Map<HasAction, List<String>> getActionsToRemove() {
    return this.actionsToRemove;
  }

}
