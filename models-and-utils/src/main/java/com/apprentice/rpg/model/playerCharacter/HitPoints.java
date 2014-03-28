package com.apprentice.rpg.model.playerCharacter;

import com.apprentice.rpg.model.CurrentMaximumPair;
import com.google.common.base.Objects;

/**
 * Represents health
 * 
 * @author theoklitos
 * 
 */
public final class HitPoints {

	private final CurrentMaximumPair values;

	/**
	 * Sets both max and current points to the given int
	 */
	public HitPoints(final int initialHitPoints) {
		values = new CurrentMaximumPair(initialHitPoints);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof HitPoints) {
			final HitPoints otherHP = (HitPoints) other;
			return Objects.equal(values, otherHP.values);
		} else {
			return false;
		}
	}

	/**
	 * what are the HPs the character has now?
	 */
	public int getCurrentHitPoints() {		
		return values.getCurrent();
	}

	/**
	 * what are the max HPs the character can have?
	 */
	public int getMaximumHitPoints() {
		return values.getMaximum();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(values);
	}

	/**
	 * Sets the current hp value. Will not go over the maximum.
	 */
	public void setCurrentHitPoints(final int currentHitPoints) {
		values.setCurrent(currentHitPoints);
	}

	/**
	 * Changes the max hp value. Will adjust the current number also, if it becomes bigger than the max.
	 */
	public void setMaximumHitPoints(final int maximumHitPoints) {
		values.setMaximum(maximumHitPoints);
	}

	@Override
	public String toString() {
		return values + " hp";
	}
}
