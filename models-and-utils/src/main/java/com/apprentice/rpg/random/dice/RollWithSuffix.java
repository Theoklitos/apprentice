package com.apprentice.rpg.random.dice;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.parsing.ParsingEx;
import com.google.common.base.Objects;

/**
 * A {@link Roll} that contains a single extra suffix number that is not a roll modifier, for example 2D6+1
 * (10)
 * 
 * @author theoklitos
 * 
 */
public class RollWithSuffix {

	private static final String SUFFIX_START_CHARACTER = "(";
	private static final String SUFFIX_END_CHARACTER = ")";

	private final int suffix;
	private final Roll roll;

	/**
	 * suffix will be zero
	 */
	public RollWithSuffix(final Roll roll) {
		this.roll = roll;
		this.suffix = 0;
	}

	public RollWithSuffix(final Roll roll, final int suffix) {
		this.roll = roll;
		this.suffix = suffix;
	}

	/**
	 * Will try to understand the roll w suffix
	 */
	public RollWithSuffix(final String rollWithSuffixAsString) throws ParsingEx {
		if (StringUtils.contains(rollWithSuffixAsString, SUFFIX_START_CHARACTER)) {
			final String rollString = StringUtils.substringBefore(rollWithSuffixAsString, SUFFIX_START_CHARACTER);
			this.roll = new Roll(rollString);
			final String suffixValue =
				StringUtils.substringBetween(rollWithSuffixAsString, SUFFIX_START_CHARACTER, SUFFIX_END_CHARACTER);
			try {
				this.suffix = Integer.valueOf(suffixValue);
			} catch (final NumberFormatException e) {
				throw new ParsingEx("No suffix provided or suffix was not understood.");
			}
		} else {
			// try only for roll
			this.roll = new Roll(rollWithSuffixAsString);
			this.suffix = 0;
		}
	}

	public RollWithSuffix(final String roll, final int suffix) throws ParsingEx {
		this(new Roll(roll), suffix);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof RollWithSuffix) {
			final RollWithSuffix otherRollWithSuffix = (RollWithSuffix) other;
			return Objects.equal(roll, otherRollWithSuffix.roll) && suffix == otherRollWithSuffix.suffix;
		} else {
			return false;
		}
	}

	/**
	 * Returns the roll without the suffix;
	 */
	public Roll getRoll() {
		return roll;
	}

	/**
	 * Returns the suffix
	 */
	public int getSuffix() {
		return suffix;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(roll, suffix);
	}

	@Override
	public String toString() {
		if (suffix != 0) {
			return roll.toString() + SUFFIX_START_CHARACTER + suffix + SUFFIX_END_CHARACTER;
		} else {
			return roll.toString();
		}
	}

}
