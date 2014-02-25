package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.List;

import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.durable.IDurableItemInstance;
import com.apprentice.rpg.random.ApprenticeRandom;

/**
 * A {@link Weapon} that is handed out to a player character
 * 
 * @author theoklitos
 * 
 */
public interface IWeaponInstance extends IDurableItemInstance<Weapon> {

	/**
	 * The current nase damage of the weapon. Might be less than original due to deterioration
	 */
	public DamageRoll getBaseDamage();

	/**
	 * Weapons might have extra damages besides the base, ie +1D6 fire damage for some magical weapons.
	 */
	public Collection<DamageRoll> getExtraDamages();

	/**
	 * Rolls the damage dice(s) and returns a list of {@link Damage}s, one per each {@link DamageRoll} this
	 * weapon has
	 */
	List<Damage> rollDamage(ApprenticeRandom random);

}
