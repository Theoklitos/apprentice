package com.apprentice.rpg.model;

import java.util.Set;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.combat.CombatCapabilities;
import com.apprentice.rpg.strike.IEffect;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;
import com.google.common.collect.Sets;

/**
 * Basic class for the system, represents a human-avatar in the rpg game, a "PC"
 * 
 */
public final class PlayerCharacter extends BaseApprenticeObject implements IPlayerCharacter {

	private static Logger LOG = Logger.getLogger(PlayerCharacter.class);

	private final HitPoints hitPoints;
	private final CharacterType characterType;
	private final Movement movement;
	private final SavingThrows savingThrows;
	private final StatBundle statBundle;
	private final PlayerLevels levels;
	private final CombatCapabilities combatCapabilities;
	private final Set<IEffect> appliedEffects;

	/**
	 * sets emtpy {@link PlayerLevels}
	 */
	public PlayerCharacter(final String name, final int initialHitPoints, final StatBundle initialStatBundle,
			final CharacterType characterType, final int movementFeet, final SavingThrows savingThrows) {
		this(name, new PlayerLevels(), initialHitPoints, initialStatBundle, characterType, movementFeet, savingThrows);
	}

	public PlayerCharacter(final String name, final PlayerLevels levels, final int initialHitPoints,
			final StatBundle initialStatBundle, final CharacterType characterType, final int movementFeet,
			final SavingThrows savingThrows) {
		super(name);
		Checker.checkNonNull("Initialized character with null or empty value(s)", true, initialStatBundle,
				characterType, savingThrows);
		this.characterType = characterType;
		this.movement = new Movement(movementFeet);
		hitPoints = new HitPoints(initialHitPoints);
		this.savingThrows = savingThrows;
		this.levels = levels;
		statBundle = initialStatBundle;
		combatCapabilities = new CombatCapabilities();
		appliedEffects = Sets.newHashSet();
	}

	@Override
	public void addExperience(final int experiencePoints) {
		levels.addExperiencePoints(experiencePoints);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof PlayerCharacter) {
			final PlayerCharacter otherPC = (PlayerCharacter) other;

//			LOG.debug("Name equals: " + getName().equals(otherPC.getName()));
//			LOG.debug("HP equals: " + getHitPoints().equals(otherPC.getHitPoints()));
//			LOG.debug("Character type equals: " + getCharacterType().equals(otherPC.getCharacterType()));
//			LOG.debug("Stats equals: " + statBundle.equals(otherPC.statBundle));
//			LOG.debug("Levels: " + getLevels().equals(otherPC.getLevels()));
//			LOG.debug("Combat Capabilities: " + combatCapabilities.equals(otherPC.combatCapabilities));
//			LOG.debug("Effects: "
//				+ ApprenticeCollectionUtils.areAllElementsEqual(appliedEffects, otherPC.appliedEffects));

			return super.equals(otherPC) && Objects.equal(getName(), otherPC.getName())
				&& Objects.equal(getHitPoints(), otherPC.getHitPoints())
				&& Objects.equal(getCharacterType(), otherPC.getCharacterType())
				&& Objects.equal(statBundle, otherPC.statBundle) && Objects.equal(getLevels(), otherPC.getLevels())
				&& Objects.equal(combatCapabilities, otherPC.combatCapabilities)
				&& ApprenticeCollectionUtils.areAllElementsEqual(appliedEffects, otherPC.appliedEffects);
		} else {
			return false;
		}
	}

	@Override
	public CharacterType getCharacterType() {
		return characterType;
	}

	@Override
	public CombatCapabilities getCombatCapabilities() {
		return combatCapabilities;
	}

	@Override
	public HitPoints getHitPoints() {
		return hitPoints;
	}

	@Override
	public PlayerLevels getLevels() {
		return levels;
	}

	/**
	 * returns one of the 6 character's statistics
	 */
	public Stat getStat(final StatType type) {
		return statBundle.getStat(type);
	}

	@Override
	public StatBundle getStatBundle() {
		return statBundle;
	}

	@Override
	public int hashCode() {
		return super.hashCode()
			+ Objects.hashCode(getName(), getHitPoints(), getCharacterType(), statBundle, getLevels(),
					combatCapabilities, appliedEffects);
	}

	@Override
	public String toString() {
		if (getLevels().getLevels().size() == 0) {
			return getName();
		} else {
			return getName() + ", " + getLevels();
		}
	}

}
