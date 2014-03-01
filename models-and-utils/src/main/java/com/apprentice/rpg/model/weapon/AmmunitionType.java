package com.apprentice.rpg.model.weapon;

import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.google.common.base.Objects;

/**
 * Some types of weapons use projectiles for ranged attacks
 * 
 * @author theoklitos
 * 
 */
public class AmmunitionType extends BaseApprenticeObject {

	private DamageRoll damage;

	public AmmunitionType(final String name, final DamageRoll damage) {
		super(name);
		this.setDamage(damage);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof AmmunitionType) {
			final AmmunitionType otherAmmo = (AmmunitionType) other;
			return super.equals(otherAmmo) && Objects.equal(damage, otherAmmo.damage);
		} else {
			return false;
		}
	}

	/**
	 * how much damage does this projectile do on impact?
	 */
	public DamageRoll getDamage() {
		return damage;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(damage, damage);
	}

	/**
	 * sets the damage for a projectile of this type
	 */
	public void setDamage(final DamageRoll damage) {
		this.damage = damage;
	}

	@Override
	public String toString() {
		return getName() + "," + damage;
	}
}
