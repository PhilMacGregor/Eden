package cz.macgregor.eden.core.logic.entities;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.Sprites;
import cz.macgregor.eden.core.logic.actions.Identifier;

/**
 * Entity type enumeration, contains all entity typec that can be used.
 * Implements Identifier interface to be used by MapObjectFactory, ActionHolder
 * etc. The instances can use the {@literal @Subscriber} annotation to determine
 * a default action to the generated entities. The used actions must be type
 * {@literal Action<Entity>}, typically EntityAction.
 * 
 * @author MacGregor
 *
 */
public enum EntityType implements Identifier<Entity> {

	/** building. where people are born. */
	@Subscriber({ "birth" })
	BUILDING("building", "img.ent.building.hut.png"),
	/**
	 * entity to display number of dwellers that have been born in the building.
	 */
	DWELLER_COUNT("NW", "img.ent.debug.ne.png"),
	/**
	 * Adam - male. Has the explore and random move action, but unlike Eve, he
	 * holds the procreate action too.
	 */
	@Subscriber({ "explore", "randomMove", "procreate" })
	ADAM("Male", "img.ent.mob.adam.png", true),
	/**
	 * Eve - female. Has the explore and random move action.
	 */
	@Subscriber({ "explore", "randomMove" })
	EVE("Female", "img.ent.mob.eve.png", true),
	/**
	 * male child. Every human is born as a child and then it grows older. The
	 * Son aquires Procreate action when it turns into Adam.
	 */
	@Subscriber({ "explore", "randomMove", "age" })
	SON("Male child", "img.ent.mob.son.png", true),
	/**
	 * female child. Every human is born as a child and then it grows older.
	 */
	@Subscriber({ "explore", "randomMove", "age" })
	DAUGHTER("Female child", "img.ent.mob.daughter.png", true),

	/**
	 * Old man. Can no longer procreate.
	 */
	@Subscriber({ "explore", "randomMove" })
	GRANDPA("Old man", "img.ent.mob.adam.png", true),
	/**
	 * Old woman. Can no longer procreate.
	 */
	@Subscriber({ "explore", "randomMove" })
	GRANDMA("Old woman", "img.ent.mob.eve.png", true),

	/** tree, randomly generated in forest or by growForest action. */
	PINETREE("pineTree", "img.ent.nature.pinetree.png"),
	/** mountain, randomly generated on mountains field. */
	MOUNTAIN("mountain", "img.ent.nature.mountain.png");

	/** entity name. */
	private final String name;
	/** entity path to the entity image. */
	private final String image;
	/** can entity be moved. */
	private final boolean movable;

	/**
	 * constructor.
	 * 
	 * @param name
	 *            name
	 * @param imgPath
	 *            image path
	 * @param movable
	 *            can the entity be moved
	 */
	private EntityType(String name, String imgPath, boolean movable) {
		this.name = name;
		this.image = imgPath;
		this.movable = movable;
	}

	/**
	 * constructor with movable=false.
	 * 
	 * @param name
	 *            name
	 * @param imgPath
	 *            image path
	 */
	private EntityType(String name, String imgPath) {
		this(name, imgPath, false);
	}

	/**
	 * getter.
	 * 
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * getter.
	 * 
	 * @return the image
	 */
	public ImageIcon getImage() {
		return Sprites.image(image);
	}

	/**
	 * getter.
	 * 
	 * @return the movable
	 */
	public boolean isMovable() {
		return movable;
	}

}
