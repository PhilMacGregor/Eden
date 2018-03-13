package cz.macgregor.eden.core.logic.tiles;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.Sprites;
import cz.macgregor.eden.core.logic.entities.Subscriber;
import cz.macgregor.eden.util.Utils;

/**
 * class to hold tile types, their names, images and properties.
 * 
 * @author MacGregor
 */
public enum TileType {
  // name, image, is water, can build on, fertility, wood, stone, ore, danger

	/** tile type to hold an image for unknown tiles. */
	UNKNOWN("unexplored", "img.tile.unknown.png", null, false),
	/** tile type to hold an image for tiles that are not visible. */
	INVISIBLE("invisible", "img.tile.invisible.png", null, false),
	/**
	 * The holy tree. This tile is only generated at 0;0. It cannot be used by map
	 * generator.
	 */
	ORIGIN("The Origin", "img.tile.holyTree.png", new TileProps().withWater(false).withCanBuild(false).withFertility(0).withWood(0).withStone(0).withOre(0).withDanger(0), false),
	/** forest. */
	@Subscriber({ "createTrees", "growForest" })
	FOREST("forest", "img.tile.forestGround.png", new TileProps().withWater(false).withCanBuild(false).withFertility(0).withWood(1).withStone(0).withOre(0).withDanger(1), true),
	/** fertile grass. */
	GRASS("grass", "img.tile.grass.png", new TileProps().withWater(false).withCanBuild(true).withFertility(2).withWood(0).withStone(0).withOre(0).withDanger(0), true),
	/** mountains. */
	@Subscriber({ "createMountains" })
	MOUNTAINS("mountains", "img.tile.mountainGround.png", new TileProps().withWater(false).withCanBuild(false).withFertility(0).withWood(0).withStone(2).withOre(1).withDanger(2), true),
	/** desert. */
	DESERT("desert", "img.tile.desert.png", new TileProps().withWater(false).withCanBuild(true).withFertility(0).withWood(0).withStone(1).withOre(0).withDanger(1), true),
	/** less fertile variant if the grass. */
	MEADOW("meadow", "img.tile.meadow.png", new TileProps().withWater(false).withCanBuild(true).withFertility(1).withWood(0).withStone(0).withOre(0).withDanger(0), true),
	/** water. */
	WATER("water", "img.tile.water.png", new TileProps().withWater(true).withCanBuild(false).withFertility(0).withWood(0).withStone(0).withOre(0).withDanger(0), true);

	/** tile name. */
	private final String		name;
	/** tile image. */
	private final String		image;
	/** default properties for the tile type. */
	private final TileProps	defaultProps;

	/**
	 * constructor.
	 * 
	 * @param name
	 *          name
	 * @param imgPath
	 *          path to image
	 * @param props
	 *          properties
	 * @param usedInGenerator
	 *          if false, this tile type is not used in random generation by map
	 *          generator
	 */
	private TileType(String name, String imgPath, TileProps props, boolean usedInGenerator) {
		this.name = name;
		this.image = imgPath;

		this.defaultProps = props;

		if (usedInGenerator) {
			addToUsableTiles(this);
		}

	}

	/**
	 * add to the list of usable items holded by a special object to prevent
	 * initialisation errors.
	 * 
	 * @param type
	 *          type
	 */
	private void addToUsableTiles(TileType type) {
		UsableListHolder.getInstance().add(type);
	}

	/**
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return image
	 */
	public ImageIcon getIcon() {
		return Sprites.image(image);
	}

	/**
	 * convenience method that returns the image instead of the icon.
	 * 
	 * @return image
	 */
	public Image getImage() {
		return Sprites.image(image).getImage();
	}

	/**
	 * returns a random tile type from list of usable tile types.
	 * 
	 * @return random tile type
	 */
	public static TileType randomType() {
		List<TileType> list = UsableListHolder.getInstance();
		return list.get(Utils.randomInt(0, list.size()));
	}

	/**
	 * @return default properties
	 */
	public TileProps getDefaultProps() {
		return defaultProps;
	}

	/**
	 * class to hold an instance of the List to store TileType instances used for
	 * random generation.
	 * 
	 * @author MacGregor
	 *
	 */
	private static final class UsableListHolder {
		/** the list instance. */
		private static List<TileType> instance;

		/**
		 * @return instance
		 */
		private static List<TileType> getInstance() {
			if (instance == null) {
				instance = new ArrayList<>();
			}
			return instance;
		}
	}

}
