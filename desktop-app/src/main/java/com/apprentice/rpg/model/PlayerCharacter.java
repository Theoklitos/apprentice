package com.apprentice.rpg.model;

import java.util.List;

import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.combat.CombatCapability;
import com.apprentice.rpg.model.items.Inventory;
import com.apprentice.rpg.model.magic.MagicalCapability;
import com.apprentice.rpg.strikes.Strike;
import com.google.common.collect.Lists;

/**
 * Basic class for the system, represents a human-avatar in the rpg game, a "PC"
 * 
 */
public final class PlayerCharacter {

	private final String name;
	private final HitPoints hp;
	private final CharacterType characterType;
	private final StatBundle statistics;
	private final PlayerLevels levels;
	
	private final CombatCapability combatCapability;
	private final Inventory equipment;
	private final MagicalCapability spells;
	
	private final List<Strike> appliedStrikes;

	public PlayerCharacter(final String name, final int initialHitPoints, final StatBundle initialStatBundle,
			final CharacterType characterType) {
		this.name = name;
		this.characterType = characterType;
		hp = new HitPoints(initialHitPoints);
		this.statistics = initialStatBundle;
		levels = new PlayerLevels();
		equipment = new Inventory();

		spells = new MagicalCapability();
		combatCapability = new CombatCapability();

		appliedStrikes = Lists.newArrayList();
	}

	/**
	 * Adds the given points to the character's total
	 */
	public void addExperience(final int experiencePoints) {
		levels.addExperiencePoints(experiencePoints);
	}

	/**
	 * Returns this character's {@link HitPoints}
	 */
	public HitPoints getHitPoints() {
		return hp;
	}

	/**
	 * returns the information regarding this character's levels and XP
	 */
	public PlayerLevels getLevels() {
		return levels;
	}

	/**
	 * Returns the character's name
	 */
	public String getName() {
		return name;
	}

}
