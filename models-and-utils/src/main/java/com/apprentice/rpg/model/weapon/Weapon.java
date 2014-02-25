package com.apprentice.rpg.model.weapon;

import java.util.Collection;

import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.durable.DurableItem;

/**
 * A weapon a PC uses in order to inflict damage
 * 
 * @author theoklitos
 * 
 */
public interface Weapon extends DurableItem {

	/**
	 * Weapons might have extra damages besides the base, ie +1D6 fire damage for some magical weapons. Note:
	 * This returns a reference, so treat with care
	 */
	public Collection<DamageRoll> getExtraDamages();

	/**
	 * What is the base damage this weapon is supposed to inflict, at optimal condition
	 */
	public DamageRoll getOriginalBaseDamage();
}
