package com.apprentice.rpg.random;

import com.apprentice.rpg.model.weapon.Damage;
import com.apprentice.rpg.model.weapon.DamageRoll;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.strike.StrikeType;

/**
 * Random number utilities
 * 
 * @author theoklitos
 * 
 */
public interface ApprenticeRandom {

	/**
	 * returns a random number from 0 (inclusive) to max (exclusive)
	 */
	int getRandomInteger(int max);

	/**
	 * rolls the given dice inside the {@link DamageRoll} and returns the result with the correct
	 * {@link StrikeType} inside a {@link Damage} object
	 */
	Damage roll(final DamageRoll damageRoll);

	/**
	 * rolls the given dice and adds the modifier(s), returns result
	 */
	int roll(final Roll roll);
}
