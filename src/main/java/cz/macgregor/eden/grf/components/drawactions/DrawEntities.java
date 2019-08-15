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
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;

/**
 * drawer used to drawing entities. The drawer has a target layer to draw and
 * only draws those entites present in that layer.
 * 
 * @author MacGregor
 *
 */
public class DrawEntities implements DrawAction {

	/** layer to draw. */
	private DrawTarget layerToDraw;
	/**
	 * position drawers. Each entity can also have a position relative to the
	 * field to be drawn at.
	 */
	private Map<Direction, EntityDrawer> positionDrawers;

	/**
	 * constructor.
	 * 
	 * @param layer
	 *            target layer
	 * @param drawers
	 *            position drawers
	 */
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

	/**
	 * create a map of entites to be drawn. Only map the entities that are
	 * targeted by th drawer.
	 * 
	 * @param field
	 *            field containing the entities
	 * @param target
	 *            target to check. Do not map the entites not present in the
	 *            target.
	 * @return list of entites to be drawn
	 */
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
