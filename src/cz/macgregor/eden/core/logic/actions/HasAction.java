package cz.macgregor.eden.core.logic.actions;

import java.util.ArrayList;
import java.util.List;

import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;

/**
 * the instances of this class can use actions if their types.
 * 
 * @author MacGregor
 *
 */
public abstract class HasAction {
	private Identifier<? extends HasAction> type;

	protected List<Action<HasAction>> actions;

	/**
	 * constructor.
	 * 
	 * @param type
	 *            type
	 */
	protected HasAction(Identifier<? extends HasAction> type) {
		this.type = type;
		this.actions = new ArrayList<>();
	}

	public Identifier<? extends HasAction> getType() {
		return this.type;
	}

	public void setType(Identifier<? extends HasAction> type) {
		this.type = type;
	}

	public List<Action<HasAction>> getActions() {
		return actions;
	}

	public void setActions(List<Action<HasAction>> actions) {
		this.actions = actions;
	}

	/**
	 * add an action. Also, subscribe this to the action.
	 * 
	 * @param action
	 *            action to add
	 */
	public void addAction(ActionEntry action) {
		this.actions.add(action.getAction());
		action.addSubscriber(this);
	}

}
