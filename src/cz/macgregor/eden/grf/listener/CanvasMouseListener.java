package cz.macgregor.eden.grf.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityUtils;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.components.canvas.CanvasLabel;
import cz.macgregor.eden.util.Const;

/**
 * mouse listener for the canvas. Can select a field and execute a specific
 * action on it.
 */
public class CanvasMouseListener implements MouseListener {
	/** canvas to be used. */
	private CanvasLabel label;
	/** field selected. */
	private Field selectedField;

	/**
	 * constructor.
	 * 
	 * @param parent
	 *            parent label
	 */
	public CanvasMouseListener(CanvasLabel parent) {
		this.label = parent;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// do nothing

	}

	@Override
	public void mousePressed(MouseEvent e) {
		selectFieldAction(e);

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// do nothing
	}

	/**
	 * select field that has been clicked on.
	 * 
	 * @param e
	 *            mouse event
	 */
	private void selectFieldAction(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		Field fieldClicked = findFieldByCoords(x, y);
		if (fieldClicked == null) {
			return;
		}

		if (this.selectedField != null) {
			this.selectedField.setSelected(false);

			if (!fieldClicked.equals(selectedField)) {

				List<Entity> movableEntities = selectedField.getMovableEntities();

				for (Entity ent : movableEntities) {
					EntityUtils.moveEntity(ent, fieldClicked);
				}

			}

			this.selectedField = null;

		} else {
			fieldClicked.setSelected(!fieldClicked.isSelected());
			this.selectedField = fieldClicked;
		}

		label.repaint();
	}

	/**
	 * find a field by given coordinates from the label.
	 * 
	 * @param x
	 *            x
	 * @param y
	 *            y
	 * @return field
	 */
	private Field findFieldByCoords(int x, int y) {
		Point focusPoint = label.getFocusPoint();
		Point canvasSize = label.getCanvasSize();

		int fromX = focusPoint.x - (canvasSize.x / 2);
		int fromY = focusPoint.y - (canvasSize.y / 2);

		int indexOfX = x / Const.TILE_WIDTH + fromX;
		int indexOfY = y / Const.TILE_HEIGHT + fromY;

		return label.getMap().get(indexOfX, indexOfY).getField();
	}

}
