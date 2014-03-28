package com.apprentice.rpg.model.playerCharacter;

import java.util.Collection;

import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorDoesNotFitEx;
import com.apprentice.rpg.model.armor.PlayerArmor;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.combat.CombatCapabilities;
import com.apprentice.rpg.model.playerCharacter.audit.AuditTrail;
import com.apprentice.rpg.util.Box;

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
	 * Adds the given skill to the player's list of skills. Will use whatever {@link Stat} that player has.
	 */
	void addSkill(Skill skill);

	/**
	 * returns this player's {@link PlayerArmor}
	 */
	PlayerArmor getArmor();

	/**
	 * returns this player's {@link AuditTrail}
	 */
	AuditTrail getAuditTrail();

	/**
	 * returns this character's {@link CharacterType}
	 */
	CharacterType getCharacterType();

	/**
	 * returns the character's skill with various weapons
	 */
	CombatCapabilities getCombatCapabilities();

	/**
	 * Returns this character's {@link HitPoints}
	 */
	HitPoints getHitPoints();

	/**
	 * returns the information regarding this character's levels and XP
	 */
	PlayerLevels getLevels();

	/**
	 * how much can this character move?
	 */
	Speed getMovement();

	/**
	 * Get this characters 3 saving throws
	 */
	SavingThrows getSavingThrows();

	/**
	 * Returns a box with the skill that is named as given. If no such named skill exists, returns empty box
	 */
	Box<Skill> getSkill(String skillName);

	/**
	 * Returns all the {@link Skill}s of this character
	 */
	Collection<Skill> getSkills();

	/**
	 * Returns the {@link StatBundle} which contain's the character's six {@link Stat}s
	 */
	StatBundle getStatBundle();

	/**
	 * "wears" the given armor to this player. WIll overrite any existing armor/armor pieces
	 * 
	 * @throws if
	 *             the body parts dont fit the type
	 */
	void giveArmorToPlayer(Armor armor) throws ArmorDoesNotFitEx;

	/**
	 * Looks for a skill with this name and deletes it. Returns true if the deletion was succesful.
	 */
	boolean removeSkill(String skillName);

}
