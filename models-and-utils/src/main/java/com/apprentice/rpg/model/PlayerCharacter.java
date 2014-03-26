package com.apprentice.rpg.model;

import java.util.Collection;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.armor.Armor;
import com.apprentice.rpg.model.armor.ArmorDoesNotFitEx;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.armor.PlayerArmor;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.model.combat.CombatCapabilities;
import com.apprentice.rpg.strike.Effect;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.Checker;
import com.apprentice.rpg.util.SortedList;
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
	private final Speed speed;
	private final SavingThrows savingThrows;
	private final StatBundle statBundle;
	private final PlayerLevels levels;
	private final SortedList<Skill> skills;
	private final CombatCapabilities combatCapabilities;
	private final PlayerArmor armor;
	private final Set<Effect> appliedEffects;

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
		this.speed = new Speed(movementFeet);
		hitPoints = new HitPoints(initialHitPoints);
		this.savingThrows = savingThrows;
		this.levels = levels;
		skills = new SortedList<Skill>(new SkillComparator());
		statBundle = initialStatBundle;
		combatCapabilities = new CombatCapabilities();
		armor = new PlayerArmor(characterType.getType());
		appliedEffects = Sets.newHashSet();
	}

	@Override
	public void addExperience(final int experiencePoints) {
		levels.addExperiencePoints(experiencePoints);
	}

	@Override
	public void addSkill(final Skill skill) {
		final Stat playerStat = statBundle.getStat(skill.getStat().getStatType());
		skill.setStat(playerStat);
		skills.add(skill);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof PlayerCharacter) {
			final PlayerCharacter otherPC = (PlayerCharacter) other;

			LOG.debug("Name equals: " + getName().equals(otherPC.getName()));
			LOG.debug("HP equals: " + getHitPoints().equals(otherPC.getHitPoints()));
			LOG.debug("Character type equals: " + getCharacterType().equals(otherPC.getCharacterType()));
			LOG.debug("Stats equals: " + statBundle.equals(otherPC.statBundle));
			LOG.debug("Movement: " + speed.equals(otherPC.speed));
			LOG.debug("Levels: " + getLevels().equals(otherPC.getLevels()));
			LOG.debug("Skills: " + ApprenticeCollectionUtils.areAllElementsEqual(skills, otherPC.skills));
			LOG.debug("Combat Capabilities: " + combatCapabilities.equals(otherPC.combatCapabilities));
			LOG.debug("Armor: " + armor.equals(otherPC.armor));
			LOG.debug("Effects: "
				+ ApprenticeCollectionUtils.areAllElementsEqual(appliedEffects, otherPC.appliedEffects));

			return super.equals(otherPC) && Objects.equal(getName(), otherPC.getName())
				&& Objects.equal(getHitPoints(), otherPC.getHitPoints())
				&& Objects.equal(getCharacterType(), otherPC.getCharacterType())
				&& Objects.equal(statBundle, otherPC.statBundle) && Objects.equal(speed, otherPC.speed)
				&& Objects.equal(getLevels(), otherPC.getLevels())
				&& ApprenticeCollectionUtils.areAllElementsEqual(skills, otherPC.skills)
				&& Objects.equal(combatCapabilities, otherPC.combatCapabilities) && Objects.equal(armor, otherPC.armor)
				&& ApprenticeCollectionUtils.areAllElementsEqual(appliedEffects, otherPC.appliedEffects);
		} else {
			return false;
		}
	}

	@Override
	public PlayerArmor getArmor() {
		return armor;
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

	@Override
	public Speed getMovement() {
		return speed;
	}

	@Override
	public SavingThrows getSavingThrows() {
		return savingThrows;
	}

	@Override
	public Box<Skill> getSkill(final String skillName) {
		for (final Skill skill : skills) {
			if (skill.getName().equals(StringUtils.capitalize(skillName.toLowerCase().trim()))) {
				return Box.with(skill);
			}
		}
		return Box.empty();
	}

	@Override
	public Collection<Skill> getSkills() {
		return skills;
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
	public void giveArmorToPlayer(final Armor armor) throws ArmorDoesNotFitEx {
		for (final BodyPart armorBodyPart : armor.getArmorToBodyPartMapping().keySet()) {
			if (!getCharacterType().getType().getBodyParts().contains(armorBodyPart)) {
				throw new ArmorDoesNotFitEx("Character " + getName() + " does not have body part \"" + armorBodyPart
					+ "\" so as to fit the " + armor.getName() + " armor.");
			}
		}
		for (final BodyPart bodyPart : getCharacterType().getType().getBodyParts()) {
			IArmorPiece armorPiece = armor.getArmorToBodyPartMapping().get(bodyPart);
			if (armorPiece != null) {
				armorPiece = armorPiece.clone();
			}
			this.armor.setArmorPieceForBodyPart(bodyPart, armorPiece);
		}
		this.armor.setName(armor.getName());
		this.armor.setDescription(armor.getDescription());
	}

	@Override
	public int hashCode() {
		return super.hashCode()
			+ Objects.hashCode(getName(), getHitPoints(), getCharacterType(), statBundle, getLevels(),
					combatCapabilities, armor, appliedEffects);
	}

	@Override
	public boolean removeSkill(final String skillName) {
		final Box<Skill> skillBox = getSkill(skillName);
		if (skillBox.isEmpty()) {
			return false;
		} else {
			return skills.remove(skillBox.getContent());
		}
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
