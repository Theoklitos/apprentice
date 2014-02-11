package com.apprentice.rpg.rules;

import com.apprentice.rpg.model.armor.ArmorPieceInstance;
import com.apprentice.rpg.model.durable.IDurableItemInstance;
import com.apprentice.rpg.model.weapon.WeaponInstance;
import com.apprentice.rpg.random.dice.DiceModificator;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollException;

/**
 * rules that are mostly copied from D20/3.5, see http://www.d20srd.org/
 * 
 * @author theoklitos
 * 
 */
public class D20BasedRuleset implements Ruleset {

	private final DiceModificator diceModificator;

	public D20BasedRuleset() {
		diceModificator = new DiceModificator();
	}

	@Override
	public Roll getDecreasedRoll(final Roll roll, final int positions) {
		try {
			final Roll copiedRoll = new Roll(roll);
			for (int i = 0; i < positions; i++) {
				diceModificator.decreaseFirstDiceOfRoll(copiedRoll);
			}
			return copiedRoll;
		} catch (final ArrayIndexOutOfBoundsException e) {
			// bug in the dicemodificator
			return new Roll(Roll.getCreateZeroRoll().toString() + roll.getModifierAsString());
		}
	}

	@Override
	public int getDeteriorationIncrementForType(final Class<? extends IDurableItemInstance> item) {
		if (item.isAssignableFrom(WeaponInstance.class)) {
			return 3;
		} else if (item.isAssignableFrom(ArmorPieceInstance.class)) {
			return 3;
		} else {
			return 1;
		}
	}

	@Override
	public Roll getIncreasedRoll(final Roll roll, final int positions) {
		try {
			final Roll copiedRoll = new Roll(roll);
			for (int i = 0; i < positions; i++) {
				diceModificator.increaseFirstDiceOfRoll(copiedRoll);
			}
			return copiedRoll;
		} catch (final ArrayIndexOutOfBoundsException e) {
			throw new RollException("Dice cannot be increased so much (steps: " + positions + ")");
		}
	}

	@Override
	public String toString() {
		return "D20 and D&D 3.9 based default ruleset";
	}
}
