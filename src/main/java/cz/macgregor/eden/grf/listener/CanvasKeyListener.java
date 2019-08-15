package cz.macgregor.eden.grf.listener;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import cz.macgregor.eden.core.GameHandler;

/**
 * keyboard listener for use by the canvas.
 * 
 * @author MacGregor
 *
 */
public class CanvasKeyListener implements KeyListener {
	/** game handler to be influenced. */
	private final GameHandler handler;

	/**
	 * constructor.
	 * 
	 * @param handler
	 *            game handler
	 */
	public CanvasKeyListener(GameHandler handler) {
		this.handler = handler;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		Point originPoint = handler.getGraphics().getFocusPoint();

		switch (e.getKeyCode()) {

		case KeyEvent.VK_LEFT:
			handler.getGraphics().setFocusPoint(new Point(originPoint.x - 1, originPoint.y));
			break;
		case KeyEvent.VK_RIGHT:
			handler.getGraphics().setFocusPoint(new Point(originPoint.x + 1, originPoint.y));
			break;
		case KeyEvent.VK_UP:
			handler.getGraphics().setFocusPoint(new Point(originPoint.x, originPoint.y - 1));
			break;
		case KeyEvent.VK_DOWN:
			handler.getGraphics().setFocusPoint(new Point(originPoint.x, originPoint.y + 1));
			break;
		case KeyEvent.VK_F5:
			handler.reset();
			break;
		case KeyEvent.VK_F3:
			handler.getGraphics().setDebugMode(!handler.getGraphics().isDebugMode());
			break;
		case KeyEvent.VK_SPACE:
			handler.newTurn();
			break;
		default:
			break;
		}
		handler.getGraphics().paint(handler.getMap());

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// do nothing
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}

}
