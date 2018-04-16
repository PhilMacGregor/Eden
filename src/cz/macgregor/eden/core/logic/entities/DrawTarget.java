package cz.macgregor.eden.core.logic.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** target layers to be drawn with their index. */
public enum DrawTarget {
	/** ground layer. */
	LAYER_GROUND(0),
	/** structure layer. */
	LAYER_STRUCT(1),
	/** biosphere layer. */
	LAYER_BIO(2),
	/** clouds layer. */
	LAYER_CLOUDS(3);

	/** layer index. */
	private int layer;
	/**
	 * entity positions to specify the field-relative position for each entity
	 * to be drawn at.
	 */
	private Map<EntityType, Direction> entityPositions;

	/**
	 * constructor.
	 * 
	 * @param layer
	 *            layer index.
	 */
	private DrawTarget(int layer) {
		this.layer = layer;
		this.entityPositions = new HashMap<>();
	}

	/**
	 * get all targets as list sorted by their index.
	 * 
	 * @return all targets as list sorted by their index
	 */
	public static List<DrawTarget> allTargets() {
		DrawTarget[] ret = new DrawTarget[DrawTarget.values().length];

		for (int i = 0; i < DrawTarget.values().length; i++) {
			DrawTarget tar = DrawTarget.values()[i];
			ret[tar.layer] = tar;
		}

		return Arrays.asList(ret);

	}

	/**
	 * add an entity type with the direction relative to the field to this
	 * layer.
	 * 
	 * @param type
	 *            entity type
	 * @param dir
	 *            direction
	 */
	public void addEntity(EntityType type, Direction dir) {
		entityPositions.put(type, dir);
	}

	public Map<EntityType, Direction> getPositions() {
		return entityPositions;
	}

	/**
	 * directions to draw the entites.
	 * 
	 * @author MacGregor
	 *
	 */
	public enum Direction {
		/**
		 * north-west. The entites will be drawn at top-left corner of the
		 * field.
		 */
		NW,
		/**
		 * north. The entites will be drawn at top side of the field.
		 */
		N,
		/**
		 * north-east. The entites will be drawn at top-right corner of the
		 * field.
		 */
		NE,
		/**
		 * east. The entites will be drawn at right side of the field.
		 */
		E,
		/**
		 * south-west. The entites will be drawn at bottom-right of the field.
		 */
		SE,
		/**
		 * south. The entites will be drawn at bottom side of the field.
		 */
		S,
		/**
		 * south-west. The entites will be drawn at bottom-left corner of the
		 * field.
		 */
		SW,
		/**
		 * west. The entites will be drawn at right side of the field.
		 */
		W,
		/**
		 * center. The entites will be drawn at center of the field.
		 */
		CENTER,
		/**
		 * random. If the entity is type EntityWithPosition, it will be drawn at
		 * its position. Otherwise, random position will be created.
		 */
		RANDOM;
	}
}
