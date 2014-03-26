package com.apprentice.rpg.model.damage;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollException;
import com.apprentice.rpg.strike.StrikeType;
import com.apprentice.rpg.util.Checker;
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
	private Penetration penetration;

	/**
	 * copy constructor
	 */
	public DamageRoll(final DamageRoll damageRoll) {
		this(new Roll(damageRoll.getRoll()), damageRoll.getPenetration(), damageRoll.getType());
	}

	/**
	 * @throws ApprenticeEx
	 *             if penetration is < 0
	 */
	public DamageRoll(final Roll damage, final Penetration penetration, final StrikeType type) {
		Checker.checkNonNull("Missing values for damage roll", true, damage, penetration, type);
		this.roll = damage;
		this.type = type;
		this.penetration = penetration;
	}

	/**
	 * no penetration
	 */
	public DamageRoll(final Roll damage, final StrikeType type) {
		this.roll = damage;
		this.type = type;
	}

	/**
	 * @throws RollException
	 *             if the damange text is not understood to be a dice
	 */
	public DamageRoll(final String damage, final Penetration penetration, final StrikeType type) {
		this(new Roll(damage), penetration, type);
	}

	/**
	 * no penetration
	 * 
	 * @throws RollException
	 *             if the damange text is not understood to be a dice
	 */
	public DamageRoll(final String damage, final StrikeType type) {
		this(new Roll(damage), new Penetration(0), type);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof DamageRoll) {
			final DamageRoll otherDamageRoll = (DamageRoll) other;
			return Objects.equal(roll, otherDamageRoll.getRoll()) && Objects.equal(type, otherDamageRoll.getType())
					&& Objects.equal(type, otherDamageRoll.getType());
		} else {
			return false;
		}
	}

	/**
	 * How many HP of penetration (DR-ignoring) damage does this roll have?
	 */
	public Penetration getPenetration() {
		return penetration;
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
		return Objects.hashCode(roll, type, penetration);
	}

	@Override
	public String toString() {
		return roll + penetration.toString() + "[" + type + "]";
	}
}
