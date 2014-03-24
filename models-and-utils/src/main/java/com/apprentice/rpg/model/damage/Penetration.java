package com.apprentice.rpg.model.damage;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.util.Box;
import com.google.common.base.Objects;

/**
 * The capability of some weapons/ammo to ignore DR
 * 
 * @author theoklitos
 * 
 */
public class Penetration {

	public enum PenetrationType {
		/**
		 * ignores all DR
		 */
		FULL,

		/**
		 * ignores half the DR
		 */
		HALF
	}

	private final int hitPoints;
	private final String penetrationType;

	public Penetration(final int penetrationHP) {
		this("", penetrationHP);
	}

	public Penetration(final PenetrationType type) {
		this(type.toString(), -1);
	}

	private Penetration(final String penetrationType, final int penetrationHP) {
		this.penetrationType = penetrationType;
		this.hitPoints = penetrationHP;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Penetration) {
			final Penetration otherWindowState = (Penetration) other;
			return Objects.equal(penetrationType, otherWindowState.penetrationType)
				&& hitPoints == otherWindowState.hitPoints;
		} else {
			return false;
		}
	}

	/**
	 * if the penetration was defined to a number, returns a box with the penetration HP
	 */
	public Box<Integer> getPenetrationHP() {
		if (hitPoints == -1) {
			return Box.empty();
		} else {
			return Box.with(hitPoints);
		}
	}

	/**
	 * if the penetration was defined to be a {@link PenetrationType}, returns a box with that type
	 */
	public Box<PenetrationType> getPenetrationType() {
		if (StringUtils.isBlank(penetrationType)) {
			return Box.empty();
		} else {
			return Box.with(PenetrationType.valueOf(penetrationType));
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(penetrationType, hitPoints);
	}

}
