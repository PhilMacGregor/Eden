package cz.macgregor.eden.core.logic.actions;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * The annotation used to determine a name and trigger for the action. Used with
 * Action class.
 * 
 * @author MacGregor
 *
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface ActionInfo {
	/**
	 * @return action name for the list of the actions
	 */
	String name();

	/**
	 * @return trigger used for the action
	 */
	TriggerType trigger();
}
