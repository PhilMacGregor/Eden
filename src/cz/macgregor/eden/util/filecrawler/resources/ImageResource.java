package cz.macgregor.eden.util.filecrawler.resources;

import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.ImageIcon;

import cz.macgregor.eden.util.filecrawler.ResourceEntry;

/**
 * resource for the images.
 * 
 * @author MacGregor
 *
 */
public class ImageResource extends ResourceEntry<ImageIcon> {

	@Override
	public ImageIcon getResource() {
		try {
			URL url = this.getFile().toURI().toURL();
			return new ImageIcon(url);

		} catch (MalformedURLException e) {
			throw new RuntimeException("Unable to load an image: " + this.getQualifiedName(), e);
		}

	}

}
