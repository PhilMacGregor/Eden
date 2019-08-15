package cz.macgregor.eden.grf.components.right;

import java.awt.Graphics;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;

import cz.macgregor.eden.core.logic.entities.DrawTarget;
import cz.macgregor.eden.core.logic.entities.DrawTarget.Direction;
import cz.macgregor.eden.core.logic.patterns.RectanglePattern;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.FieldInfo;
import cz.macgregor.eden.grf.components.drawactions.DrawAction;
import cz.macgregor.eden.grf.components.drawactions.DrawEntities;
import cz.macgregor.eden.grf.components.drawactions.DrawTiles;
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;
import cz.macgregor.eden.util.Const;

/**
 * label that contains information about the currently selected field.
 * 
 * @author MacGregor
 *
 */
public class DetailLabel extends JLabel {

  /** uid. */
  private static final long serialVersionUID = 1L;

  /**
   * list of draw actions to be used. Each DrawAction represents a single layer
   * to be drawn.
   */
  private List<DrawAction> drawActions;

  /** selected field. */
  private Field selectedField;

  /**
   * constructor.
   * 
   * @param drawers
   *          drawers used to draw the field content.
   */
  public DetailLabel(Map<Direction, EntityDrawer> drawers) {
    this.drawActions = new ArrayList<>();
    drawActions.add(new DrawTiles());
    drawActions.add(new DrawEntities(DrawTarget.LAYER_GROUND, drawers));
    drawActions.add(new DrawEntities(DrawTarget.LAYER_STRUCT, drawers));
    drawActions.add(new DrawEntities(DrawTarget.LAYER_BIO, drawers));
    drawActions.add(new DrawEntities(DrawTarget.LAYER_CLOUDS, drawers));
  }

  /**
   * set the field to draw, then repaint.
   * 
   * @param selectedField
   *          selected field
   */
  public void draw(Field selectedField) {
    this.selectedField = selectedField;
    repaint();
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponents(g);

    if (selectedField == null) {
      return;
    }

    Point coords = selectedField.getCoords();
    List<FieldInfo> fieldsToDraw = selectedField.getParent().getFieldsByPattern(new RectanglePattern(), coords, new Point(1, 1));

    for (DrawAction action : drawActions) {
      paintMap(g, new Point(coords.x - 1, coords.y - 1), new Point(coords.x + 1, coords.y + 1), fieldsToDraw, action);
    }

  }

  /**
   * method to paint the map, call once for every draw action from the
   * drawActions.
   * 
   * @param g
   *          graphics
   * @param from
   *          from point for the map
   * @param to
   *          to point from the map
   * @param fields
   *          fields to be drawn
   * @param action
   *          used draw action
   */
  private void paintMap(Graphics g, Point from, Point to, List<FieldInfo> fields, DrawAction action) {

    for (FieldInfo fld : fields) {
      Point index = fld.getPosition();
      Point coords = new Point((index.x - from.x) * Const.TILE_WIDTH, (index.y - from.y) * Const.TILE_HEIGHT);

      action.draw(g, fld.getField(), coords, index);
    }

  }

}
