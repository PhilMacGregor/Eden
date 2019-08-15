package cz.macgregor.eden.grf.components.top;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.Objects;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import cz.macgregor.eden.core.logic.Sprites;
import cz.macgregor.eden.core.logic.indicator.Indicator;

/**
 * indicator used to display an information about single resource amount with
 * its icon and tooltip.
 * 
 * @author MacGregor
 *
 */
public class ValueIndicator extends JPanel implements Indicator {
	private static final long serialVersionUID = 1L;

	/** label to display the icon. */
	private final JLabel imageLabel;
	/** text field to display the value. */
	private final JTextField valueField;

	/**
	 * value to be displayed.
	 */
	private int value;

	/**
	 * constructor.
	 * 
	 * @param icon
	 *            icon qualified name.
	 * @param name
	 *            tooltip
	 */
	public ValueIndicator(String icon, String name) {
		ImageIcon img = Sprites.image(icon);

		this.imageLabel = new IconLabel(img);
		this.valueField = new JTextField();
		valueField.setEditable(false);
		valueField.setPreferredSize(new Dimension(img.getIconWidth() * 2, img.getIconHeight()));

		this.add(imageLabel);
		this.add(valueField);
		this.setToolTipText(name);

		this.reset();
		this.repaint();
	}

	@Override
	public void update(int value) {
		this.value += value;
		this.valueField.setText(Objects.toString(this.value));
	}

	@Override
	public void reset() {
		this.value = 0;
		this.valueField.setText(Objects.toString(this.value));
	}

	@Override
	public int getValue() {
		return value;
	}

	/**
	 * label for the icon.sb
	 * 
	 * @author MacGregor
	 *
	 */
	private class IconLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		/**
		 * the icon to display.
		 */
		private final ImageIcon icon;

		/**
		 * constructor.
		 * 
		 * @param icon
		 *            icon
		 */
		private IconLabel(ImageIcon icon) {
			this.icon = icon;
			this.setPreferredSize(new Dimension(icon.getIconWidth(), icon.getIconHeight()));
			repaint();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(icon.getImage(), 0, 0, null);
		}
	}

}
