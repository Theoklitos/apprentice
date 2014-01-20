package com.apprentice.rpg.model.magic;

/**
 * The "mana" of a player; the bigger this number, the more spells he can cast. Based on D&D 2.5 edition,
 * Spells & Magic
 * 
 * @author theoklitos
 * 
 */
public final class SpellPoints {

	private int currentSpellPoints;
	private int maximumSpellPoints;

	/**
	 * No spell points
	 */
	public SpellPoints() {
		this(0, 0);
	}

	/**
	 * current spell points will be set to max
	 */
	public SpellPoints(final int maximumSpellPoints) {
		this(maximumSpellPoints, maximumSpellPoints);
	}

	public SpellPoints(final int currentSpellPoints, final int maximumSpellPoints) {
		setMaximumSpellPoints(maximumSpellPoints);
		setCurrentSpellPoints(currentSpellPoints);
	}

	public int getCurrentSpellPoints() {
		return currentSpellPoints;
	}

	public int getMaximumSpellPoints() {
		return maximumSpellPoints;
	}

	/**
	 * throws {@link IllegalArgumentException} cannot be larger than maximum spell points!
	 */
	public void setCurrentSpellPoints(final int currentSpellPoints) throws IllegalArgumentException {
		if (currentSpellPoints > getMaximumSpellPoints()) {
			throw new IllegalArgumentException("Current spell points (" + currentSpellPoints
				+ ") where set to be larger than the maximum (" + getMaximumSpellPoints() + ")");
		} else {
			this.currentSpellPoints = currentSpellPoints;
		}
	}

	/**
	 * sets the maximum spell points. If this is smaller than the current spell points, they are reduced to
	 * the max
	 */
	public void setMaximumSpellPoints(final int maximumSpellPoints) {
		this.maximumSpellPoints = maximumSpellPoints;
		if (getCurrentSpellPoints() > maximumSpellPoints) {
			setCurrentSpellPoints(maximumSpellPoints);
		}
	}
}
