package com.apprentice.rpg.strike.handlers;

import com.apprentice.rpg.model.playerCharacter.IPlayerCharacter;
import com.apprentice.rpg.strike.Effect;
import com.apprentice.rpg.strike.NonRecognizedEffectTypeEx;
import com.apprentice.rpg.strike.StrikeType;

/**
 * Handles a particular {@link StrikeType}
 * 
 * @author theoklitos
 * 
 */
public interface IEffectTypeHandler {

	/**
	 * Applies any effect this strike might have after a # of rounds have passed.
	 */
	void applyContinuousEffectToPlayer(final Effect effect, final IPlayerCharacter pc);
	
	/**
	 * Applies the negative inital effects (=immediately when the strike occurs) of this {@link Effect} to the player
	 * 
	 * @throws NonRecognizedEffectTypeEx
	 *             if the type of the effect cannot be handled
	 */
	void applyInitialEffectToPlayer(final Effect effect, final IPlayerCharacter pc) throws NonRecognizedEffectTypeEx;

	/**
	 * Removes the negative effects of this {@link Effect} from the player
	 * 
	 * @throws NonRecognizedEffectTypeEx
	 *             if the type of the effect cannot be handled
	 */
	void removeEffectFromPlayer(final Effect effect, final IPlayerCharacter pc) throws NonRecognizedEffectTypeEx;
	
}
