package com.apprentice.rpg.model;

import org.apache.log4j.Logger;

import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.body.CharacterType;
import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * Basic class for the system, represents a human-avatar in the rpg game, a "PC"
 * 
 */
public final class PlayerCharacter extends BaseApprenticeObject implements IPlayerCharacter {

	private static Logger LOG = Logger.getLogger(PlayerCharacter.class);

	private final HitPoints hitPoints;
	private final CharacterType characterType;
	private final StatBundle statBundle;
	private final PlayerLevels levels;

	public PlayerCharacter(final String name, final int initialHitPoints, final StatBundle initialStatBundle,
			final CharacterType characterType) {
		super(name);
		Checker.checkNonNull("Initialized character with null or emtpy value(s)", true, initialStatBundle,
				characterType);
		this.characterType = characterType;
		hitPoints = new HitPoints(initialHitPoints);
		statBundle = initialStatBundle;
		levels = new PlayerLevels();
	}

	@Override
	public void addExperience(final int experiencePoints) {
		levels.addExperiencePoints(experiencePoints);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof PlayerCharacter) {
			final PlayerCharacter otherPC = (PlayerCharacter) other;
			LOG.info("Name equals: " + getName().equals(otherPC.getName()));
			LOG.info("HP equals: " + getHitPoints().equals(otherPC.getHitPoints()));
			LOG.info("Character type equals: " + getCharacterType().equals(otherPC.getCharacterType()));
			LOG.info("Stats equals: " + statBundle.equals(otherPC.statBundle));
			LOG.info("Levels: " + getLevels().equals(otherPC.getLevels()));
			return Objects.equal(getName(), otherPC.getName()) && Objects.equal(getHitPoints(), otherPC.getHitPoints())
				&& Objects.equal(getCharacterType(), otherPC.getCharacterType())
				&& Objects.equal(statBundle, otherPC.statBundle) && Objects.equal(getLevels(), otherPC.getLevels());
		} else {
			return false;
		}
	}

	@Override
	public CharacterType getCharacterType() {
		return characterType;
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
		return Objects.hashCode(getName(), getHitPoints(), getCharacterType(), statBundle, getLevels());
	}

	@Override
	public String toString() {
		return getName() + ", " + getLevels();
	}

}
