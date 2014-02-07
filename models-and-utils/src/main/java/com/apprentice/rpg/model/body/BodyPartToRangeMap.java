package com.apprentice.rpg.model.body;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedMap;

import com.apprentice.rpg.util.Box;
import com.apprentice.rpg.util.IntegerRange;
import com.google.common.base.Joiner;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * Wraps around a {@link SortedMap} to map a list of {@link BodyPart} to various {@link IntegerRange}s.
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
	 * copy constructor
	 */
	public BodyPartToRangeMap(final BodyPartToRangeMap other) {
		this(Maps.newTreeMap(other.partMapping));
	}

	protected BodyPartToRangeMap(final SortedMap<IntegerRange, BodyPart> partMapping) {
		this.partMapping = partMapping;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof BodyPartToRangeMap) {
			final BodyPartToRangeMap otherWindowState = (BodyPartToRangeMap) other;
			return Objects.equal(partMapping, otherWindowState.partMapping);
		} else {
			return false;
		}
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
	 * returns the range that maps to this body part. Emtpy box if nothing maps.
	 */
	public Box<IntegerRange> getRangeForBodyPart(final BodyPart bodyPart) {
		for (final IntegerRange range : partMapping.keySet()) {
			if (partMapping.get(range).equals(bodyPart)) {
				return Box.with(range);
			}
		}
		return Box.empty();
	}

	/**
	 * returns an ordered list of entries that contain all the range->part mappings
	 */
	public List<Entry<IntegerRange, BodyPart>> getSequentialMapping() {
		final Iterator<Entry<IntegerRange, BodyPart>> iterator = partMapping.entrySet().iterator();
		final List<Entry<IntegerRange, BodyPart>> result = Lists.newArrayList();
		while (iterator.hasNext()) {
			result.add(iterator.next());
		}
		return result;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(partMapping);
	}

	/**
	 * is there a {@link BodyPart} mapped for exactly this integer range?
	 */
	public boolean hasMapping(final int min, final int max) {
		return partMapping.containsKey(new IntegerRange(min, max));
	}

	/**
	 * is there a {@link BodyPart} mapped for exactly this integer range?
	 */
	public boolean hasMapping(final IntegerRange range) {
		return partMapping.containsKey(range);
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
		final Iterator<Entry<IntegerRange, BodyPart>> iterator = partMapping.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<IntegerRange, BodyPart> entry = iterator.next();
			if (entry.getValue().equals(part)) {
				// already exists, replace
				iterator.remove();
			}
		}
		partMapping.put(range, part);
	}

	@Override
	public String toString() {
		return Joiner.on(", ").withKeyValueSeparator(":").join(partMapping);
	}

}
