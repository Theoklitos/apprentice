package com.apprentice.rpg.rules;

/**
 * a thing that can use a {@link Ruleset} to have even better functionality
 * 
 * @author theoklitos
 * 
 */
public interface RuleBased {

	/**
	 * will use the given {@link Ruleset} for any internal calculations
	 */
	void setRuleset(final Ruleset ruleset);
}
