package com.apprentice.rpg.model.weapon;

import java.util.Collection;
import java.util.HashSet;

import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.durable.DurableItemPrototype;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;

/**
 * A weapon a PC uses in order to inflict damage
 * 
 * @author theoklitos
 * 
 */
public class WeaponPrototype extends DurableItemPrototype implements Weapon {

	private final DamageRoll baseDamage;
	private final Collection<DamageRoll> extraDamages;

	public WeaponPrototype(final String name, final int durability, final DamageRoll baseDamage) {
		this(name, durability, baseDamage, new HashSet<DamageRoll>());
	}

	/**
	 * use this contructor when you have extra damange modifiers i.e. +1D8 fire
	 */
	public WeaponPrototype(final String name, final int durability, final DamageRoll baseDamage,
			final Collection<DamageRoll> extraDamages) {
		super(name, durability, baseDamage.getRoll());
		this.baseDamage = baseDamage;
		this.extraDamages = extraDamages;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof WeaponPrototype) {
			final WeaponPrototype otherWeapon = (WeaponPrototype) other;
			return super.equals(otherWeapon)
				&& Objects.equal(getOriginalBaseDamage(), otherWeapon.getOriginalBaseDamage())
				&& ApprenticeCollectionUtils.areAllElementsEqual(getExtraDamages(), otherWeapon.getExtraDamages());
		} else {
			return false;
		}
	}

	@Override
	public Collection<DamageRoll> getExtraDamages() {
		return extraDamages;
	}

	@Override
	public DamageRoll getOriginalBaseDamage() {
		return baseDamage;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(baseDamage, extraDamages);
	}

	@Override
	public String toString() {
		String appendix = "";
		if (extraDamages.size() > 0) {
			appendix = ", extra dmgs: " + Joiner.on(",").join(extraDamages);
		}
		return getName() + ", base dmg: " + baseDamage.getRoll() + appendix + ". Max dur.: " + getMaximumDurability();
	}

}
