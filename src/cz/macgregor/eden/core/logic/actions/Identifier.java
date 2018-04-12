package cz.macgregor.eden.core.logic.actions;

/**
 * an empty interface to determine that the object is the type used to specify
 * the HasAction object. Typicall Indentifiers are:<br>
 * <br>
 * TileType, implements {@literal Identifier<Field>}<br>
 * EntityType, implements {@literal Identifier<Entity>}
 * 
 * @author MacGregor
 *
 * @param <T>
 *            the type to identify
 */
public interface Identifier<T extends HasAction> {

}
