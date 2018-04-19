package cz.macgregor.eden.grf.components.top;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * JPanel to display resource amounts of the game and other context information.
 * 
 * @author MacGregor
 *
 */
public class TopPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/** text field to display info text. */
	private final JTextField infoTextField;

	/**
	 * constructor.
	 */
	public TopPanel() {
		super();
		this.infoTextField = new JTextField();
		infoTextField.setText("tady budou suroviny a další užiteèné blbosti");
		infoTextField.setEditable(false);
		infoTextField.setPreferredSize(new Dimension(600, 40));
		this.add(infoTextField);

		this.add(ValueIndicators.WOOD.getIndicator());
		this.add(ValueIndicators.POPULATION.getIndicator());
		this.add(ValueIndicators.MAP.getIndicator());
	}

	/**
	 * set the infoTextField - change its text to the given one.
	 * 
	 * @param show
	 *            text to show
	 */
	public void showInfo(String show) {
		infoTextField.setText(show);
	}

}
