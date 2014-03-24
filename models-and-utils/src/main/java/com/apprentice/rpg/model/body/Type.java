package com.apprentice.rpg.model.body;

import java.util.List;
import java.util.SortedMap;

import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.Checker;
import com.apprentice.rpg.util.IntegerRange;
import com.google.common.base.Objects;

/**
 * All beings (PCs and NPCs) comprise of several body parts. Many body parts create a type.
 * 
 * @author theoklitos
 * 
 */
public final class Type extends BaseApprenticeObject implements IType {

	private BodyPartToRangeMapping parts;

	/**
	 * @throws BodyPartMappingEx
	 *             if the {@link BodyPartToRangeMapping} is not consecutively mapped from 1 to 100
	 */
	public Type(final String name, final BodyPartToRangeMapping parts) {
		super(name);
		Checker.checkNonNull("Type initialized null parts", true, parts);
		setBodyPartMapping(parts);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Type) {
			final Type type = (Type) other;
			return super.equals(other) && Objects.equal(getBodyParts(), type.getBodyParts());
		} else {
			return false;
		}
	}

	@Override
	public List<BodyPart> getBodyParts() {
		return parts.getParts();
	}

	@Override
	public int getMaxRangeValue() {
		return parts.getMaxRangeValue();
	}

	@Override
	public BodyPart getPartForNumber(final int number) throws BodyPartMappingEx {
		final List<BodyPart> parts = this.parts.getPartsForNumber(number);
		if (parts.size() == 1) {
			return parts.get(0);
		} else {
			// this cannot happen, but just in case...
			throw new BodyPartMappingEx("Type \"" + getName() + "\" had either 0 or >1 mappings for number " + number
				+ "! Check your type verification code.");
		}
	}

	@Override
	public BodyPartToRangeMapping getPartMapping() {
		return new BodyPartToRangeMapping(parts);
	}

	@Override
	public Box<IntegerRange> getRangeForPartName(final String bodyPartName) {
		final BodyPart equivalentPart = new BodyPart(bodyPartName);
		final SortedMap<IntegerRange, BodyPart> mapping = parts.getInternalMapping();
		if (mapping.values().contains(equivalentPart)) {
			for (final IntegerRange range : mapping.keySet()) {
				if (mapping.get(range).equals(equivalentPart)) {
					return Box.with(range);
				}
			}
		}
		return Box.empty();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(getBodyParts());
	}

	@Override
	public void setBodyPartMapping(final BodyPartToRangeMapping mapping) throws BodyPartMappingEx {
		verifyMapping(mapping);
		this.parts = mapping;
	}

	@Override
	public String toString() {
		return getName() + ", has bodyparts: " + parts.toString();
	}

	/**
	 * verifies that parts have consecutive ranges from 1 to 100
	 * 
	 * @throw throws BodyPartMappingEx
	 */
	private void verifyMapping(final BodyPartToRangeMapping mapping) throws BodyPartMappingEx {
		if (mapping.getInternalMapping().size() == 0) {
			throw new BodyPartMappingEx("Body Part mapping is empty!");
		}
		if (mapping.getInternalMapping().firstKey().getMin() != 1) {
			throw new BodyPartMappingEx("Body Part mapping must start from 1");
		}
		final int maxRangeValue = mapping.getMaxRangeValue();
		if (mapping.getInternalMapping().lastKey().getMax() != maxRangeValue) {
			throw new BodyPartMappingEx("Body Part mapping must end at " + maxRangeValue);
		}
		for (int i = 1; i <= maxRangeValue; i++) {
			if (mapping.getPartsForNumber(i).isEmpty()) {
				throw new BodyPartMappingEx("Body Part has no mapping for number " + i);
			}
		}
	}

}
