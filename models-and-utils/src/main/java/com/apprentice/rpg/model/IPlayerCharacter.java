package com.apprentice.rpg.model;

import com.apprentice.rpg.model.body.CharacterType;


/**
 * Basic class for the system, represents a human-avatar in the rpg game, a "PC"
 * 
 */
public interface IPlayerCharacter extends Nameable {

	/**
	 * Adds the given points to the character's total
	 */
	void addExperience(int experiencePoints);

	/**
	 * returns this character's {@link CharacterType}
	 */
	CharacterType getCharacterType();

	/**
	 * Returns this character's {@link HitPoints}
	 */
	HitPoints getHitPoints();

	/**
	 * returns the information regarding this character's levels and XP
	 */
	PlayerLevels getLevels();

	/**
	 * Returns the character's name
	 */
	@Override
	String getName();

	/**
	 * Returns the {@link StatBundle} which contain's the character's six {@link Stat}s
	 */
	StatBundle getStatBundle();

}
