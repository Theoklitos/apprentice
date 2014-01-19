package com.apprentice.rpg.model.body;

import org.apache.commons.lang3.StringUtils;

/**
 * A character type is an existing {@link Type} plus his size and his race
 * 
 * @author theoklitos
 * 
 */
public final class CharacterType {

	private final static String NOT_SPECIFIED_RACE = "Not specified";

	private final Type type;
	private final Size size;
	private String race;

	/**
	 * Use this contructor if you don't care about the race
	 */
	public CharacterType(final Type type, final Size size) {
		this(type, size, null);
	}

	public CharacterType(final Type type, final Size size, final String race) {
		if (StringUtils.isBlank(race)) {
			this.race = NOT_SPECIFIED_RACE;
		}
		this.type = type;
		this.size = size;
	}

}
