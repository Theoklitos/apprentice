package com.apprentice.rpg.model.body;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.util.Checker;
import com.google.common.base.Objects;

/**
 * A character type is an existing {@link Type} plus his size and his race
 * 
 * @author theoklitos
 * 
 */
public final class CharacterType {

	private final static String NOT_SPECIFIED_RACE = "Not specified";

	private final IType type;
	private final String size;
	private String race;

	/**
	 * Use this contructor if you don't care about the race
	 */
	public CharacterType(final IType type, final Size size) {
		this(type, size, null);
	}

	public CharacterType(final IType type, final Size size, final String race) {
		if (StringUtils.isBlank(race)) {
			this.setRace(NOT_SPECIFIED_RACE);
		} else {
			this.setRace(race);
		}
		Checker.checkNonNull("Empty character type and/or size", true, type, size);
		this.type = type;
		this.size = size.name();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof CharacterType) {
			final CharacterType otherCT = (CharacterType) other;
			return Objects.equal(getType(), otherCT.getType()) && Objects.equal(getSize(), otherCT.getSize())
				&& Objects.equal(getRace(), otherCT.getRace());
		} else {
			return false;
		}
	}

	public String getRace() {
		return race;
	}

	public Size getSize() {
		return Size.valueOf(size);
	}

	/**
	 * What is the type of this character? Humanoid? Quadropod?
	 */
	public IType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getType(), getSize(), getRace());
	}

	/**
	 * what race does this character belong to? this is not specific - can be any stirng
	 */
	public void setRace(final String race) {
		this.race = race;
	}

}
