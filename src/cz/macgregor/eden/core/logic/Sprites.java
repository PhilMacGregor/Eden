package cz.macgregor.eden.core.logic;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import cz.macgregor.eden.util.filecrawler.FileCrawler;
import cz.macgregor.eden.util.filecrawler.ResourceEntry;

public class Sprites {
	private static Sprites instance;

	private Map<String, Sprite> spriteMap;

	private Sprites() {
		init();
	}

	private void init() {
		this.spriteMap = new HashMap<>();
		Collection<ResourceEntry<?>> resources = FileCrawler.getInstance().searchFolder("img", ".png");
		for (ResourceEntry<?> res : resources) {
			spriteMap.put(res.getQualifiedName(), new Sprite((ImageIcon) res.getResource()));
		}

	}

	public static Sprites getInstance() {
		if (instance == null) {
			instance = new Sprites();
		}

		return instance;
	}

	public static ImageIcon image(String spriteName) {
		Sprite sprite = getInstance().spriteMap.get(spriteName);
		return sprite.getIcon();
	}

	private class Sprite {
		private final ImageIcon icon;

		private Sprite(ImageIcon img) {
			this.icon = img;
		}

		/**
		 * @return the icon
		 */
		public ImageIcon getIcon() {
			return icon;
		}

	}

}
