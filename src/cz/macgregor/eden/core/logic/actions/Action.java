package cz.macgregor.eden.core.logic.actions;

/**
 * The interface of the action that can be called upon specific HasAction object
 * (fields or entites).
 * 
 * @author MacGregor
 *
 * @param <T>
 *            type extending HasAction for this action type to be applied
 */
public interface Action<T extends HasAction> {

	/**
	 * apply the action to the given instance. Must be the T type.
	 * 
	 * @param ent
	 *            object to apply action
	 */
	public void doAction(T ent);

}
