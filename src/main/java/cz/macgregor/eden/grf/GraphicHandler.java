package cz.macgregor.eden.grf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.UIManager;

import cz.macgregor.eden.core.GameHandler;
import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.entities.DrawTarget;
import cz.macgregor.eden.core.logic.entities.DrawTarget.Direction;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.components.bottom.BottomPanel;
import cz.macgregor.eden.grf.components.canvas.CanvasLabel;
import cz.macgregor.eden.grf.components.drawer.CenterDrawer;
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;
import cz.macgregor.eden.grf.components.drawer.RandomDrawer;
import cz.macgregor.eden.grf.components.drawer.SimpleDrawer;
import cz.macgregor.eden.grf.components.right.RightPanel;
import cz.macgregor.eden.grf.components.top.TopPanel;
import cz.macgregor.eden.grf.listener.CanvasKeyListener;
import cz.macgregor.eden.grf.listener.CanvasMouseListener;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

/**
 * class to control the game graphics.
 * 
 * @author MacGregor
 *
 */
public class GraphicHandler {
  /**
   * default entity offset for the simpleDrawers that draw the entities with
   * fixed positions at the center, sides or corners.
   */
  private static final int ENTITY_DEFAULT_OFFSET = 5;

  /** JLabel where the game map will be displayed. */
  private final CanvasLabel canvasLabel;
  /** bottom panel with context information. */
  private final BottomPanel bottomPanel;
  /** top panel with context information. */
  private final TopPanel topPanel;
  /** right panel for unit selection and info. */
  private final RightPanel rightPanel;
  /** focus point for the canvas label. */
  private Point focusPoint;
  /** application window. */
  private final JFrame frame;

  /** field selected by the mouse listener. */
  private Field selectedField;

  /** debug mode. */
  private boolean debugMode;

  /**
   * drawers used for drawing entities. Each position has a drawer to be used
   * and each entity type has a position to be drawn at.
   */
  private Map<Direction, EntityDrawer> positionDrawers;

  /**
   * constructor. Creates the window with appropriate component. Its size will
   * be computed from canvasWidth * width of a single map field and canvasHeight
   * * height of a single field.
   * 
   * @param canvasWidth
   *          canvas width in the number of displayed fields in a map
   * @param canvasHeight
   *          canvas height in the number of displayed fields in a map
   * @param gameHandler
   *          game handler
   */
  public GraphicHandler(int canvasWidth, int canvasHeight, GameHandler gameHandler) {
    initEntityDrawers();

    try {
      UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
    } catch (Throwable t) {
      throw new RuntimeException("Failed to set the look and feel.", t);
    }

    this.setDebugMode(Const.SCREEN_DEBUG);

    this.frame = new JFrame(Const.APP_NAME);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Dimension canvasSize = new Dimension(canvasWidth * Const.TILE_WIDTH, canvasHeight * Const.TILE_HEIGHT);
    this.canvasLabel = new CanvasLabel(canvasWidth, canvasHeight, positionDrawers);
    canvasLabel.setPreferredSize(canvasSize);
    canvasLabel.addKeyListener(new CanvasKeyListener(gameHandler));
    canvasLabel.setFocusable(true);
    canvasLabel.addMouseListener(new CanvasMouseListener(canvasLabel, this));

    this.bottomPanel = new BottomPanel();
    this.topPanel = new TopPanel();

    this.rightPanel = new RightPanel(positionDrawers);
    rightPanel.setPreferredSize(new Dimension(300, canvasHeight * Const.TILE_HEIGHT));

    this.focusPoint = new Point(0, 0);

    frame.add(canvasLabel, BorderLayout.CENTER);
    frame.add(topPanel, BorderLayout.NORTH);
    frame.add(bottomPanel, BorderLayout.SOUTH);
    frame.add(rightPanel, BorderLayout.EAST);
    frame.setResizable(false);
    frame.pack();

    rightPanel.update(selectedField);

    canvasLabel.requestFocus();

    Utils.moveToCenter(frame);
  }

  /**
   * get map focus point.
   * 
   * @return focus point
   */
  public Point getFocusPoint() {
    return focusPoint;
  }

  /**
   * set map focus point.
   * 
   * @param focusPoint
   *          focus point
   */
  public void setFocusPoint(Point focusPoint) {
    this.focusPoint = focusPoint;
  }

  /**
   * getter.
   * 
   * @return the debugMode
   */
  public boolean isDebugMode() {
    return debugMode;
  }

  /**
   * setter.
   * 
   * @param debugMode
   *          the debugMode to set
   */
  public void setDebugMode(boolean debugMode) {
    this.debugMode = debugMode;
  }

  /**
   * getter.
   * 
   * @return the selectedField
   */
  public Field getSelectedField() {
    return selectedField;
  }

  /**
   * setter.
   * 
   * @param selectedField
   *          the selectedField to set
   */
  public void setSelectedField(Field selectedField) {
    this.selectedField = selectedField;
    this.rightPanel.update(selectedField);
  }

  /**
   * start the initialised graphics. Set the application window to visible.
   */
  public void start() {
    frame.setVisible(true);
  }

  /**
   * repaint the canvas label to show a given map from the set focus point.
   * 
   * @param map
   *          map to show
   */
  public void paint(GameMap map) {
    canvasLabel.repaint(map, focusPoint, debugMode);
  }

  /**
   * Initialize entity drawers. For each entity that could be drawed, there is a
   * need to set its layer (ground to clouds) and position to be drawed at
   * relative to the field it occupies.
   */
  private void initEntityDrawers() {
    DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DWELLER_COUNT, Direction.NW);

    DrawTarget.LAYER_GROUND.addEntity(EntityType.ADAM, Direction.RANDOM);
    DrawTarget.LAYER_GROUND.addEntity(EntityType.EVE, Direction.RANDOM);
    DrawTarget.LAYER_GROUND.addEntity(EntityType.SON, Direction.RANDOM);
    DrawTarget.LAYER_GROUND.addEntity(EntityType.DAUGHTER, Direction.RANDOM);
    DrawTarget.LAYER_GROUND.addEntity(EntityType.GRANDPA, Direction.RANDOM);
    DrawTarget.LAYER_GROUND.addEntity(EntityType.GRANDMA, Direction.RANDOM);

    DrawTarget.LAYER_GROUND.addEntity(EntityType.BUILDING, Direction.RANDOM);

    DrawTarget.LAYER_GROUND.addEntity(EntityType.PINETREE, Direction.RANDOM);
    DrawTarget.LAYER_GROUND.addEntity(EntityType.MOUNTAIN, Direction.RANDOM);

    this.positionDrawers = new HashMap<>();
    positionDrawers.put(Direction.CENTER, new CenterDrawer(Const.TILE_WIDTH / 2, Const.TILE_HEIGHT / 2, 8));
    positionDrawers.put(Direction.NW, new SimpleDrawer(0, 0, ENTITY_DEFAULT_OFFSET));
    positionDrawers.put(Direction.SW, new SimpleDrawer(0, Const.TILE_HEIGHT, ENTITY_DEFAULT_OFFSET));
    positionDrawers.put(Direction.SE, new SimpleDrawer(Const.TILE_WIDTH, Const.TILE_HEIGHT, -ENTITY_DEFAULT_OFFSET));
    positionDrawers.put(Direction.NE, new SimpleDrawer(Const.TILE_WIDTH, 0, -ENTITY_DEFAULT_OFFSET));

    positionDrawers.put(Direction.RANDOM, new RandomDrawer());

  }

  /**
   * This method is called at the beginning of each turn.
   * 
   * @param turnCount
   *          turns elapsed since the game start
   */
  public void turnAction(int turnCount) {
    bottomPanel.showInfo("DEBUG: kolo " + turnCount + ".");
  }

}
