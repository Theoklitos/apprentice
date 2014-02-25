package com.apprentice.rpg.strike;

import java.util.Map;

import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.body.BodyPart;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * Represents what kind of strike this is, i.e. slashing, bludgeoning, piercing etc
 * 
 * @author theoklitos
 * 
 */
public final class StrikeType extends BaseApprenticeObject {

	private final Map<BodyPart, IntensityToEffectMapping> strikes;

	public StrikeType(final String name) {
		super(name);
		strikes = Maps.newHashMap();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof StrikeType) {
			final StrikeType otherStrikeType = (StrikeType) other;
			return super.equals(otherStrikeType) && Objects.equal(strikes, otherStrikeType.strikes);
		} else {
			return false;
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(strikes) + super.hashCode();
	}

	@Override
	public String toString() {
		return getName();
	}

}
