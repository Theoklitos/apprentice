package com.apprentice.rpg.model.combat;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.weapon.WeaponInstance;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * 
 * A collection of the player's weapons along with their {@link BonusSequence}s
 * 
 * @author theoklitos
 * 
 */
public final class CombatCapabilities {

	private int modifier;
	private final Map<WeaponInstance, BonusSequence> weaponSkills;

	public CombatCapabilities() {
		weaponSkills = Maps.newHashMap();
		modifier = 0;
	}

	/**
	 * will add this integer to the modifier
	 */
	public void addToModifier(final int toAdd) {
		modifier += toAdd;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof CombatCapabilities) {
			final CombatCapabilities otherCapabilities = (CombatCapabilities) other;
			return (modifier == otherCapabilities.modifier)
				&& ApprenticeCollectionUtils.areAllElementsEqual(weaponSkills, otherCapabilities.weaponSkills);
		} else {
			return false;
		}
	}

	/**
	 * Returns the modified
	 * 
	 * @throws ApprenticeEx
	 *             if the given weapon does not exist
	 */
	public BonusSequence getBonusSequenceForWeapon(final WeaponInstance weaponInstance) {
		final BonusSequence result = weaponSkills.get(weaponInstance);
		if (result == null) {
			throw new ApprenticeEx("No skill with weapon \"" + weaponInstance.getName() + "\"");
		} else {
			final BonusSequence modified = new BonusSequence(result);
			modified.modify(modifier);
			return modified;
		}
	}

	/**
	 * how much are the capabilities affected?
	 */
	public int getModifier() {
		return modifier;
	}

	/**
	 * returns a modifiable referrence to the {@link WeaponInstance}s
	 */
	public Set<WeaponInstance> getWeapons() {
		return weaponSkills.keySet();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(modifier, weaponSkills);
	}

	/**
	 * sets or replaces the {@link BonusSequence} for the given {@link WeaponInstance}
	 */
	public void setWeaponForSequence(final WeaponInstance weaponInstance, final BonusSequence sequence) {
		weaponSkills.put(weaponInstance, sequence);
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		final Iterator<Entry<WeaponInstance, BonusSequence>> iterator = weaponSkills.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<WeaponInstance, BonusSequence> entry = iterator.next();
			result.append("[" + entry.getKey().getName() + ":" + entry.getValue() + "]");
			// if (iterator.hasNext()) {
			// result.append("\n");
			// }
		}
		return result.toString();
	}
}
