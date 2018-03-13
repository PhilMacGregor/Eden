package cz.macgregor.eden.core.logic.actions;

import java.util.ArrayList;
import java.util.List;

import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;

public abstract class HasAction {
	protected List<Action<HasAction>> actions;

	protected HasAction() {
		this.actions = new ArrayList<>();
	}

	public List<Action<HasAction>> getActions() {
		return actions;
	}

	public void setActions(List<Action<HasAction>> actions) {
		this.actions = actions;
	}

	public void addAction(ActionEntry action) {
		this.actions.add(action.getAction());
		action.addSubscriber(this);
	}

}
