package com.apprentice.rpg.strike;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.body.BaseApprenticeObject;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.util.Box;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
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

	/**
	 * Returns a box ith the effects for the given bodyaprt+intensity. Emtpy box if no such effect(s) have
	 * been set.
	 */
	public Box<List<Effect>> getEffectsForBodyPart(final BodyPart bodyPart, final int intensity) {
		return getMappingForBodyPart(bodyPart).getEffectsForIntensity(intensity);
	}

	/**
	 * Returns what {@link IntensityToEffectMapping} is mapped for this bodypart
	 */
	public IntensityToEffectMapping getMappingForBodyPart(final BodyPart bodyPart) {
		IntensityToEffectMapping mapping = strikes.get(bodyPart);
		if (mapping == null) {
			mapping = new IntensityToEffectMapping();
			strikes.put(bodyPart, mapping);
		}
		return mapping;
	}

	@Override
	public int hashCode() {
		return super.hashCode() + Objects.hashCode(strikes);
	}

	/**
	 * 
	 * Sets the single effect for this bodypart + intensity. Intensity cannot be < 1. Will replace any
	 * previous mapping.
	 * 
	 * @throws ApprenticeEx
	 */
	public void setEffects(final BodyPart bodyPart, final int intensity, final Effect effect) throws ApprenticeEx {
		setEffects(bodyPart, intensity, Lists.newArrayList(effect));
	}

	/**
	 * 
	 * Sets the given effects for this bodypart + intensity. Intensity cannot be < 1. Will replace any
	 * previous
	 * mapping.
	 * 
	 * @throws ApprenticeEx
	 */
	public void setEffects(final BodyPart bodyPart, final int intensity, final List<Effect> effects)
			throws ApprenticeEx {
		final IntensityToEffectMapping mapping = getMappingForBodyPart(bodyPart);
		mapping.setIntensityForEffect(intensity, effects);
	}

	/**
	 * Sets the effects for the given bodypart+intensity
	 * 
	 * @throws ApprenticeEx
	 *             if intensity is < 1
	 */
	public void setEffectsForBodyPart(final BodyPart bodyPart, final int intensity, final List<Effect> effects) {
		getMappingForBodyPart(bodyPart).setIntensityForEffect(intensity, effects);
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		final Iterator<Entry<BodyPart, IntensityToEffectMapping>> iterator = strikes.entrySet().iterator();
		if (strikes.size() > 0) {
			result.append(":");
		}
		while (iterator.hasNext()) {
			final Entry<BodyPart, IntensityToEffectMapping> entry = iterator.next();
			result.append(entry.getKey().getName() + "[" + entry.getValue().size() + "]");
			if (iterator.hasNext()) {
				result.append(", ");
			} else {
				result.append(".");
			}
		}
		return getName() + result.toString();
	}

}
