package cz.macgregor.eden.util.namegen;

import java.util.Random;

/**
 * standard name generator. Generates up to given amount of syllables created
 * with given chars.
 * 
 * @author MacGregor
 *
 */
public class StandardNameGenerator implements NameGenerator {
	/** maximum syllables to generate. */
	private int syllables;

	/** possible beginning chars of the syllable. */
	private char[] pre;
	/** possible middle chars of the syllable. */
	private char[] mid;
	/** possible end chars of the syllable. */
	private char[] post;

	/** random generator. */
	private Random random;

	/**
	 * constructor.
	 * 
	 * @param pre
	 *            possible beginning chars of the syllable
	 * @param mid
	 *            possible middle chars of the syllable
	 * @param post
	 *            possible end chars of the syllable
	 * @param syllables
	 *            maximum syllables to generate
	 */
	public StandardNameGenerator(char[] pre, char[] mid, char[] post, int syllables) {
		this.pre = pre;
		this.mid = mid;
		this.post = post;
		this.syllables = syllables;

		this.random = new Random();
	}

	@Override
	public String generate() {
		int howMany = random.nextInt(syllables) + 1;
		String name = "";

		for (int i = howMany; i > 0; i--) {
			name += pickChar(pre);
			name += pickChar(mid);
			name += pickChar(post);
		}

		return name.replaceAll("\\W", "");
	}

	/**
	 * pick a random char from given array.
	 * 
	 * @param chars
	 *            char array
	 * @return random char from the array
	 */
	private char pickChar(char[] chars) {
		return chars[random.nextInt(chars.length)];
	}

}
