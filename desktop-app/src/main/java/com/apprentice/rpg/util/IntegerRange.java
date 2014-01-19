package com.apprentice.rpg.util;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

/**
 * Represents an inclusive range of integers
 * 
 */
public final class IntegerRange implements Comparable<IntegerRange> {

	public static final String NUMBER_RANGE_INDICATOR = "-";

	private final int min;
	private final int max;

	/**
	 * Reminder: The range is inclusive
	 * 
	 * @throws IllegalArgumentException
	 *             if max is > that min
	 */
	public IntegerRange(final int min, final int max) throws IllegalArgumentException {
		Checker.checkNonNull("Error while creating integer range", false, min, max);
		if (min > max) {
			throw new IllegalArgumentException("Error while creating integer range. Max value '" + max
				+ "' is smaller than min value '" + min + "'");
		}
		this.min = min;
		this.max = max;
	}

	/**
	 * Copy constructor
	 */
	public IntegerRange(final IntegerRange other) {
		this.min = other.min;
		this.max = other.max;
	}

	/**
	 * Returns this location in the correct form that the file reader can parse. Uses current
	 * values.
	 * 
	 * @throws IllegalArgumentException
	 *             if the text is non-understable<
	 */
	public IntegerRange(final String text) {
		final String actualText = StringUtils.trimToEmpty(text);
		final IllegalArgumentException exception =
			new IllegalArgumentException("Could not understand integer range " + text);
		if (!StringUtils.contains(actualText, NUMBER_RANGE_INDICATOR)
			|| StringUtils.countMatches(actualText, NUMBER_RANGE_INDICATOR) != 1) {
			throw exception;
		}
		final StringTokenizer tokenizer = new StringTokenizer(actualText, NUMBER_RANGE_INDICATOR);
		int min = 0;
		int max = 0;
		try {
			min = Integer.valueOf(StringUtils.trimToEmpty((String) tokenizer.nextElement()));
			max = Integer.valueOf(StringUtils.trimToEmpty((String) tokenizer.nextElement()));
		} catch (final NumberFormatException e) {
			throw exception;
		} catch (final NoSuchElementException e) {
			throw exception;
		}
		this.min = min;
		this.max = max;
	}

	@Override
	public int compareTo(final IntegerRange o) {
		if (this.min < o.min) {
			return -1;
		} else if (this.min == o.min) {
			return 0;
		} else {
			return 1;
		}
	}

	/**
	 * Returns whether this range contains the given int
	 */
	public boolean contains(final int number) {
		return (number >= getMin() && number <= getMax());
	}

	public int getMax() {
		return max;
	}

	public int getMin() {
		return min;
	}

	/**
	 * Returns the number of numbers this range contains
	 */
	public int size() {
		return Math.abs(getMin() - getMax()) + 1;
	}

	public String toParsingString() {
		return min + NUMBER_RANGE_INDICATOR + max;
	}

	@Override
	public String toString() {
		return "From " + getMin() + " to " + getMax() + ". " + size() + " numbers";
	}
}
