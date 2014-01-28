package com.apprentice.rpg.model.magic;

import com.apprentice.rpg.model.CurrentMaximumPair;

/**
 * The "mana" of a player; the bigger this number, the more spells he can cast. Based on D&D 2.5 edition,
 * Spells & Magic
 * 
 * @author theoklitos
 * 
 */
public final class SpellPoints {

	private final CurrentMaximumPair values;

	/**
	 * No spell points
	 */
	public SpellPoints() {
		this(0);
	}

	/**
	 * current spell points will be set to max
	 */
	public SpellPoints(final int maximumSpellPoints) {
		values = new CurrentMaximumPair(0);
	}

	public int getCurrentSpellPoints() {
		return values.getCurrent();
	}

	public int getMaximumSpellPoints() {
		return values.getMaximum();
	}

	public void setCurrentSpellPoints(final int currentSpellPoints) {
		values.setCurrent(currentSpellPoints);
	}

	public void setMaximumSpellPoints(final int maximumSpellPoints) {
		values.setMaximum(maximumSpellPoints);
	}
}
