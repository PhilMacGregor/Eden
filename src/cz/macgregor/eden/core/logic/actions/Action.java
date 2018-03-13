package cz.macgregor.eden.core.logic.actions;

public interface Action<T extends HasAction> {

	public void doAction(T ent);

}
