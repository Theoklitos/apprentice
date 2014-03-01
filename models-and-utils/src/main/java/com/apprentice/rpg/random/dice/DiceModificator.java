package com.apprentice.rpg.random.dice;

import java.util.List;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.util.OccurrenceList;
import com.google.common.collect.Lists;

/**
 * Module responsible for increasing and decreasing dice, such as D6<->D8 and 2D12<->3D8
 * 
 */
public final class DiceModificator {

	private static boolean doesRollListContainRoll(final List<Roll> source, final Roll roll) {
		for (final Roll sourceRoll : source) {
			if (sourceRoll.match(roll)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns -1 if not existent
	 */
	private static int getIndexOfRollInList(final List<Roll> source, final Roll roll) {
		int count = 0;
		for (final Roll sourceRoll : source) {
			if (sourceRoll.match(roll)) {
				return count;
			}
			count++;
		}
		return -1;
	}

	private static void replaceDiceNumberWith(final int elementNumberToReplace, final Roll from,
			final int replacementDice, final int replacementOccurrences) {
		final OccurrenceList<Integer> fromDices = from.getDice();
		final int dice = fromDices.get(elementNumberToReplace);
		final int index = fromDices.remove(dice);
		for (int i = 0; i < replacementOccurrences; i++) {
			fromDices.add(index, replacementDice);
		}
	}

	private final List<Roll> categoryMappings;
	private final List<Integer> diceMappings;
	private final int breakPoint;
	private final int breakPointOccurrencesForDecrease;

	/**
	 * @throws RuleException
	 */
	public DiceModificator() {
		try {
			breakPoint = 12;
			breakPointOccurrencesForDecrease = 5;
			categoryMappings = Lists.newArrayList();
			categoryMappings.add(new Roll("2D6"));
			categoryMappings.add(new Roll("3D8"));
			categoryMappings.add(new Roll("4D10"));
			diceMappings = Lists.newArrayList();
			diceMappings.add(0);
			diceMappings.add(1);
			diceMappings.add(2);
			diceMappings.add(3);
			diceMappings.add(4);
			diceMappings.add(6);
			diceMappings.add(8);
			diceMappings.add(10);
			diceMappings.add(12);
		} catch (final RollException e) {
			throw new ApprenticeEx("Error while initializting the dice modifier: " + e.getMessage());
		}
	}

	/**
	 * Decreases the chosen (by number) "element" of the given roll.
	 */
	protected void decreaseElement(final int elementNumber, final Roll from) {
		if (from.getDice().get(elementNumber) < 0) {
			increaseElement(elementNumber, from, true);
		} else {
			decreaseElement(elementNumber, from, false);
		}
	}

	/**
	 * Call that can set the parameter whether the input element is negative or not.
	 */
	protected void decreaseElement(final int elementNumber, final Roll from, final boolean isNegative) {
		final OccurrenceList<Integer> fromDices = from.getDice();
		final int diceToDecrease = fromDices.get(elementNumber);
		final int occurrences = fromDices.getOccurencesOf(diceToDecrease);
		final int diceToDecreaseAbs = Math.abs(diceToDecrease);
		final Roll fromRoll = Roll.getRollFromDiceAndOccurrences(diceToDecreaseAbs, occurrences);
		int resultDice = -1;
		int resultOccurrences = 1;
		// two cases:
		if (doesRollListContainRoll(categoryMappings, fromRoll)) { // if category decreases
			resultOccurrences = getIndexOfRollInList(categoryMappings, fromRoll) + 1;
			resultDice = breakPoint;
		} else if ((diceToDecreaseAbs == breakPoint || diceToDecreaseAbs == (breakPoint - 1))
			&& occurrences >= breakPointOccurrencesForDecrease) {
			resultOccurrences = occurrences - 1;
			resultDice = breakPoint;
		} else { // if category does not decrease
			resultOccurrences = occurrences;
			for (int i = 0; i < diceMappings.size(); i++) {
				final int mappedDice = diceMappings.get(i);
				if (diceToDecreaseAbs <= mappedDice) {
					resultDice = diceMappings.get(i - 1);
					break;
				}
			}
			// also, if the number is above the breakpoint
			if (diceToDecreaseAbs > breakPoint) {
				resultDice = breakPoint;
			}
		}
		// check
		if (resultDice == -1) {
			throw new ApprenticeEx("Something went wrong while increasing roll '" + from + "'");
		}
		// replace
		if (isNegative) {
			resultDice = -resultDice;
		}
		replaceDiceNumberWith(elementNumber, from, resultDice, resultOccurrences);
	}

	/**
	 * Decreases the given role by "one" position, i.e 2D6->D12. If the given roll has many dices,
	 * increases only the first.
	 */
	public void decreaseFirstDiceOfRoll(final Roll roll) {
		decreaseElement(0, roll);
	}

	/**
	 * Decreases the given roll all the way to D0.
	 */
	public void decreaseFirstDiceOfRollToZero(final Roll roll) {		
		while (!roll.isZeroRoll()) {
			decreaseElement(0, roll);
		}
	}

	/**
	 * Increases the chosen (by number) "element" of the given roll.
	 */
	protected void increaseElement(final int elementNumber, final Roll from) {
		if (from.getDice().get(elementNumber) < 0) {
			decreaseElement(elementNumber, from, true);
		} else {
			increaseElement(elementNumber, from, false);
		}
	}

	/**
	 * Call that can set the parameter whether the input element is negative or not.
	 */
	protected void increaseElement(final int elementNumber, final Roll from, final boolean isNegative) {
		final OccurrenceList<Integer> fromDices = from.getDice();
		final int diceToIncrease = fromDices.get(elementNumber);
		if (diceToIncrease < 0 && !isNegative) {
			decreaseElement(elementNumber, from, true);
		}
		final int occurrences = fromDices.getOccurencesOf(diceToIncrease);
		final int diceToIncreaseAbs = Math.abs(diceToIncrease);
		int resultDice = -1;
		int resultOccurrences = 1;
		// two cases:
		if (diceToIncreaseAbs >= breakPoint) { // if category increases
			Roll replacement = null;
			if (occurrences > 3) {
				resultDice = breakPoint;
				resultOccurrences = occurrences + 1;
			} else {
				replacement = categoryMappings.get(occurrences - 1);
				resultDice = replacement.getDice().get(0);
				resultOccurrences = replacement.getDice().getOccurencesOf(resultDice);
			}
		} else { // if category does not increase
			resultOccurrences = occurrences;
			for (int i = 0; i < diceMappings.size(); i++) {
				final int mappedDice = diceMappings.get(i);
				if (diceToIncreaseAbs < mappedDice) {
					resultDice = mappedDice;
					break;
				} else if (diceToIncreaseAbs == mappedDice) {
					resultDice = diceMappings.get(i + 1);
					break;
				}
			}
		}
		// check
		if (resultDice == -1) {
			throw new ApprenticeEx("Something went wrong while increasing roll '" + from + "'");
		}
		// replace
		if (isNegative) {
			resultDice = -resultDice;
		}
		replaceDiceNumberWith(elementNumber, from, resultDice, resultOccurrences);
	}

	/**
	 * Increases the given role by "one" position, i.e D6->D8. If the given roll has many dices,
	 * increases only the first.
	 */
	public void increaseFirstDiceOfRoll(final Roll roll) {
		increaseElement(0, roll);
	}

}
