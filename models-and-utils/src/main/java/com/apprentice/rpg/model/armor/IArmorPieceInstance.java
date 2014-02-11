package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.durable.IDurableItemInstance;
import com.apprentice.rpg.random.ApprenticeRandom;

/**
 * Instance of {@link ArmorPiece} that can be handed out to a player, and is durable(can be damaged/repaired) etc
 * 
 * @author theoklitos
 * 
 */
public interface IArmorPieceInstance extends IDurableItemInstance {

	/**
	 * returns the (random) number of damage points to be absorbed, due to DR 
	 */
	public int rollDamageReduction(ApprenticeRandom random);
}
