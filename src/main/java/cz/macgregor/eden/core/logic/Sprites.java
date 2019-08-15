package cz.macgregor.eden.core.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import cz.macgregor.eden.util.filecrawler.FileCrawler;
import cz.macgregor.eden.util.filecrawler.ResourceEntry;

/**
 * class containing all sprites used in the game.
 * 
 * @author MacGregor
 *
 */
public class Sprites {
	/** the instance. */
	private static Sprites instance;
	/** sprites and their qualified names. */
	private Map<String, Sprite> spriteMap;

	/**
	 * constructor.
	 */
	private Sprites() {
		init();
	}

	/**
	 * initialisation method. Resolves resources by the FileCrawler and creates
	 * a sprite for each icon contained in root folder.
	 */
	private void init() {
		this.spriteMap = new HashMap<>();
		Collection<ResourceEntry<?>> resources = FileCrawler.getInstance().searchFolder("img", ".png");
		for (ResourceEntry<?> res : resources) {
			spriteMap.put(res.getQualifiedName(), new Sprite((ImageIcon) res.getResource()));
		}

	}

	/**
	 * get instance.
	 * 
	 * @return instance
	 */
	public static Sprites getInstance() {
		if (instance == null) {
			instance = new Sprites();
		}

		return instance;
	}

	/**
	 * get the image with specific qualified name.
	 * 
	 * @param spriteName
	 *            qualified name
	 * @return resolved icon
	 */
	public static ImageIcon image(String spriteName) {
		Sprite sprite = getInstance().spriteMap.get(spriteName);
		return sprite.getIcon();
	}

	/**
	 * every instance of this class contains a single resolved icon.
	 * 
	 * @author MacGregor
	 *
	 */
	private class Sprite {
		/** the icon encapsulated by the sprite. */
		private final ImageIcon icon;

		/**
		 * constructor.
		 * 
		 * @param img
		 *            icon to be contained
		 */
		private Sprite(ImageIcon img) {
			this.icon = img;
		}

		/**
		 * getter.
		 * 
		 * @return the icon
		 */
		public ImageIcon getIcon() {
			return icon;
		}

	}

}
