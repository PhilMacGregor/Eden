package cz.macgregor.eden.core.logic.entities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum DrawTarget {
	LAYER_GROUND(0), LAYER_STRUCT(1), LAYER_BIO(2), LAYER_CLOUDS(3);
	
	private int layer;
	private Map<EntityType, Direction> entityPositions;
	
	private DrawTarget(int layer) {
		this.layer = layer;
		this.entityPositions = new HashMap<>();
	}
	
	public static List<DrawTarget> allTargets() {
		DrawTarget[] ret = new DrawTarget[DrawTarget.values().length];
		
		for (int i = 0; i < DrawTarget.values().length; i++) {
			DrawTarget tar = DrawTarget.values()[i];
			ret[tar.layer] = tar;
		}
		
		return Arrays.asList(ret);
		
	}
	
	public void addEntity(EntityType type, Direction dir) {
		entityPositions.put(type, dir);
	}
	
	public Map<EntityType, Direction> getPositions() {
		return entityPositions;
	}
	
	public enum Direction {
		NW, N, NE, E, SE, S, SW, W, CENTER, LOOKUP, RANDOM;
	}
}
