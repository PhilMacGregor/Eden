package cz.macgregor.eden.core.logic.actions.entity;

import java.awt.Point;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.MapObjectFactory;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Const;

@ActionInfo(name = "procreate", trigger = TriggerType.TURN_END)
public class ProcreateAction implements EntityAction {

	@Override
	public void doAction(Entity ent) {
		Field fld = ent.getField();

		if (!fld.getType().getDefaultProps().isCanBuild()) {
			return;
		}

		boolean hasEve = false;
		boolean hasBuildings = false;
		for (Entity other : fld.getEntities()) {

			if (other.getType() == EntityType.EVE) {
				hasEve = true;
			}

			if (other.getType() == EntityType.BUILDING) {
				hasBuildings = true;
			}

		}

		if (hasEve && !hasBuildings) {
			Point centerPoint = getCenterForIcon(EntityType.BUILDING.getImage());

			Entity house = new EntityWithPosition(EntityType.BUILDING, centerPoint.x, centerPoint.y);
			MapObjectFactory.addDefaultActions(house);
			fld.addEntity(house);
		}

	}

	private Point getCenterForIcon(ImageIcon icon) {
		int x = (Const.TILE_WIDTH / 2) - (icon.getIconWidth() / 2);
		int y = (Const.TILE_HEIGHT / 2) - (icon.getIconHeight() / 2);

		return new Point(x, y);
	}

}
