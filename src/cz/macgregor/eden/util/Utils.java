package cz.macgregor.eden.util;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.util.Random;

import javax.swing.JFrame;

/**
 * utility functions.
 * 
 * @author MacGregor
 *
 */
public final class Utils {

	/**
	 * move the frame to the center of the screen.
	 * 
	 * @param frame
	 *            frame
	 */
	public static void moveToCenter(JFrame frame) {
		Point stred = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		stred.move(stred.x - (frame.getSize().width / 2), stred.y - (frame.getSize().height / 2));
		frame.setLocation(stred);
	}

	/**
	 * random in from min to max.
	 * 
	 * @param min
	 *            min
	 * @param max
	 *            max
	 * @return random.nextInt(max - min) + min;
	 */
	public static int randomInt(int min, int max) {
		Random randy = new Random();

		int bound = max - min;

		return randy.nextInt(bound) + min;
	}

}
