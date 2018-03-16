package cz.macgregor.eden.grf.components.bottom;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class BottomPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField infoTextField;

	public BottomPanel() {
		super();
		this.infoTextField = new JTextField();
		infoTextField.setText("debug info");
		infoTextField.setEditable(false);
		infoTextField.setPreferredSize(new Dimension(600, 30));
		this.add(infoTextField);
	}
	
	public void showInfo(String show) {
		infoTextField.setText(show);
	}
}
