package com.apprentice.rpg.rules;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.durable.IDurableItem;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.random.dice.DiceModificator;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollException;
import com.google.inject.Inject;

/**
 * rules that are mostly copied from D20/3.5, see http://www.d20srd.org/
 * 
 * @author theoklitos
 * 
 */
public class D20BasedRuleset implements Ruleset {

	private static Logger LOG = Logger.getLogger(D20BasedRuleset.class);

	private final DiceModificator diceModificator;

	@Inject
	public D20BasedRuleset() {
		diceModificator = new DiceModificator();
	}

	@Override
	public void decreaseRoll(final Roll roll, final int positions) {
		try {
			for (int i = 0; i < positions; i++) {
				diceModificator.decreaseFirstDiceOfRoll(roll);
			}
		} catch (final ArrayIndexOutOfBoundsException e) {
			// bug in the dicemodificator
			diceModificator.decreaseFirstDiceOfRollToZero(roll);
		}
	}

	@Override
	public void decreaseRollToZero(final Roll roll) {
		diceModificator.decreaseFirstDiceOfRollToZero(roll);
	}

	@Override
	public int getBaseBlockForSize(final Size size) {
		switch (size) {
		case DIMUNITIVE:
			return 16;
		case TINY:
			return 14;
		case SMALL:
			return 12;
		case MEDIUM:
			return 10;
		case LARGE:
			return 8;
		case HUGE:
			return 6;
		case GARGANTUAN:
			return 4;
		default:
			throw new ApprenticeEx("Size \"" + size + "\" not supported!");
		}
	}

	@Override
	public int getDeteriorationIncrementForType(final IDurableItem durableItemInstance) {
		if (durableItemInstance.getClass().isAssignableFrom(Weapon.class)) {
			return 3;
		} else if (durableItemInstance.getClass().isAssignableFrom(ArmorPiece.class)) {
			return 3;
		} else {
			LOG.debug("Deterioration increment asked for type that is not handled: "
				+ durableItemInstance.getClass().getSimpleName());
			return 1;
		}
	}

	@Override
	public void increaseRoll(final Roll roll, final int positions) {
		try {
			for (int i = 0; i < positions; i++) {
				diceModificator.increaseFirstDiceOfRoll(roll);
			}
		} catch (final ArrayIndexOutOfBoundsException e) {
			throw new RollException("Dice cannot be increased so much (steps: " + positions + ")");
		}
	}

	@Override
	public String toString() {
		return "D20 and D&D 3.9 based default ruleset";
	}

}
