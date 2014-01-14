package com.apprentice.rpg.model;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

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
	 * Adds this XP to the total
	 */
	public void addExperiencePoints(final int earnedExperience) {
		experiencePoints += earnedExperience;
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

	/**
	 * returns the mappings of class name -> character level. This is immutable.
	 */
	public Map<String, Integer> getClassLevels() {
		return Maps.newHashMap(levels);
	}

	/**
	 * How much experience does this character have?
	 * 
	 * @return
	 */
	public int getExperiencePoints() {
		return experiencePoints;
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
		final String properName = StringUtils.capitalize(className.toLowerCase());
		return properName;
	}
}
