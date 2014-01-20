package com.apprentice.rpg.model;

import com.apprentice.rpg.dice.Roll;
import com.apprentice.rpg.model.StatBundle.StatType;

/**
 * Each character has 6 "statistics" that represents his qualities, such as Strength, Intelligence, etc
 * 
 */
public final class Stat {

	@SuppressWarnings("unused")
	private final StatType statType;
	private final int originalValue;
	private int currentValue;
	private int bonus;

	/**
	 * Requires the name of the stat (ie Strength) and its value (ie 18)
	 */
	public Stat(final StatType statType, final int value) {
		this.statType = statType;
		this.originalValue = value;
		this.currentValue = this.originalValue;
		this.bonus = getDetermineBonus(value);
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

	public int getValue() {
		return currentValue;
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
}
