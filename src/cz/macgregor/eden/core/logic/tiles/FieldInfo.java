package cz.macgregor.eden.core.logic.tiles;

import java.awt.Point;

public class FieldInfo {
	private Point position;
	private Field field;
	
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
