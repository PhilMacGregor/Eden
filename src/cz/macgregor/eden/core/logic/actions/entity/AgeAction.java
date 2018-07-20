package cz.macgregor.eden.core.logic.actions.entity;

import java.util.Objects;

import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;

/**
 * action that handles human aging. The humans are born as children, then they
 * grow older through time.
 * 
 * @author MacGregor
 *
 */
@ActionInfo(name = "age", trigger = TriggerType.TURN_START)
public class AgeAction implements EntityAction {

	private static final int AGE_ADULT = 5;
	private static final int AGE_OLD = 30;
	private static final int MORTALITY = 1;

	@Override
	public void doAction(Entity ent) {
		if (ent.getProp("age") == null) {
			ent.setProp("age", 0);
		}

		if (Objects.equals(ent.getProp("age"), AGE_ADULT)) {
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

		if (Objects.equals(ent.getProp("age"), AGE_OLD)) {
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

		ent.setProp("age", (Integer) ent.getProp("age") + 1);

	}

}
