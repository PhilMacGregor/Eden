package cz.macgregor.eden.grf.components.top;

import cz.macgregor.eden.core.logic.indicator.Indicator;

/**
 * resource indicators used to display on the top panel.
 * 
 * @author MacGregor
 *
 */
public enum ValueIndicators implements Indicator {
	/** wood amount. */
	WOOD("img.icons.resources.logs.png", "Døevo"),
	/** world population. */
	POPULATION("img.icons.resources.population.png", "Populace"),
	/** number of fields explored so far. */
	MAP("img.icons.resources.map.png", "Prozkoumané území");

	/** the indicator. */
	private ValueIndicator indicator;

	/**
	 * constructor.
	 * 
	 * @param icon
	 *            icon qualified name
	 * @param name
	 *            tooltip
	 */
	private ValueIndicators(String icon, String name) {
		this.indicator = new ValueIndicator(icon, name);
	}

	public ValueIndicator getIndicator() {
		return indicator;
	}

	@Override
	public void update(int value) {
		indicator.update(value);
	}

	@Override
	public void reset() {
		indicator.reset();
	}

	@Override
	public int getValue() {
		return indicator.getValue();
	}

}
