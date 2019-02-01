package cz.macgregor.eden.grf.components.right;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import cz.macgregor.eden.core.logic.entities.DrawTarget.Direction;
import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;
import cz.macgregor.eden.util.Const;

/**
 * Side panel to precision-control of the units.
 * 
 * @author MacGregor
 *
 */
public class RightPanel extends JPanel {

  /**
   * uid.
   */
  private static final long serialVersionUID = 1L;
  /** text field to display text information about the selected field. */
  private final JLabel descField;
  /** detail label. */
  private final DetailLabel detail;
  /** entity list. */
  private final EntityListPanel entityList;

  /**
   * constructor.
   * 
   * @param drawers
   *          drawers used to draw the field content.
   * 
   */
  public RightPanel(Map<Direction, EntityDrawer> drawers) {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

    this.detail = new DetailLabel(drawers);
    detail.setPreferredSize(new Dimension(3 * Const.TILE_WIDTH, 3 * Const.TILE_HEIGHT));
    detail.setMinimumSize(new Dimension(3 * Const.TILE_WIDTH, 3 * Const.TILE_HEIGHT));
    detail.setMaximumSize(new Dimension(3 * Const.TILE_WIDTH, 3 * Const.TILE_HEIGHT));
    detail.setOpaque(true);
    detail.setBackground(Color.GREEN);

    this.entityList = new EntityListPanel();
    entityList.setLayout(new BoxLayout(entityList, BoxLayout.Y_AXIS));

    JScrollPane scrollPane = new JScrollPane(entityList, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPane.getVerticalScrollBar().setUnitIncrement(20);

    this.descField = new JLabel();
    descField.setMinimumSize(new Dimension(3 * Const.TILE_WIDTH, 30));
    descField.setMaximumSize(new Dimension(3 * Const.TILE_WIDTH, 30));
    descField.setPreferredSize(new Dimension(3 * Const.TILE_WIDTH, 30));

    this.add(descField);
    this.add(detail);
    this.add(scrollPane);

    validate();
    repaint();
  }

  /**
   * update content of the right panel. Repaint the detail label and entity list
   * using currently selected field.
   * 
   * @param field
   *          currently selected field
   */
  public void update(Field field) {
    if (field != null) {
      descField.setText("selected: " + field.getType().getName() + " entities: " + field.getMovableEntities().size());
    } else {
      descField.setText("");
    }

    detail.draw(field);

    entityList.removeAll();
    for (Entity ent : getEntitiesToDraw(field)) {
      JComponent item = new JTextField(ent.getType().getName() + " - " + ent.getPropAsString("name"));
      item.setSize(new Dimension(3 * Const.TILE_WIDTH, 30));
      item.setMinimumSize(new Dimension(3 * Const.TILE_WIDTH, 30));
      item.setMaximumSize(new Dimension(3 * Const.TILE_WIDTH, 30));
      item.setPreferredSize(new Dimension(3 * Const.TILE_WIDTH, 30));

      String name = ent.getPropAsString("name");
      String offspring = ent.getPropAsString("gender").equals("male") ? "son" : "daughter";
      String father = ent.getPropAsString("father");
      String mother = ent.getPropAsString("mother");
      if (!ent.getPropAsString("father").isEmpty() && !ent.getPropAsString("mother").isEmpty()) {
        item.setToolTipText(String.format("%s, %s of %s and %s", name, offspring, father, mother));
      } else {
        item.setToolTipText(String.format("%s, the %s", name, ent.getType() == EntityType.ADAM ? "Ancestor" : "Ancestress"));
      }

      entityList.add(item);
    }

    this.repaint();
  }

  /**
   * null-safe way to get movable entities from a field.
   * 
   * @param field
   *          field
   * @return movable entities of the field
   */
  private Collection<Entity> getEntitiesToDraw(Field field) {
    if (field != null) {
      return field.getMovableEntities();
    } else {
      return new ArrayList<Entity>(0);
    }
  }

}