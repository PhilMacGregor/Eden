package cz.macgregor.eden.core.logic.actions;

import java.util.HashSet;
import java.util.Set;

import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;

/**
 * the instances of this class can use actions if their types.
 * 
 * @author MacGregor
 *
 */
public abstract class HasAction {
  /** item type (TileType, EntityType). */
  private Identifier<? extends HasAction> type;
  /** actions this item is subscribed to. */
  protected Set<Action<HasAction>> actions;

  /**
   * constructor.
   * 
   * @param type
   *          type
   */
  protected HasAction(Identifier<? extends HasAction> type) {
    this.type = type;
    this.actions = new HashSet<>();
  }

  public Identifier<? extends HasAction> getType() {
    return this.type;
  }

  public void setType(Identifier<? extends HasAction> type) {
    this.type = type;
  }

  public Set<Action<HasAction>> getActions() {
    return actions;
  }

  public void setActions(Set<Action<HasAction>> actions) {
    this.actions = actions;
  }

  /**
   * add an action. Also, subscribe this to the action.
   * 
   * @param action
   *          action to add
   */
  public void addAction(ActionEntry action) {
    this.actions.add(action.getAction());
    action.addSubscriber(this);
  }

  public void removeAction(ActionEntry action) {
    this.actions.remove(action.getAction());
    action.removeSubscriber(this);
  }

  public void removeAllActions() {
    this.actions.forEach((action) -> this.removeAction(ActionHolder.byClass(action.getClass())));
    this.actions.clear();
  }

}
