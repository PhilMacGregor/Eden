package cz.macgregor.eden.grf.components.top;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTextField;

public class TopPanel extends JPanel {
	private static final long serialVersionUID = 1L;

	private final JTextField infoTextField;

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

	public void showInfo(String show) {
		infoTextField.setText(show);
	}

}
