package com.apprentice.rpg.rules;

import com.apprentice.rpg.model.body.Size;
import com.apprentice.rpg.model.durable.IDurableItem;
import com.apprentice.rpg.random.dice.Roll;

/**
 * Holds various rpg rules, invariants, tables etc
 * 
 * @author theoklitos
 * 
 */
public interface Ruleset {

	/**
	 * Decreases this {@link Roll} by the number of "positions" given, for example for one position we have
	 * D10->D8. For two, it is D10->D8->D6. If the given roll has many dices, increases only the first.
	 */
	void decreaseRoll(Roll roll, int positions);

	/**
	 * Decreases the first dice of this {@link Roll} to D0. Modifiers are not affected.
	 */
	void decreaseRollToZero(Roll roll);

	/**
	 * reteurns the target difficuly (usually 1-20) based on size. For example, medium sized beings might be a
	 * 10
	 */
	int getBaseBlockForSize(Size size);

	/**
	 * returns a the number that every 1/number of item hit point loss, that item deteriorates
	 */
	int getDeteriorationIncrementForType(final IDurableItem durableItemInstance);

	/**
	 * Increases this {@link Roll} incerased by the number of "positions" given, for example for one position
	 * we have D6->D8. For two, it is D6->D8->D10. If the given roll has many dices, increases only the first.
	 */
	void increaseRoll(Roll roll, int positions);

}
