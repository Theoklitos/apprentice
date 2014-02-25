package com.apprentice.rpg.strike;

import org.apache.log4j.Logger;

import com.apprentice.rpg.rules.Ruleset;

/**
 * Effect of a strike; has some bad effect in the player
 * 
 * @author theoklitos
 * 
 */
public abstract class Effect implements IEffect {

	public enum EffectType{
		BLEEDING,
		PENALTY,
		ROUND_LOSS,
		BREAK		
	}
	
	
	private static Logger LOG = Logger.getLogger(Effect.class);

	private Ruleset ruleset;
	private final int level;

	public Effect(final int initialLevel) {
		if (initialLevel < 1) {
			throw new StrikeEx("Effects need an initial level of > 0");
		}
		this.level = initialLevel;
	}

	@Override
	public void setRuleset(final Ruleset ruleset) {
		LOG.debug(getClass().getSimpleName() + " effect is using ruleset: " + ruleset);
		this.ruleset = ruleset;
	}

}
