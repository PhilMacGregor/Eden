package cz.macgregor.eden.core.logic.indicator;

/** indicator to display some value on the top menu. */
public interface Indicator {

	/**
	 * update the indicator. Set its value to the given one.
	 * 
	 * @param value
	 *            value
	 */
	public void update(int value);

	/**
	 * set indicator's value to default.
	 */
	public void reset();

	/**
	 * get indicator's value.
	 * 
	 * @return value
	 */
	public int getValue();

}
