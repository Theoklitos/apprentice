package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.durable.IDurableItem;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.parsing.ParsingEx;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.util.Box;

/**
 * Something a player uses to inflict damage. Can be either a prototype or an instance.
 * 
 * @author theoklitos
 * 
 */
public interface IWeapon extends IDurableItem, Nameable, Cloneable {

	/**
	 * Adds this melee damage to the existing melee damages. Cannot already exist.
	 * 
	 * @thows {@link ApprenticeEx} if this {@link DamageRoll} already exists
	 */
	void addMeleeDamage(DamageRoll daAt) throws ApprenticeEx;

	/**
	 * Deep copies this weapon, in order to be handed out to a player. Will only work in prototypes.
	 */
	IWeapon clone();

	/**
	 * Returns a map that maps the {@link AmmunitionType} this weapon can use along with their respective
	 * range
	 */
	public Map<AmmunitionType, Range> getAmmunitionsWithRange();

	/**
	 * Gets the offset that will affect this weapon's block scores. Default is 0.
	 */
	public int getBlockModifier();

	/**
	 * Returns any "extra" damages weapons might have besides the base melee ones, ie +1D6 fire damage for
	 * some magical weapons.
	 */
	public Collection<DamageRoll> getExtraDamages();

	/**
	 * Returns the melee damages this weapon can cause. Note: This is a copy of the list, cannot be modified.
	 * Use the add/remove methods to change this.
	 */
	public List<DamageRoll> getMeleeDamages();

	/**
	 * If this weapon is ranged = has a {@link Range} obejct set, returns it in a box
	 */
	public Box<Range> getRange();

	/**
	 * Returns, if any, how much ranged/thrown damage this weapon inflicts. Emtpy box if no thrown damage was
	 * set.
	 */
	public Box<DamageRoll> getThrownDamage();

	/**
	 * returns true if this weapon has a thrown range + damage
	 */
	boolean isThrownWeapon();

	/**
	 * Removes this damage from the existing melee damages. Must exist.
	 * 
	 * @thows {@link ApprenticeEx} if this {@link DamageRoll} does not exist
	 */
	void removeMeleeDamage(DamageRoll itemAt) throws ApprenticeEx;

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

	/**
	 * Enables this weapon to fire the given {@link AmmunitionType}
	 */
	void setAmmoType(AmmunitionType type, Range range);

	/**
	 * Sets an offset that will affect this weapon's block scores. Default is 0.
	 */
	public void setBlockModifier(final int modifier);

	/**
	 * Weapons might have extra damages besides the base, ie +1D6 fire damage for some magical weapons. This
	 * method sets those damages.
	 */
	public void setExtraDamages(Collection<DamageRoll> extraDamages);

	/**
	 * Sets the thrown range information for this weapon
	 * 
	 * @throws ApprenticeEx
	 *             if no ranged {@link DamageRoll} is set
	 */
	public void setRange(final Range range) throws ApprenticeEx;

	/**
	 * sets both the thrown damage and the {@link Range} for that damage
	 */
	public void setRangeAndThrownDamage(final Range range, final DamageRoll rangedDamage);

	/**
	 * sets both the thrown damage and the {@link Range} for that damage. Ranged will be parsed from
	 * the string
	 */
	public void setRangeAndThrownDamage(final String rangeAsString, final DamageRoll rangedDamage) throws ParsingEx;

	/**
	 * Sets how much thrown damage this weapon inflicts
	 * 
	 * @throws ApprenticeEx
	 *             if no {@link Range} or thrown damage is set
	 */
	public void setThrownDamage(final DamageRoll damageRoll) throws ApprenticeEx;

}
