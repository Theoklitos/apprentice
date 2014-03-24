package com.apprentice.rpg.model.damage;

import com.apprentice.rpg.strike.StrikeType;
import com.apprentice.rpg.util.ApprenticeStringUtils;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * A number of damage points along with the {@link StrikeType}
 * 
 * @author theoklitos
 * 
 */
public final class Damage {

	private final StrikeType type;
	private final int damage;
	private final Penetration penetration;

	public Damage(final int hitPointsOfDamage, final Penetration penetration, final StrikeType type) {
		Checker.checkNonNull("Damage needs a StrikeType and a penetration", true, type, penetration);
		this.damage = hitPointsOfDamage;
		this.type = type;
		this.penetration = penetration;
	}

	public Damage(final int hitPointsOfDamage, final StrikeType type) {
		this(hitPointsOfDamage, new Penetration(0), type);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Damage) {
			final Damage otherDamage = (Damage) other;
			return Objects.equal(type, otherDamage.type) && damage == otherDamage.damage
				&& Objects.equal(penetration, otherDamage.penetration);
		} else {
			return false;
		}
	}

	/**
	 * how many HPs of damage?
	 */
	public int getDamageHP() {
		return damage;
	}

	/**
	 * The penetration, if any, is damange that ignores DR
	 */
	public Penetration getPenetrationHP() {
		return penetration;
	}

	/**
	 * what is the type of this damage?
	 */
	public StrikeType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(type, damage, penetration);
	}

	@Override
	public String toString() {
		String word = " points ";
		if (damage == 1) {
			word = " point ";
		}
		String suffix = ".";
		if (penetration.getPenetrationHP().hasContent()) {
			suffix += " Penetration: " + String.valueOf(penetration.getPenetrationHP().getContent());
		} else if (penetration.getPenetrationType().hasContent()) {
			suffix +=
				" Penetration: " + ApprenticeStringUtils.getReadableEnum(penetration.getPenetrationType().getContent());
		}
		return damage + word + "of " + getType() + " damage" + suffix;
	}
}
