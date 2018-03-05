package cz.macgregor.eden.core;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import cz.macgregor.eden.core.logic.GameMap;
import cz.macgregor.eden.core.logic.MapGenerator;
import cz.macgregor.eden.core.logic.Sprites;
import cz.macgregor.eden.core.logic.actions.ActionHolder;
import cz.macgregor.eden.core.logic.actions.TriggerType;
import cz.macgregor.eden.core.logic.entities.EntityFactory;
import cz.macgregor.eden.core.logic.entities.EntityType;
import cz.macgregor.eden.core.logic.patterns.DiamondPattern;
import cz.macgregor.eden.core.logic.patterns.RectanglePattern;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.core.logic.tiles.FieldInfo;
import cz.macgregor.eden.core.logic.tiles.TileFactory;
import cz.macgregor.eden.core.logic.tiles.TileType;
import cz.macgregor.eden.grf.GraphicHandler;
import cz.macgregor.eden.util.Const;
import cz.macgregor.eden.util.Utils;

/**
 * Class used to control the game logic, hold the game map and graphics.
 * 
 * @author MacGregor
 *
 */
public class GameHandler {
	private static final int INITIAL_MAP_SIZE = 5;

	/** game graphics handler. */
	private final GraphicHandler graphics;
	/** game map. */
	private GameMap map;
	/** map generator. */
	private MapGenerator mapGen;
	
	private int turnCount;

	/**
	 * constructor. Initializes the graphic controller which will be kept even
	 * if the game handler will be reset.
	 */
	public GameHandler() {
		this.graphics = new GraphicHandler(Const.CANVAS_WIDTH, Const.CANVAS_HEIGHT, this);
		init();
	}

	/**
	 * initialisation method. Creates a game map and map generator. Then
	 * generates the map.
	 */
	private void init() {
		Sprites.getInstance();
		
		this.map = new GameMap();
		this.mapGen = new MapGenerator(map);

		Point startPoint = new Point(0, 0);

		map.put(startPoint, TileFactory.newField(TileType.ORIGIN));
		mapGen.fillPattern(new DiamondPattern(), startPoint, new Point(2, 2), TileFactory.newField(TileType.GRASS));

		mapGen.fillPattern(new RectanglePattern(), startPoint, new Point(INITIAL_MAP_SIZE, INITIAL_MAP_SIZE));

		map.get(1, 0).getField().addEntity(EntityFactory.newEntity(EntityType.ADAM));
		map.get(-1, 0).getField().addEntity(EntityFactory.newEntity(EntityType.EVE));

//		map.get(1, 1).getField().addEntity(EntityFactory.newEntity(EntityType.BUILDING));
		
//		addTestEntites();
		
		this.turnCount = 0;
		
		ActionHolder.activateTrigger(TriggerType.GAME_START);
		graphics.turnAction(turnCount);
		graphics.paint(map);
	}

	@SuppressWarnings("unused")
	private void addTestEntites() {
		int nrOfEntities = map.getMap().size();
		
		List<EntityType> entsToPlace = new ArrayList<>();
		entsToPlace.add(EntityType.DEBUG_NW);
		entsToPlace.add(EntityType.ADAM);
		entsToPlace.add(EntityType.EVE);
		entsToPlace.add(EntityType.SETH);
		entsToPlace.add(EntityType.BUILDING);
 
		System.out.printf("Generating %d entities.\n", nrOfEntities);

		FieldInfo[] fldInfos = map.getMap().values().toArray(new FieldInfo[map.getMap().values().size()]);
		
		while (nrOfEntities > 0) {
			Field fieldToPopulate = fldInfos[Utils.randomInt(0, fldInfos.length - 1)].getField();
			
			if (fieldToPopulate != null && fieldToPopulate.getType().getDefaultProps().isCanBuild()) {
				fieldToPopulate.addEntity(EntityFactory.newEntity(entsToPlace.get(Utils.randomInt(0, entsToPlace.size()))));
				
				nrOfEntities--;
				
				System.out.printf("Adding entity to: %d; %d. %d entities remaining to add.\n", fieldToPopulate.getCoords().x, fieldToPopulate.getCoords().y, nrOfEntities);
			}
		}
	}

	/**
	 * reset method. Will result in creating new map and map generator.
	 */
	public void reset() {
		ActionHolder.reset();
		graphics.setFocusPoint(new Point(0, 0));
		init();
	}

	/**
	 * starting method, called after initialisation is complete. Starts the
	 * graphics handler calls for paint.
	 */
	public void start() {
		graphics.start();
		graphics.paint(map);
	}

	/**
	 * get graphic handler.
	 * 
	 * @return graphic handler
	 */
	public GraphicHandler getGraphics() {
		return graphics;
	}

	/**
	 * get map.
	 * 
	 * @return map
	 */
	public GameMap getMap() {
		return map;
	}
	
	/**
	 * Do action for the new turn.
	 */
	public void newTurn() {
		ActionHolder.activateTrigger(TriggerType.TURN_START);
		
		graphics.turnAction(turnCount);
		graphics.paint(map);
		
		ActionHolder.activateTrigger(TriggerType.TURN_END);
		
		turnCount++;
	}

}
