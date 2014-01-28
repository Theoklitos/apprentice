package com.apprentice.rpg.model;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * Describes a player character's level(s)
 * 
 * @author theoklitos
 * 
 */
public final class PlayerLevels {

	private final Map<String, Integer> levels;
	private int experiencePoints;

	public PlayerLevels() {
		levels = Maps.newHashMap();
		experiencePoints = 0;
	}

	/**
	 * Adds this XP to the total. You can subtract XP but this will never fall below zero
	 */
	public void addExperiencePoints(final int earnedExperience) {
		experiencePoints += earnedExperience;
		if (experiencePoints < 0) {
			experiencePoints = 0;
		}
	}

	/**
	 * Adds one level to the given class. If the given class does not exist, creates it. Note: class names are
	 * not case sensitive
	 */
	public void addLevel(final String className) {
		addLevels(className, 1);
	}

	/**
	 * Adds the # of levels to the given class. If the given class does not exist, creates it. Note: class
	 * names are not case sensitive
	 */
	public void addLevels(final String className, final int levelsToAdd) {
		final String properName = getProperClassName(className);
		final Integer existingLevel = levels.get(properName);
		if (existingLevel == null) {
			levels.put(properName, levelsToAdd);
		} else {
			levels.put(properName, existingLevel + levelsToAdd);
		}
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof PlayerLevels) {
			final PlayerLevels otherPlayerLevels = (PlayerLevels) other;
			return Objects.equal(levels, otherPlayerLevels.levels)
				&& experiencePoints == otherPlayerLevels.experiencePoints;
		} else {
			return false;
		}
	}

	/**
	 * How much experience does this character have?
	 */
	public int getExperiencePoints() {
		return experiencePoints;
	}

	/**
	 * returns a copy of the mappings of class name -> character level. 
	 */
	public Map<String, Integer> getLevels() {
		return Maps.newHashMap(levels);
	}

	/**
	 * Returns the levels for this class. If this class does not exist for this character, returns 0.
	 */
	public int getLevels(final String className) {
		return levels.get(getProperClassName(className)) == null ? 0 : levels.get(getProperClassName(className));
	}

	/**
	 * makes the name pretty & readable: all lower case + first capitalized
	 */
	private String getProperClassName(final String className) {
		final String properName = StringUtils.capitalize(className.trim().toLowerCase());
		return properName;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(levels, experiencePoints);
	}

	@Override
	public String toString() {
		String experienceString;
		if (experiencePoints == 0) {
			experienceString = "";
		} else {
			experienceString = ". XP: " + experiencePoints;
		}
		return Joiner.on("/").withKeyValueSeparator("").join(levels) + experienceString;
	}
}
