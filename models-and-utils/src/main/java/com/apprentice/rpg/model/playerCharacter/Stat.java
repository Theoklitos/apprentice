package com.apprentice.rpg.model.playerCharacter;

import com.apprentice.rpg.model.playerCharacter.StatBundle.StatType;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.util.ApprenticeStringUtils;
import com.google.common.base.Objects;

/**
 * Each character has 6 "statistics" that represents his qualities, such as Strength, Intelligence, etc. A
 * stat is a positive integer that has a +/- bonus associated with it.
 * 
 */
public final class Stat {

	private final String statType;
	private final int originalValue;
	private int currentValue;
	private int bonus;

	/**
	 * Requires the name of the stat (ie Strength) and its value (ie 18)
	 */
	public Stat(final StatType statType, final int value) {
		this.statType = statType.toString();
		this.originalValue = value;
		this.currentValue = this.originalValue;
		this.bonus = getDetermineBonus(value);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Stat) {
			final Stat stat = (Stat) other;
			return Objects.equal(statType, stat.statType) && Objects.equal(getOriginalValue(), stat.getOriginalValue())
				&& Objects.equal(getValue(), stat.getValue()) && Objects.equal(getBonus(), stat.getBonus());
		} else {
			return false;
		}
	}

	public int getBonus() {
		return bonus;
	}

	private int getDetermineBonus(final int value) {
		boolean isPositive = true;
		if (value < 9) {
			isPositive = false;
		}

		final int distanceFrom10 = Math.abs(value - 10);
		int absoluteBonus = distanceFrom10 / 2;
		if (!isPositive) {
			if (distanceFrom10 % 2 > 0) {
				absoluteBonus++;
			}
		}

		if (isPositive) {
			return absoluteBonus;
		} else {
			return -absoluteBonus;
		}

	}

	/**
	 * The original (unmodified) value for this stat. Depending on buffs and/or damage, this might differ from
	 * the getCurrentValue() one
	 */
	public int getOriginalValue() {
		return originalValue;
	}

	/**
	 * What is the {@link StatType} of this stat?
	 */
	public StatType getStatType() {
		return StatType.valueOf(statType);
	}

	/**
	 * The current numerical value for this stat
	 */
	public int getValue() {
		return currentValue;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(statType, getOriginalValue(), getValue(), getBonus());
	}	
	
	public void setValue(final int value) {
		this.currentValue = value;
		this.bonus = getDetermineBonus(currentValue);
	}
	
	@Override
	public String toString() {
		String operator = Roll.getOperator(bonus, true);
		if (operator.equals("-")) {
			operator = "";
		}
		return currentValue + " (" + operator + bonus + ")";
	}

	/**
	 * Returns a more complete form of toString() with more info
	 */
	public String toStringDetailed() {
		return statType + " (" + ApprenticeStringUtils.getNumberWithOperator(getBonus()) + ")";
	}
}
