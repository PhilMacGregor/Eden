package cz.macgregor.eden.grf;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;

import cz.macgregor.eden.core.GameHandler;
import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.entities.DrawTarget;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.entities.DrawTarget.Direction;
import cz.macgregor.eden.grf.components.BottomPanel;
import cz.macgregor.eden.grf.components.CanvasLabel;
import cz.macgregor.eden.grf.components.drawer.CenterDrawer;
import cz.macgregor.eden.grf.components.drawer.EntityDrawer;
import cz.macgregor.eden.grf.components.drawer.RandomDrawer;
import cz.macgregor.eden.grf.components.drawer.SimpleDrawer;
import cz.macgregor.eden.grf.listener.CanvasKeyListener;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

/**
 * class to control the game graphics.
 * 
 * @author MacGregor
 *
 */
public class GraphicHandler {
	private static final int ENTITY_DEFAULT_OFFSET = 5;

	/** JLabel where the game map will be displayed. */
	private final CanvasLabel canvasLabel;
	/** bottom panel with context information. */
	private final BottomPanel	bottomPanel;
	/** focus point for the canvas label. */
	private Point focusPoint;
	/** application window. */
	private final JFrame			frame;
	/** debug mode. */
	private boolean						debugMode;
	
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
		
		this.setDebugMode(Const.SCREEN_DEBUG);

		this.frame = new JFrame(Const.APP_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Dimension canvasSize = new Dimension(canvasWidth * Const.TILE_WIDTH, canvasHeight * Const.TILE_HEIGHT);
		this.canvasLabel = new CanvasLabel(canvasWidth, canvasHeight, positionDrawers);
		canvasLabel.setPreferredSize(canvasSize);
		canvasLabel.addKeyListener(new CanvasKeyListener(gameHandler));
		canvasLabel.setFocusable(true);
		
		this.bottomPanel = new BottomPanel();

		this.focusPoint = new Point(0, 0);
		
		frame.add(canvasLabel, BorderLayout.CENTER);
		frame.add(bottomPanel, BorderLayout.SOUTH);
		frame.pack();
		frame.setResizable(false);
		
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
	 * @return the debugMode
	 */
	public boolean isDebugMode() {
		return debugMode;
	}

	/**
	 * @param debugMode
	 *          the debugMode to set
	 */
	public void setDebugMode(boolean debugMode) {
		this.debugMode = debugMode;
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
	
	private void initEntityDrawers() {
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_CENTER, Direction.CENTER);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DWELLER_COUNT, Direction.NW);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_NW, Direction.NW);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_SW, Direction.SW);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_SE, Direction.SE);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_NE, Direction.NE);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_N, Direction.N);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_S, Direction.S);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_E, Direction.E);
		DrawTarget.LAYER_CLOUDS.addEntity(EntityType.DEBUG_W, Direction.W);

		DrawTarget.LAYER_BIO.addEntity(EntityType.ADAM, Direction.CENTER);
		DrawTarget.LAYER_BIO.addEntity(EntityType.EVE, Direction.CENTER);
		DrawTarget.LAYER_BIO.addEntity(EntityType.SETH, Direction.CENTER);

		DrawTarget.LAYER_STRUCT.addEntity(EntityType.BUILDING, Direction.CENTER);

		DrawTarget.LAYER_GROUND.addEntity(EntityType.PINETREE, Direction.RANDOM);
		DrawTarget.LAYER_GROUND.addEntity(EntityType.MOUNTAIN, Direction.RANDOM);

		this.positionDrawers = new HashMap<>();
		positionDrawers.put(Direction.CENTER, new CenterDrawer(Const.TILE_WIDTH / 2, Const.TILE_HEIGHT / 2, 8));
		positionDrawers.put(Direction.NW, new SimpleDrawer(0, 0, ENTITY_DEFAULT_OFFSET));
		positionDrawers.put(Direction.SW, new SimpleDrawer(0, Const.TILE_HEIGHT, ENTITY_DEFAULT_OFFSET));
		positionDrawers.put(Direction.SE,
				new SimpleDrawer(Const.TILE_WIDTH, Const.TILE_HEIGHT, -ENTITY_DEFAULT_OFFSET));
		positionDrawers.put(Direction.NE, new SimpleDrawer(Const.TILE_WIDTH, 0, -ENTITY_DEFAULT_OFFSET));

		positionDrawers.put(Direction.RANDOM, new RandomDrawer());

	}
	
	public void turnAction(int turnCount) {
		bottomPanel.showInfo("DEBUG: kolo " + turnCount + ".");
	}

}
