package com.apprentice.rpg.model.damage;

import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollException;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.base.Objects;

/**
 * A {@link Roll} along with the type of damage it is
 * 
 * @author theoklitos
 * 
 */
public class DamageRoll {

	private final Roll roll;
	private final StrikeType type;

	public DamageRoll(final Roll damage, final StrikeType type) {
		this.roll = damage;
		this.type = type;
	}

	/**
	 * @throws RollException
	 *             if the damange text is not understood to be a dice
	 */
	public DamageRoll(final String damage, final StrikeType type) {
		this.roll = new Roll(damage);
		this.type = type;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof DamageRoll) {
			final DamageRoll otherDamageRoll = (DamageRoll) other;
			return Objects.equal(roll, otherDamageRoll.getRoll()) && Objects.equal(type, otherDamageRoll.getType());
		} else {
			return false;
		}
	}

	/**
	 * returns the damage dice+modifiers
	 */
	public Roll getRoll() {
		return roll;
	}

	/**
	 * What type of strike is this damage?
	 */
	public StrikeType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(roll, type);
	}

	@Override
	public String toString() {
		return roll + "(" + type + ")";
	}
}
