package cz.macgregor.eden.core.logic;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionHolder.ActionEntry;
import cz.macgregor.eden.core.logic.actions.HasAction;
import cz.macgregor.eden.core.logic.actions.Identifier;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.tiles.Field;

/**
 * factory class to create the map objects (fields and entities). Can create a
 * HasAction object based on its Identifier and assign its default actions.
 * 
 * @author MacGregor
 *
 */
public class MapObjectFactory {
	/** the instance. */
	private static MapObjectFactory instance;

	/** creator for fields. */
	private final Creator<Field> fieldCreator;
	/** creator for entities. */
	private final Creator<Entity> entityCreator;

	/**
	 * create a field.
	 * 
	 * @param type
	 *            field type
	 * @return the field
	 */
	public static Field createField(Identifier<Field> type) {
		return getInstance().fieldCreator.create(type);
	}

	/**
	 * create an entity.
	 * 
	 * @param type
	 *            entity type
	 * @return the entity
	 */
	public static Entity createEntity(Identifier<Entity> type) {
		Entity prototype = getInstance().entityCreator.create(type);

		return prototype;
	}

	/**
	 * create a new HasAction object. This method creates the object, assigns
	 * its default actions and activates the Create trigger for the created
	 * object. However, it will not be properly typed. To type it, use the
	 * Creator interface omplementations inside this class, called by the
	 * createField/Entity methods.
	 * 
	 * @param obj
	 *            the object to generate
	 * @return the created object
	 */
	private static HasAction createNew(HasAction obj) {
		addDefaultActions(obj);
		ActionHolder.activateTrigger(TriggerType.CREATE, obj);
		return obj;
	}

	/**
	 * add default actions to the created object.
	 * 
	 * @param obj
	 *            the created object
	 */
	public static void addDefaultActions(HasAction obj) {
		ActionEntry[] actions = ActionHolder.getDefaultActions(obj.getType());
		if (actions != null) {
			for (ActionEntry holder : actions) {
				obj.addAction(holder);
			}
		}
	}

	/**
	 * get instance.
	 * 
	 * @return instance
	 */
	private static MapObjectFactory getInstance() {
		if (instance == null) {
			instance = new MapObjectFactory();
		}

		return instance;
	}

	/**
	 * constructor. Creates anonymous instances of the Creator interface to be
	 * used to create fields or entities.
	 */
	private MapObjectFactory() {
		this.fieldCreator = (type) -> {
			return (Field) createNew(new Field(type));
		};
		this.entityCreator = (type) -> {
			return (Entity) createNew(new Entity(type));
		};
	}

	/**
	 * interface used for generating HasAction objects.
	 * 
	 * @author MacGregor
	 *
	 * @param <T>
	 *            type of the generated object
	 */
	private interface Creator<T extends HasAction> {

		/**
		 * create an object of the type T based on the given Identifier.
		 * 
		 * @param type
		 *            identifier to use
		 * @return the created object
		 */
		T create(Identifier<T> type);
	}

}
