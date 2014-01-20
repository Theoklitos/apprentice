package com.apprentice.rpg.model;

/**
 * Represents health
 * 
 * @author theoklitos
 * 
 */
public final class HitPoints {

	private int maximumHitPoints;
	private int currentHitPoints;

	public HitPoints() {
		this(0);
	}

	/**
	 * Sets both max and current points to the given int
	 */
	public HitPoints(final int initialHitPoints) {
		setMaximumHitPoints(initialHitPoints);
		setCurrentPoints(initialHitPoints);
	}

	/**
	 * Sets the current hp value. Will not go over the maximum.
	 */
	public void setCurrentPoints(final int currentHitPoints) {
		if (currentHitPoints > maximumHitPoints) {
			this.currentHitPoints = maximumHitPoints;
		} else {
			this.currentHitPoints = currentHitPoints;
		}
	}

	/**
	 * Changes the max hp value. Will adjust the current number also, if it becomes bigger than the max.
	 */
	public void setMaximumHitPoints(final int maximumHitPoints) {
		this.maximumHitPoints = maximumHitPoints;
		if (currentHitPoints > this.maximumHitPoints) {
			currentHitPoints = this.maximumHitPoints;
		}

	}
}
