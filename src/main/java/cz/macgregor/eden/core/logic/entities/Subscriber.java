package cz.macgregor.eden.core.logic.entities;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * annotation used on HasAction objects to determine the object has a certain
 * actions.
 * 
 * @author MacGregor
 *
 */
@Retention(RUNTIME)
@Target(FIELD)
public @interface Subscriber {
	/**
	 * @return list of string names of the action (annotation ActionInfo, name)
	 *         to be used.
	 * 
	 */
	String[] value();
}
