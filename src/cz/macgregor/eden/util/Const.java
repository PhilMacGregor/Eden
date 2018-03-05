package cz.macgregor.eden.util;

public final class Const {

	/** this will be a title of the window. */
	public static final String APP_NAME = "Eden";
	
	/** tile default width in pixels. */
	public static final int	TILE_WIDTH	= 80;
	/** tile default height in pixels. */
	public static final int	TILE_HEIGHT	= 60;
	
	/** tile canvas width in tiles. */
	public static final int CANVAS_WIDTH = 15;
	/** tile canvas height in tiles. */
	public static final int CANVAS_HEIGHT = 10;
	
	/** if this is true, display debug features on screen. */
	public static final boolean SCREEN_DEBUG = false;
	
	/** maximum entities to be displayed on single field and position. If higher counts of entities are present, display them using a number instead. */
	public static final int ENT_MAX_DISPLAYED = 3;

	/**
	 * probability for a map generator to create a random new Field or a copy of a
	 * neighbouring field. ([1, 0] - always random; [0, 1] - always copy; [<=0,
	 * <=0] create a black hole and destroy the entire universe)
	 */
	public static final int[] MAP_GENERATION_DISTORTION_RATE = new int[] { 1, 20 };

}
