package cz.macgregor.eden.core.logic.tiles;

import java.awt.Point;

/**
 * field info. Placeholder for the field containing the field itself if
 * generated. May also contain some context information in the future.
 * 
 * @author MacGregor
 *
 */
public class FieldInfo {
	/** position in the map. */
	private Point position;
	/** the field. */
	private Field field;

	/**
	 * constructor.
	 * 
	 * @param position
	 *            position
	 */
	public FieldInfo(Point position) {
		this.position = position;
	}

	public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		this.field = field;
		this.field.setPosition(this);
	}

}
