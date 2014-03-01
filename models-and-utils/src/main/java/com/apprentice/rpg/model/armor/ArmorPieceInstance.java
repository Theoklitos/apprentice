package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.random.ApprenticeRandom;

/**
 * Armor piece that a player has handed out to him
 * 
 * @author theoklitos
 *
 */
public interface ArmorPieceInstance extends IArmorPiece {

	/**
	 * returns the (random) number of damage points to be absorbed, due to DR
	 */
	public int rollDamageReduction(ApprenticeRandom random);
	
}
