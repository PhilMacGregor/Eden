package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

public class RandomDrawer implements EntityDrawer {
	private static final int Y_OFFSET_TOP = 15;
	private static final int Y_OFFSET_BOTTOM = 5;

	@Override
	public void draw(Graphics g, List<Entity> ents, int x, int y) {
		Entity[] entsArray = sortEntitiesByHeight(ents);

		for (int i = 0; i < entsArray.length; i++) {
			Entity ent = entsArray[i];
			
			ImageIcon img = ent.getType().getImage();

			int randX;
			int randY;

			if (ent instanceof EntityWithPosition) {
				randX = x + ((EntityWithPosition) ent).getCoordsX();
				randY = y + ((EntityWithPosition) ent).getCoordsY();
			} else {
				randX = x + (Utils.randomInt(0, Const.TILE_WIDTH - img.getIconWidth()));
				randY = y + (Utils.randomInt(-Y_OFFSET_TOP, Y_OFFSET_BOTTOM));
			}

			g.drawImage(img.getImage(), randX, randY, null);
		}
	}
	
	private Entity[] sortEntitiesByHeight(List<Entity> ents) {
		Entity[] ret = ents.toArray(new Entity[ents.size()]);
		Arrays.sort(ret, new EntityHeightSorter());
		return ret;
	}
	
	private class EntityHeightSorter implements Comparator<Entity> {
		@Override
		public int compare(Entity ent1, Entity ent2) {
			if (ent1 instanceof EntityWithPosition && ent2 instanceof EntityWithPosition) {
				int y1 = ((EntityWithPosition) ent1).getCoordsY();
				int y2 = ((EntityWithPosition) ent2).getCoordsY();

				return y1 - y2;
			} else {
				return 0;
			}
		}
	}

}
