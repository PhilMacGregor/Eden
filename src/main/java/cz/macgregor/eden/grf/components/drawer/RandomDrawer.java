package cz.macgregor.eden.grf.components.drawer;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.ImageIcon;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityWithPosition;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

/**
 * drawer used for drawing the entities with positions. If the entity does not
 * have position, draw it at random position.
 * 
 * @author MacGregor
 *
 */
public class RandomDrawer implements EntityDrawer {
	/** minimum offset from top. */
	private static final int Y_OFFSET_TOP = 5;
	/** minimum offset from bottom. */
	private static final int Y_OFFSET_BOTTOM = 5;

	@Override
	public void draw(Graphics g, List<Entity> ents, int x, int y) {
		Map<Point, ImageIcon> entsWithCoords = new TreeMap<>();

		Entity[] entsArray = sortEntitiesByHeight(ents);
		for (int i = 0; i < entsArray.length; i++) {
			Entity ent = entsArray[i];

			int randX;
			int randY;

			ImageIcon img = ent.getType().getImage();

			if (ent instanceof EntityWithPosition) {
				randX = x + ((EntityWithPosition) ent).getCoordsX();
				randY = y + ((EntityWithPosition) ent).getCoordsY();
			} else {
				randX = x + (Utils.randomInt(0, Const.TILE_WIDTH - img.getIconWidth()));
				randY = y + (Utils.randomInt(0 - Y_OFFSET_TOP,
						Const.TILE_HEIGHT - img.getIconHeight() - Y_OFFSET_BOTTOM));
			}

			entsWithCoords.put(new PointByHeight(randX, randY), img);
		}

		for (Point pt : entsWithCoords.keySet()) {
			ImageIcon img = entsWithCoords.get(pt);
			g.drawImage(img.getImage(), pt.x, pt.y, null);
		}

	}

	/**
	 * sort the entities from higher to lower for them to display properly.
	 * 
	 * @param ents
	 *            entities
	 * @return sorted entity array
	 */
	private Entity[] sortEntitiesByHeight(List<Entity> ents) {
		Entity[] ret = ents.toArray(new Entity[ents.size()]);
		Arrays.sort(ret, new EntityHeightSorter());
		return ret;
	}

	/**
	 * comparator used for sorting the entities.
	 * 
	 * @author MacGregor
	 *
	 */
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

	/**
	 * the classic java.awt.Point, only with implemented Comparable.
	 * 
	 * @author MacGregor
	 *
	 */
	private class PointByHeight extends Point implements Comparable<Point> {
		private static final long serialVersionUID = 1L;

		/**
		 * constructor.
		 * 
		 * @param x
		 *            x
		 * @param y
		 *            y
		 */
		private PointByHeight(int x, int y) {
			super(x, y);
		}

		@Override
		public int compareTo(Point o) {
			return this.y - o.y;
		}

	}

}
