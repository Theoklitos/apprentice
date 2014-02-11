package com.apprentice.rpg.rules;

import com.apprentice.rpg.model.durable.IDurableItemInstance;
import com.apprentice.rpg.random.dice.Roll;

/**
 * Holds various rpg rules, invariants, tables etc
 * 
 * @author theoklitos
 * 
 */
public interface Ruleset {

	/**
	 * Returns a {@link Roll} decreased by the number of "positions", for example for one position we have
	 * D10->D8. For two, it is D10->D8->D6. If the given roll has many dices, increases only the first.
	 */
	Roll getDecreasedRoll(Roll roll, int positions);

	/**
	 * returns a the number that every 1/number of item hit point loss, that item deteriorates
	 */
	int getDeteriorationIncrementForType(Class<? extends IDurableItemInstance> item);

	/**
	 * Returns a {@link Roll} incerased by the number of "positions", for example for one position we have
	 * D6->D8. For two, it is D6->D8->D10. If the given roll has many dices, increases only the first.
	 */
	Roll getIncreasedRoll(Roll roll, int positions);

}
