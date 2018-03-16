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

public class ValueIndicator extends JPanel implements Indicator {
	private static final long serialVersionUID = 1L;

	private final JLabel			imageLabel;
	private final JTextField	valueField;

	private int value;

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

	private class IconLabel extends JLabel {
		private static final long serialVersionUID = 1L;

		private final ImageIcon icon;

		private IconLabel(ImageIcon	icon) {
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
