package cz.macgregor.eden.core.logic.entities;

import java.io.FileNotFoundException;

import javax.swing.ImageIcon;

import cz.macgregor.eden.util.Utils;

public enum Sprite {
	
	ADAM("Adam", "img/ent/mob/adam.png"),
	EVE("Eve", "img/ent/mob/eve.png");

	private final String name;
	private final ImageIcon	image;
	
	private Sprite(String name, String imgPath) {
		this.name = name;
		
		try {
			this.image = Utils.loadIcon(imgPath);
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		}
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
		return this.image;
	}
}
