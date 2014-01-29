package com.apprentice.rpg.model.body;

import java.util.List;
import java.util.SortedMap;

import com.apprentice.rpg.util.IntegerRange;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Wraps around a {@link SortedMap} to map a list of {@link BodyPart} to their consecutive
 * {@link IntegerRange}, from 1 to 100
 * 
 * @author theoklitos
 * 
 */
public final class BodyPartToRangeMap {

	private final SortedMap<IntegerRange, BodyPart> partMapping;

	public BodyPartToRangeMap() {
		partMapping = Maps.newTreeMap();
	}

	/**
	 * returns the internal, modifiable map
	 */
	protected SortedMap<IntegerRange, BodyPart> getInternalMapping() {
		return partMapping;
	}

	/**
	 * returns a copy of the {@link BodyPart} values of this mapping
	 */
	public List<BodyPart> getParts() {
		return Lists.newArrayList(partMapping.values());
	}

	/**
	 * Returns a list of body part(s) that are mapped for this number
	 * 
	 * throws {@link BodyPartMappingEx} if a number outside 1-100 is given
	 */
	public List<BodyPart> getPartsForNumber(final int number) throws BodyPartMappingEx {
		if (number < 1 || number > 100) {
			throw new BodyPartMappingEx("Body Parts are mapped only from 1 to 100, and a \"" + number
				+ "\" was requested!");
		}
		final List<BodyPart> result = Lists.newArrayList();
		for (final IntegerRange range : partMapping.keySet()) {
			if (range.contains(number)) {
				if (partMapping.get(range) != null) {
					result.add(partMapping.get(range));
				}
			}
		}
		return result;
	}

	/**
	 * returns true if such a part existed inside this {@link BodyPartToRangeMap} and was succesfully removed
	 */
	public boolean removePart(final BodyPart part) {
		for (final IntegerRange range : partMapping.keySet()) {
			if (partMapping.get(range).equals(part)) {
				return partMapping.remove(range) != null;
			}
		}
		return false;
	}

	/**
	 * defines the range for the given part. All parts should have consecutive ranges from 1 to 100.
	 */
	public void setPartForRange(final int start, final int end, final BodyPart part) {
		setPartForRange(new IntegerRange(start, end), part);
	}

	/**
	 * defines the {@link IntegerRange} for the given part. All parts should have consecutive ranges from 1 to
	 * 100.
	 */
	public void setPartForRange(final IntegerRange range, final BodyPart part) {
		for (final IntegerRange existingRange : partMapping.keySet()) {
			if (partMapping.get(existingRange).equals(part)) {
				// already exists, replace
				partMapping.remove(existingRange);
			}
		}
		partMapping.put(range, part);
	}

	@Override
	public String toString() {
		return Joiner.on(", ").withKeyValueSeparator(":").join(partMapping);
	}


}
