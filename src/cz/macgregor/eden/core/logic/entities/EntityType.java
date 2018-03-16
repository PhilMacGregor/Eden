package cz.macgregor.eden.core.logic.entities;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.Sprites;
import cz.macgregor.eden.core.logic.actions.Identifier;

public enum EntityType implements Identifier<Entity> {
	PERSON("person", "img.ent.mob.adam.png", true), @Subscriber({ "birth" })
	BUILDING("building", "img.ent.building.hut.png"), DWELLER_COUNT("NW", "img.ent.debug.ne.png"), DEBUG_NW("NW", "img.ent.debug.nw.png"), DEBUG_NE("NE", "img.ent.debug.ne.png"), DEBUG_SE("SE", "img.ent.debug.se.png"), DEBUG_SW("SW", "img.ent.debug.sw.png"), DEBUG_N("N", "img.ent.debug.n.png"), DEBUG_E("E", "img.ent.debug.e.png"), DEBUG_S("S", "img.ent.debug.s.png"), DEBUG_W("W", "img.ent.debug.w.png"), DEBUG_CENTER("CENTER", "img.ent.debug.center.png"), @Subscriber({ "explore", "randomMove", "procreate" })
	ADAM("Adam", "img.ent.mob.adam.png", true), @Subscriber({ "explore", "randomMove" })
	EVE("Eve", "img.ent.mob.eve.png", true), SETH("Eve", "img.ent.mob.seth.png", true), PINETREE("pineTree", "img.ent.nature.pinetree.png"), MOUNTAIN("mountain", "img.ent.nature.mountain.png");

	private final String name;
	private final String image;
	private final boolean movable;

	private EntityType(String name, String imgPath, boolean movable) {
		this.name = name;
		this.image = imgPath;
		this.movable = movable;
	}

	private EntityType(String name, String imgPath) {
		this(name, imgPath, false);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * @return the image
	 */
	public ImageIcon getImage() {
		return Sprites.image(image);
	}

	/**
	 * @return the movable
	 */
	public boolean isMovable() {
		return movable;
	}

}
