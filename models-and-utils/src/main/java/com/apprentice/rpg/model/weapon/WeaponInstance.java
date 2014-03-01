package com.apprentice.rpg.model.weapon;

import java.util.List;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.random.ApprenticeRandom;

/**
 * A {@link WeaponPrototype} that is handed out to a player character
 * 
 * @author theoklitos
 * 
 */
public interface WeaponInstance extends IWeapon {

	/**
	 * Rolls the selected {@link DamageRoll}, adds the extra damage, and returns the result
	 * 
	 * @throws ApprenticeEx
	 *             if this weapon does not have such a damage roll
	 */
	List<Damage> rollMeleeDamage(final DamageRoll damageRoll, ApprenticeRandom random) throws ApprenticeEx;

	/**
	 * Rolls the numbered {@link DamageRoll} from the list of melee damages, adds the extra damage, and
	 * returns the result
	 * 
	 * @throws ApprenticeEx
	 *             if this weapon does not have such a damage roll
	 */
	List<Damage> rollMeleeDamage(final int number, ApprenticeRandom random) throws ApprenticeEx;

}
