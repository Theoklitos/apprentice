package com.apprentice.rpg.model.durable;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.rules.Ruleset;

/**
 * item with name and description, and a durability that can be changed (like hit points) that affects the
 * items dice(s)
 * 
 * @author theoklitos
 * 
 */
public interface IDurableItemInstance {

	/**
	 * adds the given number to this item's hp in regards to its durability
	 */
	void addHitPoints(int hpToAdd);

	/**
	 * returns a pair (max/current) that representsthe durability of this weapon
	 */
	CurrentMaximumPair getDurability();

	/**
	 * returns the current health of this item
	 */
	int getHitPoints();

	/**
	 * adds the given number to this item's hp in regards to its durability
	 */
	void removeHitPoints(int hpToRemove);

	/**
	 * set the item's hit points. Use this when the item is damaged/repaired, to set its health
	 */
	void setHitPoints(int current);

	/**
	 * will use the given {@link Ruleset} to determine deterioration
	 */
	void setRuleset(final Ruleset ruleset);
}
