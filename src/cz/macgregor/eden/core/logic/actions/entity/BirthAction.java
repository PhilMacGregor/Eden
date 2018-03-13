package cz.macgregor.eden.core.logic.actions.entity;

import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.EntityAction;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityFactory;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.util.Utils;

@ActionInfo(name = "birth", trigger = TriggerType.TURN_START)
public class BirthAction implements EntityAction {
	private static final int NEWBORN_PROBABILITY = 5;
	private static final int MAX_DWELLERS = 5;

	@Override
	public void doAction(Entity ent) {
		int dwellers = 0;
		
		Field fld = ent.getField();
		for (Entity other : fld.getEntities()) {
			if (other.getType() == EntityType.DWELLER_COUNT) {
				dwellers++;
			}
		}
		
		if (dwellers >= MAX_DWELLERS) {
			return;
		}
		
		int probability = NEWBORN_PROBABILITY * (dwellers * dwellers * dwellers + 1);
		
		int doBirth = Utils.randomInt(0, probability);
		
		if (doBirth >= probability - 1) {
			int isMale = Utils.randomInt(0, 2);
			
			Entity newBorn = EntityFactory.newEntity(isMale == 1 ? EntityType.ADAM : EntityType.EVE);
			fld.addEntity(newBorn);
			fld.addEntity(EntityFactory.newEntity(EntityType.DWELLER_COUNT));
			
			System.out.println("New human was born: " + newBorn.getType());
		}
	}

}
