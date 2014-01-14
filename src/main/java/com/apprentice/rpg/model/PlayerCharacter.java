package com.apprentice.rpg.model;

import java.util.List;

import com.apprentice.rpg.model.body.Type;
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
	private final Type type;	
	private final PlayerLevels levels;
	private final CombatCapability combatCapability;	
	private final Inventory equipment;	
	private final MagicalCapability spells;

	private final List<Strike> appliedStrikes;
	
	public PlayerCharacter(final String name, final int initialHitPoints, final Type type) {
		this.name = name;
		this.type = type;
		hp = new HitPoints(initialHitPoints);
		levels = new PlayerLevels();		
		equipment = new Inventory();
		
		spells = new MagicalCapability();
		combatCapability = new CombatCapability();
		
		appliedStrikes = Lists.newArrayList();
	}

	/**
	 * returns the information regarding this character's levels and XP 
	 */
	public PlayerLevels getLevels() {
		return levels;
	}
}
