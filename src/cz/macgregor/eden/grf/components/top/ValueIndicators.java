package cz.macgregor.eden.grf.components.top;

import cz.macgregor.eden.core.logic.indicator.Indicator;

public enum ValueIndicators implements Indicator {
	WOOD("img.icons.resources.logs.png", "Dřevo"), POPULATION("img.icons.resources.population.png",
	    "Populace"), MAP("img.icons.resources.map.png", "Prozkoumané území");

	private ValueIndicator indicator;

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
