package cz.macgregor.eden.core.logic.indicator;

public interface Indicator {

	public void update(int value);

	public void reset();

	public int getValue();

}
