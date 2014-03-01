package com.apprentice.rpg.model.weapon;

import java.util.StringTokenizer;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * How far can a weapon be thrown/launched? Has 3 increments: close, medium, long
 * 
 * @author theoklitos
 * 
 */
public final class Range {

	private final static String RANGE_CATEGORIES_DELIMINATOR = "/";

	private int shortRange;
	private int mediumRange;
	private int longRange;

	/**
	 * @throws ApprenticeEx
	 *             if we have anything other than shortRange < mediumRange < longRange
	 */
	public Range(final int shortRange, final int mediumRange, final int longRange) throws ApprenticeEx {
		setRanges(shortRange, mediumRange, longRange);
	}

	/**
	 * @throws ParsingEx
	 *             if the text is not understood
	 * @throws ApprenticeEx
	 *             if we have anything other than shortRange < mediumRange < longRange
	 */
	public Range(final String rangeAsText) throws ParsingEx, ApprenticeEx {
		final StringTokenizer tokenizer = new StringTokenizer(rangeAsText.trim(), RANGE_CATEGORIES_DELIMINATOR);
		if (tokenizer.countTokens() != 3) {
			throw new ParsingEx("All three range types must be given.");
		}
		try {
			final int shortRange = Integer.valueOf(tokenizer.nextToken());
			final int mediumRange = Integer.valueOf(tokenizer.nextToken());
			final int longRange = Integer.valueOf(tokenizer.nextToken());
			setRanges(shortRange, mediumRange, longRange);
		} catch (final IllegalArgumentException e) {
			throw new ParsingEx("One or more range values were not numbers.");
		}
	}

	/**
	 * returns the long (3rd) range category
	 */
	public int getLongRange() {
		return longRange;
	}

	/**
	 * returns the medium (2nd) range category
	 */
	public int getMediumRange() {
		return mediumRange;
	}

	/**
	 * returns the short (1std) range category
	 */
	public int getShortRange() {
		return shortRange;
	}

	/**
	 * sets the long (3rd) range category
	 */
	public void setLongRange(final int feet) {
		setRanges(shortRange, mediumRange, feet);
	}

	/**
	 * sets the medium (2nd) range category
	 */
	public void setMediumRange(final int feet) {
		setRanges(shortRange, feet, longRange);
	}

	/**
	 * sets all the range categories
	 * 
	 * @throws ApprenticeEx
	 *             if we have anything other than shortRange < mediumRange < longRange
	 */
	public void setRanges(final int shortRange, final int mediumRange, final int longRange) throws ApprenticeEx {
		if (shortRange <= 0 || mediumRange <= 0 || longRange <= 0) {
			throw new ApprenticeEx("Range values must be > 0");
		}
		if (shortRange >= mediumRange || shortRange >= longRange || mediumRange >= longRange) {
			throw new ApprenticeEx("Range values do not make sense");
		}
		this.shortRange = shortRange;
		this.mediumRange = mediumRange;
		this.longRange = longRange;
	}

	/**
	 * sets the short (1st) range category
	 */
	public void setShortRange(final int feet) {
		setRanges(feet, mediumRange, longRange);
	}

	@Override
	public String toString() {
		return shortRange + RANGE_CATEGORIES_DELIMINATOR + mediumRange + RANGE_CATEGORIES_DELIMINATOR + longRange;
	}

}
