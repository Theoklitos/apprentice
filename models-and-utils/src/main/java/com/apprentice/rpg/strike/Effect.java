package com.apprentice.rpg.strike;

import org.apache.log4j.Logger;

/**
 * Effect of a strike; has some bad effect in the player. A "strike" may contain multiple effects, usually
 * more than 1.
 * 
 * @author theoklitos
 * 
 */
public class Effect {

	/**
	 * what type of effect is this? Effects are grouped into big categories.
	 * 
	 * @author theoklitos
	 * 
	 */
	public enum EffectType {
		/**
		 * continuous HP loss
		 */
		BLEEDING,

		/**
		 * A negative modifier to combat capabilities
		 */
		PENALTY,

		/**
		 * Stun, daze or some sort of play action loss
		 */
		ACTION_LOSS,

		/**
		 * Specific body part debiliation: breakage, fracture or severance
		 */
		DEBILITATION
	}

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(Effect.class);

	private final int level;
	private final EffectType type;

	public Effect(final EffectType effectType, final int initialLevel) {
		if (initialLevel < 1) {
			throw new StrikeEx("Effects need an initial level of > 0");
		}
		this.level = initialLevel;
		this.type = effectType;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Effect) {
			final Effect otherEffect = (Effect) other;
			return level == otherEffect.level && type.equals(otherEffect.type);
		} else {
			return false;
		}
	}

	/**
	 * Returns the level of this effect; bigger levels -> worse damage for the player
	 */
	public int getLevel() {
		return level;
	}
	
	@Override
	public String toString() {
		return type + " lvl" + level;
	}

}
