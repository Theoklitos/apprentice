package com.apprentice.rpg.random.dice;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.util.Checker;
import com.apprentice.rpg.util.OccurrenceList;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * A d&d roll with modifiers and bonuses. Used for attack rolls, weapon damages, etc.
 * 
 */
public final class Roll {

	private final static String DICE_PREFIX = "D";
	private static final String MINUS_SYMBOL = "-";
	private static final String PLUS_SYMBOL = "+";

	public static Roll getCreateZeroRoll() {
		try {
			return new Roll("D0");
		} catch (final ParsingEx e) {
			throw new ApprenticeEx("This should not have happened. Look inside the getCreateZeroRoll() method");
		}
	}

	/**
	 * Returns operator "+" if positive/true/zero, or defined ""/"-" if false/negative
	 */
	public static String getOperator(final boolean isPositive, final boolean shouldReturnMinusSign) {
		if (isPositive) {
			return PLUS_SYMBOL;
		} else {
			if (shouldReturnMinusSign) {
				return MINUS_SYMBOL;
			} else {
				return "";
			}
		}
	}

	/**
	 * Returns operator "+" if positive/true/zero, or defined ""/"-" if false/negative
	 */
	public static String getOperator(final int value, final boolean shouldUseMinusSign) {
		if (value >= 0) {
			return PLUS_SYMBOL;
		} else {
			if (shouldUseMinusSign) {
				return MINUS_SYMBOL;
			} else {
				return "";
			}
		}
	}

	/**
	 * Self-explanatory
	 */
	public static Roll getRollFromDiceAndOccurrences(final int dice, final int occurrences) {
		String operator = "";
		if (dice < 0) {
			operator = MINUS_SYMBOL;
		}
		String occurrenceString = "";
		if (occurrences > 1) {
			occurrenceString = String.valueOf(occurrences);
		}
		final String resultString = operator + occurrenceString + DICE_PREFIX + Math.abs(dice);
		Roll result;
		try {
			result = new Roll(resultString);
		} catch (final RollException e) {
			throw new ApprenticeEx(
					"This should not have happened. Look inside the getRollFromDiceAndOccurrences() method");
		}
		return result;
	}

	private int modifier;
	private final OccurrenceList<Integer> dice;

	/**
	 * Copy constructor
	 */
	public Roll(final Roll copyFrom) {
		this.modifier = copyFrom.modifier;
		this.dice = new OccurrenceList<Integer>(copyFrom.dice);
	}

	/**
	 * Main constructor. Tries to understand the text.
	 * 
	 * @throws ParsingEx
	 *             if the roll was not understood
	 */
	public Roll(final String text) throws ParsingEx {
		Checker.checkNonNull("Error while parsing dice roll", true, text);
		final String actualText = text.trim().toUpperCase();
		this.dice = new OccurrenceList<Integer>();
		modifier = 0;

		if (actualText.indexOf(DICE_PREFIX) == -1) {
			throw new RollException("No dices in roll text: " + text);
		}

		final StringTokenizer plusTokenizer = new StringTokenizer(actualText, PLUS_SYMBOL);
		while (plusTokenizer.hasMoreElements()) {
			final String splitAtPlus = (String) plusTokenizer.nextElement();
			final boolean wasFirstElementNegative = splitAtPlus.startsWith(MINUS_SYMBOL);
			final StringTokenizer minusTokenizer = new StringTokenizer(splitAtPlus, MINUS_SYMBOL);
			int minusCount = 0;
			while (minusTokenizer.hasMoreElements()) {
				final String splitAtMinus = (String) minusTokenizer.nextElement();
				if (minusCount == 0 && !wasFirstElementNegative) {
					understandElement(splitAtMinus, true);
				} else {
					understandElement(splitAtMinus, false);
				}
				minusCount++;
			}
		}
	}

	/**
	 * Adds the given dice to the rest, at the end of their list. Use this if you don't care about
	 * position. Will merge occurrences if dice already exists.
	 */
	protected void addDice(final int diceToAdd) {
		addDice(diceToAdd, dice.size());
	}

	/**
	 * Adds the given dice in the given position among the dices and shifts the others to the right.
	 * Position count is 1-based. Beware of going out of index. Use with caution. Will merge
	 * occurrences if dice already exists.
	 * 
	 * @throws ApprenticeEx
	 */
	protected void addDice(final int diceToAdd, final int position) {
		if (position == 0) {
			throw new ApprenticeEx("Tried to add dice to position zero");
		}
		dice.add(position - 1, diceToAdd);
	}

	/**
	 * Adds the dice of the given roll to this roll. Modifiers are totaled.
	 */
	protected void addRoll(final Roll toAdd) {
		// add the modifier
		this.modifier += toAdd.getModifier();
		// add the dices
		for (final int diceToAdd : toAdd.getDice().getAllElements()) {
			dice.add(diceToAdd);
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Roll) {
			final Roll otherRoll = (Roll) other;
			return Objects.equal(dice, otherRoll.dice) && modifier == otherRoll.modifier;
		} else {
			return false;
		}
	}

	/**
	 * Returns all the stored dices as an {@link OccurrenceList}
	 */
	public OccurrenceList<Integer> getDice() {
		return dice;
	}

	/**
	 * Using the toString() method, returns all the individual dices as a list of strings, while
	 * merging multiple occurrences of dices, for example, D6 and D6 are returned as one element,
	 * 2D6.
	 */
	protected List<String> getMergedDicesAsStringList(final boolean showPlusOperators) {
		final List<String> result = Lists.newArrayList();
		for (int i = 0; i < dice.size(); i++) {
			int roll = dice.get(i);
			final int occurences = dice.getOccurencesOf(roll);
			final boolean isNegative = roll < 0;
			roll = Math.abs(roll);
			// first, append D
			final String withD = DICE_PREFIX + roll;
			String elementAsString = withD;
			// then number
			if (occurences > 1) {
				elementAsString = occurences + elementAsString;
			}
			if (isNegative) {
				elementAsString = MINUS_SYMBOL + elementAsString;
			} else if (showPlusOperators) {
				elementAsString = PLUS_SYMBOL + elementAsString;
			}
			result.add(elementAsString);
		}
		return result;
	}

	/**
	 * returns the total sum of all modifiers as one integer, ie +2+8 will return +10
	 */
	public int getModifier() {
		return modifier;
	}

	/**
	 * returns the modifier but with a plus/minus sumbol
	 */
	public String getModifierAsString() {
		final String result;
		if (modifier > 0) {
			result = PLUS_SYMBOL + modifier;
		} else if (modifier == 0) {
			result = "";
		} else {
			result = String.valueOf(modifier);
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(modifier, dice);
	}

	/**
	 * Returns whether this is a roll that can only roll zero (example D0+1-1)
	 */
	protected boolean isZeroRoll() {
		if (modifier != 0) {
			return false;
		}
		boolean rollsZero = true;
		for (int i = 0; i < dice.size(); i++) {
			if (dice.get(i) != 0) {
				rollsZero = false;
			}
		}
		return rollsZero;
	}

	public boolean match(final Object o) {
		if (o instanceof Roll) {
			final Roll other = (Roll) o;
			if (this.modifier == other.modifier && this.dice.match(other.getDice())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Removes the given dice (all its occurrences), and returns its index. Returns -1 if it did not
	 * exist.
	 */
	protected int removeDice(final int diceToRemove) {
		return dice.remove(diceToRemove);
	}

	/**
	 * Removes one occurrence of the given element and returns its index (-1 if non existance). If
	 * the element has only one occurrence, it is removed completely,
	 */
	protected int removeOneOccurenceOfDice(final int diceToDecrease) {
		return dice.removeOnce(diceToDecrease);
	}

	/**
	 * Rolls itself, applies modifiers and returns result
	 */
	public int roll(final ApprenticeRandom random) {
		int result = 0;
		for (final int diceRange : dice.getAllElements()) {
			result += (random.getRandomInteger(diceRange)) + 1;
		}
		return result + modifier;
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		for (final String elementAsString : getMergedDicesAsStringList(true)) {
			result.append(elementAsString);
		}
		result.append(getModifierAsString());
		String stringResult = result.toString();
		stringResult = StringUtils.removeStart(stringResult, PLUS_SYMBOL);
		return stringResult;
	}

	private void understandElement(final String text, final boolean isPositive) throws ParsingEx {
		final String operator = getOperator(isPositive, false);
		final ParsingEx rollException =
			new ParsingEx("Roll piece '" + operator + text + "' does not make sense");

		// if it has letters other than D, stop
		if (!StringUtils.isNumeric(text)) {
			final String textWithoutD = StringUtils.remove(text, DICE_PREFIX);
			if (!StringUtils.isNumeric(textWithoutD)) {
				throw rollException;
			}
		}
		if (StringUtils.isNumeric(text)) { // if it is just a modifier
			if (isPositive) {
				modifier += Integer.parseInt(text);
			} else {
				modifier -= Integer.parseInt(text);
			}
		} else if (StringUtils.isAlphanumeric(text)) { // if it is dice
			// check if many die
			final String[] withoutD = StringUtils.split(text, DICE_PREFIX);
			if (withoutD.length == 1) { // one die
				if (isPositive) {
					dice.add(Integer.valueOf(withoutD[0]));
				} else {
					dice.add(-Integer.valueOf(withoutD[0]));
				}
			} else { // many dices
				final int times = Integer.valueOf(withoutD[0]);
				for (int i = 0; i < times; i++) {
					if (isPositive) {
						dice.add(Integer.valueOf(withoutD[1]));
					} else {
						dice.add(-Integer.valueOf(withoutD[1]));
					}
				}
			}
		} else {
			throw rollException;
		}
	}
}
