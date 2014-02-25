package com.apprentice.rpg.model.combat;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.rules.RuleBased;
import com.apprentice.rpg.rules.Ruleset;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;

/**
 * Simulates the bonuses in SRD (and in 3.9), in the form of +X/+(X-5)/+(X-10). Has the option of
 * disabling the operators to represent Blocks.
 * 
 */
public final class BonusSequence implements RuleBased {

	private static final String BONUS_SEPARATOR = "/";

	private final List<Integer> bonuses;
	private transient Ruleset ruleset;

	/**
	 * copy constructor
	 */
	public BonusSequence(final BonusSequence other) {
		this(other.bonuses.get(0).intValue());
		this.setRuleset(other.ruleset);
	}

	/**
	 * @param baseAttackBonus
	 *            highest bonus, rest will be auto-calculated
	 */
	public BonusSequence(final int baseAttackBonus) {
		bonuses = Lists.newArrayList();
		generateBonuses(baseAttackBonus);
	}

	/**
	 * Enter a sequence as a string, plus operators (+) are optional
	 * 
	 * @throws ParsingEx
	 *             if the string does not make senes
	 */
	public BonusSequence(final String sequence) throws ParsingEx {
		Checker.checkNonNull("BonusSequence needs some text to parse", true, sequence);
		bonuses = Lists.newArrayList();
		Integer firstBonus = null;
		try {
			final StringTokenizer tokenizer = new StringTokenizer(sequence, BONUS_SEPARATOR);
			while (tokenizer.hasMoreElements()) {
				final String bonus = StringUtils.remove(tokenizer.nextToken(), "+");
				final int number = Integer.valueOf(bonus);
				if (firstBonus == null) {
					firstBonus = number;
				}
				bonuses.add(number);
			}
		} catch (final IllegalArgumentException e) {
			throw new ParsingEx("Sequence \"" + sequence + "\" was not understood.");
		}
		final BonusSequence expected = new BonusSequence(firstBonus);
		if (!expected.equals(this)) {
			throw new ParsingEx("Sequence \"" + sequence + "\" has a wrong step somewhere.");
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof BonusSequence) {
			final BonusSequence otherSequence = (BonusSequence) other;
			return ApprenticeCollectionUtils.areAllElementsEqual(bonuses, otherSequence.bonuses);
		} else {
			return false;
		}
	}

	/**
	 * adds 1 bonus every 5 numbers
	 */
	private void generateBonuses(final int baseAttackBonus) {
		bonuses.clear();
		// number of rolls
		int noExtras = (baseAttackBonus / 5) - 1;
		if (baseAttackBonus % 5 > 0) {
			noExtras++;
		}
		// populate
		bonuses.add(baseAttackBonus);
		for (int i = 0; i < noExtras; i++) {
			final int toSubtract = baseAttackBonus - (5 * (i + 1));
			bonuses.add(toSubtract);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(bonuses);
	}

	/**
	 * will add or subtract the given number to this sequence. All attacks will be affected
	 */
	public void modify(final int modifier) {
		generateBonuses(bonuses.get(0) + modifier);
	}

	/**
	 * if there exists a backslash at the end, cut it
	 */
	private String pruneLastSeparator(final StringBuffer result) {
		String resultString = result.toString().trim();
		if (resultString.endsWith(BONUS_SEPARATOR)) {
			resultString = resultString.substring(0, resultString.length() - 1);
		}
		return resultString;
	}

	@Override
	public void setRuleset(final Ruleset ruleset) {
		this.ruleset = ruleset;
	}

	@Override
	public String toString() {
		return Joiner.on(",").join(bonuses);
	}

	/**
	 * returns the sequence in an attack form i.e. +7/+2
	 */
	public String toStringAttack() {
		final StringBuffer result = new StringBuffer();
		for (final Integer bonus : bonuses) {
			final String integerAsString = Integer.toString(bonus) + BONUS_SEPARATOR;
			if (bonus >= 0) {
				result.append("+");
			}
			result.append(integerAsString);
		}
		return pruneLastSeparator(result);
	}

	/**
	 * returns the sequence in an block form, depending on the size i.e.
	 */
	public String toStringBlock(final Size size) {
		int additionForSize = 10;
		if (ruleset != null) {
			additionForSize = ruleset.getBaseBlockForSize(size);
		}
		final StringBuffer result = new StringBuffer();
		for (final Integer bonus : bonuses) {
			final String integerAsString = Integer.toString(bonus + additionForSize) + BONUS_SEPARATOR;
			result.append(integerAsString);
		}
		return pruneLastSeparator(result);
	}
}
