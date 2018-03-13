package cz.macgregor.eden.core.logic.actions.entity;

import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.EntityAction;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityFactory;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;

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
			fld.addEntity(EntityFactory.newEntity(EntityType.BUILDING));
		}
		
	}

}
