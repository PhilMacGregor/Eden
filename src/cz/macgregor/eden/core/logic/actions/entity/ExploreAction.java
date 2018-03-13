package cz.macgregor.eden.core.logic.actions.entity;

import java.awt.Point;

import cz.macgregor.eden.core.logic.MapGenerator;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.EntityAction;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.patterns.RectanglePattern;

@ActionInfo(name = "explore", trigger = TriggerType.AFTER_MOVE)
public class ExploreAction implements EntityAction {

	@Override
	public void doAction(Entity ent) {
		
		MapGenerator mapGen = ent.getField().getParent().getMapGenerator();
		Point pos = ent.getField().getCoords();
		mapGen.fillPattern(new RectanglePattern(), pos, new Point(1, 1));
	}

}
