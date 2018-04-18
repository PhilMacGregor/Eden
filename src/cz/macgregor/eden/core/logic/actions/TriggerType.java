package cz.macgregor.eden.core.logic.actions;

/**
 * triggers used to determine when an action should be called.
 * 
 * @author MacGregor
 *
 */
public enum TriggerType {
	/** the action is called at the start of the game. */
	GAME_START,
	/** the action is called at the start of the turn. */
	TURN_START,
	/** the action is called at the end of the turn. */
	TURN_END,
	/** the action is called before an entity move. */
	BEFORE_MOVE,
	/** the action is called after an entity move. */
	AFTER_MOVE,
	/** the action is called when the entity is created. */
	CREATE,
	/** the action is called when the entity is destroyed. */
	DESTROY,
	/**
	 * there is no automatic call of the action. It should be called manually.
	 */
	MANUALLY
}