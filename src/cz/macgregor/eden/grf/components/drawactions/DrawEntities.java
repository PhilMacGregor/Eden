package cz.macgregor.eden.grf.components.drawactions;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.macgregor.eden.core.logic.entities.DrawTarget;
import cz.macgregor.eden.core.logic.entities.DrawTarget.Direction;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.components.DrawAction;
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;

public class DrawEntities implements DrawAction {

	private DrawTarget layerToDraw;
	
	private Map<Direction, EntityDrawer> positionDrawers;

	public DrawEntities(DrawTarget layer, Map<Direction, EntityDrawer> drawers) {
		this.layerToDraw = layer;
		this.positionDrawers = drawers;
	}

	@Override
	public void draw(Graphics g, Field field, Point coords, Point index) {
		if (field == null || !field.isVisible() || !field.hasEntites()) {
			return;
		}

		Map<Direction, List<Entity>> entityMap = createEntityMap(field, layerToDraw);

		for (Direction pos : entityMap.keySet()) {
			if (positionDrawers.containsKey(pos)) {
				List<Entity> entsToDraw = entityMap.get(pos);
				EntityDrawer drawer = positionDrawers.get(pos);
				drawer.draw(g, entsToDraw, coords.x, coords.y);
			}
		}

	}

	private Map<Direction, List<Entity>> createEntityMap(Field field, DrawTarget target) {
		Map<EntityType, Direction> entityPositions = target.getPositions();

		Map<Direction, List<Entity>> entityMap = new HashMap<>();
		for (Entity ent : field.getEntities()) {
			Direction pos = entityPositions.get(ent.getType());

			if (!entityMap.containsKey(pos)) {
				entityMap.put(pos, new ArrayList<>());
			}
			entityMap.get(pos).add(ent);
		}

		return entityMap;
	}

	

}
