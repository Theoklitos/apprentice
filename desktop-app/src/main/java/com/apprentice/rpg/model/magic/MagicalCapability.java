package com.apprentice.rpg.model.magic;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A list of all the spells a character has available, regardless of whether they are cast or not. plus his
 * Spell Points and other spell-related information
 */
public final class MagicalCapability {

	private final SpellPoints spellPoints;
	private final List<Spell> spells;

	/**
	 * Use this constructor if there is no capability
	 */
	public MagicalCapability() {
		this(0);
	}

	public MagicalCapability(final int maximumSpellPoints) {
		spellPoints = new SpellPoints(maximumSpellPoints);
		spells = Lists.newArrayList();
	}

	/**
	 * can this character cast spells?
	 */
	public boolean hasMagicCapability() {
		return spellPoints.getMaximumSpellPoints() != 0;
	}
}
