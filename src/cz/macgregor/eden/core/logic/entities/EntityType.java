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

	@Subscriber({ "birth" })
	BUILDING("building", "img.ent.building.hut.png"),

	DWELLER_COUNT("NW", "img.ent.debug.ne.png"),

	@Subscriber({ "explore", "randomMove", "procreate" })
	ADAM("Adam", "img.ent.mob.adam.png", true),

	@Subscriber({ "explore", "randomMove" })
	EVE("Eve", "img.ent.mob.eve.png", true),

	PINETREE("pineTree", "img.ent.nature.pinetree.png"),

	MOUNTAIN("mountain", "img.ent.nature.mountain.png");

	private final String name;
	private final String image;
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
