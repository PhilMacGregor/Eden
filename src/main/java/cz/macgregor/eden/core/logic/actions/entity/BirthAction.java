package cz.macgregor.eden.core.logic.actions.entity;

import cz.macgregor.eden.core.logic.MapObjectFactory;
import cz.macgregor.eden.core.logic.actions.ActionInfo;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.actions.Updates;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.components.top.ValueIndicators;
import cz.macgregor.eden.util.Utils;
import cz.macgregor.eden.util.namegen.Names;

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

  /** random names generator. */
  private final Names names;

  /**
   * constructor.
   */
  public BirthAction() {
    this.names = new Names();
  }

  @Override
  public Updates doAction(Entity ent) {
    int dwellers = 0;

    Field fld = ent.getField();
    for (Entity other : fld.getEntities()) {
      if (other.getType() == EntityType.DWELLER_COUNT) {
        dwellers++;
      }
    }

    if (dwellers >= MAX_DWELLERS) {
      return null;
    }

    int probability = NEWBORN_PROBABILITY * (dwellers * dwellers * dwellers + 1);

    int doBirth = Utils.randomInt(0, probability);

    if (doBirth >= probability - 1) {
      boolean isMale = Utils.randomInt(0, 2) == 1;

      Entity newBorn = MapObjectFactory.createEntity(isMale ? EntityType.SON : EntityType.DAUGHTER);
      newBorn.setProp("name", names.generate(isMale));
      newBorn.setProp("mother", ent.getPropAsString("mother"));
      newBorn.setProp("father", ent.getPropAsString("father"));
      newBorn.setProp("gender", isMale ? "male" : "female");

      System.out.println(newBorn.getPropAsString("name") + " was born to " + newBorn.getPropAsString("mother") + " and "
          + newBorn.getPropAsString("father") + ".");

      Updates updates = new Updates();
      updates.addEntities(fld, newBorn);
      updates.addEntities(fld, MapObjectFactory.createEntity(EntityType.DWELLER_COUNT));

      ValueIndicators.POPULATION.update(1);

      return updates;
    }

    return null;
  }

}
