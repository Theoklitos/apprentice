package com.apprentice.rpg.strike.handlers;

import org.apache.log4j.Logger;

import com.apprentice.rpg.strike.Effect;
import com.apprentice.rpg.strike.EffectType;
import com.apprentice.rpg.strike.StrikeType;

/**
 * Handles a particular {@link StrikeType}
 * 
 * @author theoklitos
 * 
 */
public abstract class AbstractEffectTypeHandler implements SingleEffectTypeHandler {

	private final String type;

	public AbstractEffectTypeHandler(final EffectType type) {
		this.type = type.toString();
	}
	
	@Override
	public EffectType getHandledType() {
		return EffectType.valueOf(type);
	}

	/**
	 * returns the first parameter parsed to a positive integer.
	 * 
	 * @throws EffectApplicationEx
	 */
	public int getLevel(final Effect effect) {
		try {
			final Integer level = Integer.valueOf(effect.getVariables().get(0));
			if (level < 0) {
				throw new EffectApplicationEx("Level cannot be < 0, but was " + level);
			}
			return level;
		} catch (final IllegalArgumentException e) {
			throw new EffectApplicationEx("Non-numeric level: " + effect.getVariables().get(0));
		}
	}
	
	/**
	 * returns the appropriate logger for this subclass
	 */
	public Logger getLogger() {
		return Logger.getLogger(getClass());
	}
}
