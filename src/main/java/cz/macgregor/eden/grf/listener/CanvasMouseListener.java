package cz.macgregor.eden.grf.listener;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import cz.macgregor.eden.core.logic.entities.Entity;
import cz.macgregor.eden.core.logic.entities.EntityUtils;
import cz.macgregor.eden.core.logic.tiles.Field;
import cz.macgregor.eden.grf.GraphicHandler;
import cz.macgregor.eden.grf.components.canvas.CanvasLabel;
import cz.macgregor.eden.util.Const;

/**
 * mouse listener for the canvas. Can select a field and execute a specific
 * action on it.
 */
public class CanvasMouseListener implements MouseListener {
	/** canvas to be used. */
	private CanvasLabel label;
	/** graphic handler to store the selected field. */
	private GraphicHandler graphicHandler;

	/**
	 * constructor.
	 * 
	 * @param parent
	 *            parent label
	 * @param grfHandler
	 *            graphic handler
	 */
	public CanvasMouseListener(CanvasLabel parent, GraphicHandler grfHandler) {
		this.label = parent;
		this.graphicHandler = grfHandler;
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
		label.requestFocus();
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

		if (graphicHandler.getSelectedField() != null) {
			graphicHandler.getSelectedField().setSelected(false);

			List<Entity> movableEntities = graphicHandler.getSelectedField().getMovableEntities();

			if (movableEntities.isEmpty()) {
				graphicHandler.setSelectedField(fieldClicked);
				fieldClicked.setSelected(!fieldClicked.isSelected());
			} else {

				for (Entity ent : movableEntities) {
					EntityUtils.moveEntity(ent, fieldClicked);
				}

				graphicHandler.setSelectedField(null);

			}

		} else {
			fieldClicked.setSelected(!fieldClicked.isSelected());
			graphicHandler.setSelectedField(fieldClicked);
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
