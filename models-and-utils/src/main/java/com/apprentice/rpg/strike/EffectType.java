package com.apprentice.rpg.strike;

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