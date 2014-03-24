package com.apprentice.rpg.strike;

import java.util.List;
import java.util.Map;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Objects;
import com.google.common.collect.Maps;

/**
 * Maps 10+ levels of strike intensity to their appropriate effects
 * 
 * @author theoklitos
 * 
 */
public final class IntensityToEffectMapping {

	private final Map<Integer, List<Effect>> mapping;

	public IntensityToEffectMapping() {
		mapping = Maps.newTreeMap();
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof IntensityToEffectMapping) {
			final IntensityToEffectMapping otherMapping = (IntensityToEffectMapping) other;
			return ApprenticeCollectionUtils.areAllElementsEqual(mapping, otherMapping.mapping);
		} else {
			return false;
		}
	}

	/**
	 * Returns a box with the effects for this intensity, emtpy box if nothing has been defined.
	 */
	public Box<List<Effect>> getEffectsForIntensity(final int intensity) {
		final List<Effect> effects = mapping.get(intensity);
		if (effects == null) {
			return Box.empty();
		} else {
			return Box.with(effects);
		}
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(mapping);
	}

	/**
	 * Defines what the effects will be for the given intensity. Will replace any previous values.
	 * 
	 * @throws ApprenticeEx
	 *             if the intensity is < 1
	 */
	public void setIntensityForEffect(final int intensity, final List<Effect> effects) throws ApprenticeEx {
		if (intensity < 1) {
			throw new ApprenticeEx("Strike intensity must be > 0");
		}
		mapping.put(intensity, effects);
	}

	/**
	 * how many mappings are there?
	 */
	public int size() {
		return mapping.size();
	}
}
