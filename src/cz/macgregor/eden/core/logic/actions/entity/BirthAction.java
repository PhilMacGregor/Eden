package cz.macgregor.eden.core.logic.actions.entity;

import cz.macgregor.eden.core.logic.MapObjectFactory;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.components.top.ValueIndicators;
import cz.macgregor.eden.util.Utils;

/**
 * The action used for birth of new humans.<br>
 * <br>
 * Every house has this action and is used for birth of new humans. Maximum
 * humans able to be born in a house is determined by MAX_DWELLERS, more people
 * have already been born in the house, less probably a new one will be born.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "birth", trigger = TriggerType.TURN_START)
public class BirthAction implements EntityAction {
	/**
	 * higher this number is, the slower new humans will be born. The final
	 * probability is determined as 1/NEWBORN_PROBABILITY.
	 */
	private static final int NEWBORN_PROBABILITY = 5;
	/** maximum dwellers for a single house. */
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

			Entity newBorn = MapObjectFactory.createEntity(isMale == 1 ? EntityType.ADAM : EntityType.EVE);
			fld.addEntity(newBorn);
			fld.addEntity(MapObjectFactory.createEntity(EntityType.DWELLER_COUNT));

			ValueIndicators.POPULATION.update(1);
		}
	}

}
