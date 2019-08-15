package cz.macgregor.eden.grf.components.bottom;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * A panel used to display some development information.
 * 
 * @author MacGregor
 *
 */
public class BottomPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	/**
	 * text feld for the debug info.
	 */
	private final JTextField infoTextField;

	/**
	 * constructor.
	 */
	public BottomPanel() {
		super();
		this.infoTextField = new JTextField();
		infoTextField.setText("debug info");
		infoTextField.setEditable(false);
		infoTextField.setPreferredSize(new Dimension(600, 30));
		this.add(infoTextField);
	}

	/**
	 * update the text in infoTextField.
	 * 
	 * @param show
	 *            text to show
	 */
	public void showInfo(String show) {
		infoTextField.setText(show);
	}
}
