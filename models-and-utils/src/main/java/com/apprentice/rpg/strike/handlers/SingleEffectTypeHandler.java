package com.apprentice.rpg.strike.handlers;

import com.apprentice.rpg.strike.EffectType;
import com.apprentice.rpg.strike.StrikeType;

/**
 * Handles a particular {@link StrikeType}
 * 
 * @author theoklitos
 * 
 */
public interface SingleEffectTypeHandler extends IEffectTypeHandler {

	/**
	 * returns what {@link EffectType} this handler is supposed to handle
	 */
	EffectType getHandledType();

}
