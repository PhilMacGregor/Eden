package cz.macgregor.eden.util;

import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public final class Utils {

	public static void moveToCenter(JFrame frame) {
		Point stred = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
		stred.move(stred.x - (frame.getSize().width / 2), stred.y - (frame.getSize().height / 2));
		frame.setLocation(stred);
	}

	public static ImageIcon loadIcon(String path) throws FileNotFoundException {
		URL url = Utils.class.getClassLoader().getResource(path);

		if (url == null) {
			throw new FileNotFoundException(String.format("Resource not found: %s", path));
		}
		
		ImageIcon ico = new ImageIcon(url);
		return ico;
	}

	public static int randomInt(int min, int max) {
		Random randy = new Random();

		int bound = max - min;

		return randy.nextInt(bound) + min;
	}

}
