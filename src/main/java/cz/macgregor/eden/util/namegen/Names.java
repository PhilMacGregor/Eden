package cz.macgregor.eden.util.namegen;

/**
 * random names generator. Generates names of given length by syllables and of
 * given gender.
 * 
 * @author MacGregor
 *
 */
public class Names {
	/** constant for male names generation. */
	public static final boolean MALE = true;
	/** constant for female names generation. */
	public static final boolean FEMALE = false;

	/**
	 * chars that can be used as the first letter of a syllable.
	 */
	private static final char[] PRE = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'v',
			'w', 'z', '\u0000' };
	/**
	 * chars used in the middle of the syllable.
	 */
	private static final char[] MID = { 'a', 'e', 'i', 'o', 'u', 'y' };
	/**
	 * chars used at the end of the syllable.
	 */
	private static final char[] POST = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't', 'v',
			'z', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000', '\u0000', '\u0000', '\u0000' };

	/**
	 * female-specific mid-chars.
	 */
	private static final char[] MID_F = { 'a', 'a', 'a', 'a', 'e', 'i', 'i', 'o', 'o' };
	/**
	 * female-specific post-chars.
	 */
	private static final char[] POST_F = { '\u0000' };
	/**
	 * male specific post-chars.
	 */
	private static final char[] POST_M = { 'b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm', 'n', 'p', 'r', 's', 't',
			'v', 'z', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000', '\u0000',
			'\u0000', '\u0000' };

	/**
	 * maximum syllables per generated name.
	 */
	private static final int SYLLABLES = 4;

	/**
	 * NameGenerator instance used to generate male names end syllable.
	 */
	private static NameGenerator maleGen;
	/**
	 * NameGenerator instance used to generate female names end syllable.
	 */
	private static NameGenerator femaleGen;
	/**
	 * basic NameGenerator instance. Generates the whole name but the last
	 * syllable, which is gender-specific.
	 */
	private static NameGenerator baseGen;

	/**
	 * constructor.
	 */
	public Names() {
		baseGen = new StandardNameGenerator(PRE, MID, POST, SYLLABLES - 1);
		maleGen = new StandardNameGenerator(PRE, MID, POST_M, 1);
		femaleGen = new StandardNameGenerator(PRE, MID_F, POST_F, 1);
	}

	/**
	 * generate the name.
	 * 
	 * @param gender
	 *            gender
	 * @return generated name based on syllables count and gender.
	 */
	public String generate(boolean gender) {
		String base = baseGen.generate();
		base = base.substring(0, 1).toUpperCase() + base.substring(1);

		NameGenerator addGen = gender == MALE ? maleGen : femaleGen;
		return base + addGen.generate();
	}

}
