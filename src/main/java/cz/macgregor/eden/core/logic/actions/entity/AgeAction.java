package cz.macgregor.eden.core.logic.actions.entity;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.actions.Updates;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.grf.components.top.ValueIndicators;

/**
 * action that handles human aging. The humans are born as children, then they
 * grow older through time.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "age", trigger = TriggerType.TURN_START)
public class AgeAction implements EntityAction {
	/** age for a child to turn adult. */
	private static final int AGE_ADULT = 5;
	/** age for a child to turn old. */
	private static final int AGE_OLD = 60;
	/** age for a child to die. */
	private static final int MORTALITY = 100;

	@Override
	public Updates doAction(Entity ent) {
		if (ent.getProp("age") == null) {
			ent.setProp("age", 0);
		}

		int age = (int) ent.getProp("age");

		if (age == AGE_ADULT) {
			switch (ent.getType()) {
			case SON:
				ent.setType(EntityType.ADAM);
				ent.addAction(ActionHolder.byName("procreate"));
				System.out.println(ent.getPropAsString("name") + " grown up to a man.");
				break;
			case DAUGHTER:
				ent.setType(EntityType.EVE);
				System.out.println(ent.getPropAsString("name") + " grown up to a woman.");
				break;
			default:
				break;
			}
		}

		if (age == AGE_OLD) {
			switch (ent.getType()) {
			case ADAM:
				ent.setType(EntityType.GRANDPA);
				ent.removeAction(ActionHolder.byName("procreate"));
				break;
			case EVE:
				ent.setType(EntityType.GRANDMA);
				break;
			default:
				break;
			}
			System.out.println(ent.getPropAsString("name") + " has got old.");
		}

		if (age == MORTALITY) {
			System.out.println(ent.getPropAsString("name") + " died at age of " + age + ".");
			ValueIndicators.POPULATION.update(-1);

			Updates updates = new Updates();
			updates.killEntities(ent);
			return updates;
		}

		ent.setProp("age", age + 1);
		return null;

	}

}
