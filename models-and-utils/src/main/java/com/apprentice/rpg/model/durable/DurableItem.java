package com.apprentice.rpg.model.durable;

import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.random.dice.Roll;

/**
 * item with a max durability, name and description
 * 
 * @author theoklitos
 * 
 */
public interface DurableItem extends Nameable {

	/**
	 * Depending on what this item is, this represents its basic roll that is modified as durability falls.
	 * For weapons its base damage, for armors its DR
	 */
	Roll getBaseRoll();

	/**
	 * returns the maximum durabtility for this weapon
	 */
	int getMaximumDurability();
	
	/**
	 * (re)sets this item's defining roll, for armors it DR, for weapons its bsae damage
	 */
	void setBaseRoll(final Roll roll);

	/**
	 * sets the maximum durabtility for this weapon
	 */
	void setMaximumDurability(int maxDurability);
}
