package com.apprentice.rpg.model.weapon;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.strike.StrikeType;
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
	private final int hitPoints;

	/**
	 * @throws ApprenticeEx
	 *             if the dmg HP are below 0
	 */
	public Damage(final int hitPointsOfDamage, final StrikeType type) throws ApprenticeEx {
		if (hitPointsOfDamage < 0) {
			throw new ApprenticeEx("Damage must be at least 0");
		}
		Checker.checkNonNull("Damage needs a StrikeType", true, type);
		this.hitPoints = hitPointsOfDamage;
		this.type = type;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Damage) {
			final Damage otherDamage = (Damage) other;
			return Objects.equal(type, otherDamage.type) && hitPoints == otherDamage.hitPoints;
		} else {
			return false;
		}
	}

	/**
	 * how many HPs of damage?
	 */
	public int getHitPoints() {
		return hitPoints;
	}

	/**
	 * what is the type of this damage?
	 */
	public StrikeType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(type, hitPoints);
	}

	@Override
	public String toString() {
		String word = " points ";
		if (hitPoints == 1) {
			word = " point ";
		}
		return hitPoints + word + "of " + getType() + " damage";
	}
}
