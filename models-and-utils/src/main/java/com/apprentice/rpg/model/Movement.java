package com.apprentice.rpg.model;

import com.google.common.base.Objects;

/**
 * Represents how much a character can move in feet
 * 
 * @author theoklitos
 * 
 */
public final class Movement {

	private int feet;

	public Movement(final int movementFeet) {
		setMovement(movementFeet);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Movement) {
			final Movement otherMovement = (Movement) other;
			return feet == otherMovement.feet;
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(feet);
	}

	/**
	 * @throws ApprenticeEx
	 *             if smaller than 0
	 */
	public void setMovement(final int newMovementInFeet) throws ApprenticeEx {
		if (newMovementInFeet < 0) {
			throw new ApprenticeEx("Movement must be at least 0ft, was " + newMovementInFeet);
		}
		this.feet = newMovementInFeet;
	}

	@Override
	public String toString() {
		return feet + "ft.";
	}

}
