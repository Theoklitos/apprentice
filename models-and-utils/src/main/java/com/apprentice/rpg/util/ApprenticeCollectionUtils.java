package com.apprentice.rpg.util;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.apprentice.rpg.model.Nameable;
import com.google.common.base.Objects;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * performs various equality operations for tests
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeCollectionUtils {

	/**
	 * checks if the collections have the same size and all elements equal() exactly one other
	 */
	public static boolean areAllElementsEqual(final Collection<?> expected, final Collection<?> target) {
		if (expected.size() != target.size()) {
			return false;
		}
		final Collection<?> expectedCopy = Lists.newArrayList(expected);
		final Collection<?> targetCopy = Lists.newArrayList(target);
		final Iterator<?> expectedIterator = expectedCopy.iterator();
		while (expectedIterator.hasNext()) {
			final Object expectedObject = expectedIterator.next();
			final Iterator<?> targetIterator = targetCopy.iterator();
			while (targetIterator.hasNext()) {
				final Object targetObject = targetIterator.next();
				if (expectedObject.equals(targetObject)) {
					expectedIterator.remove();
					targetIterator.remove();
				}
			}
		}
		return expectedCopy.size() == 0 && targetCopy.size() == 0;
	}

	/**
	 * checks if the maps have the same size and all elements equal() exactly one other in the key and value
	 * sets. Note: Does not like null values, so take care.
	 */
	public static boolean areAllElementsEqual(final Map<?, ?> map1, final Map<?, ?> map2) {
		if ((map1.keySet().size() != map2.keySet().size()) || (map1.values().size() != map2.values().size())) {
			return false;
		}
		for (final Object key1 : map1.keySet()) {
			boolean keyEquals = false;
			final Object value1 = map1.get(key1);
			Object value2 = null;
			for (final Object key2 : map2.keySet()) {
				if (key1.equals(key2)) {
					value2 = map2.get(key2);
					keyEquals = true;
					break;
				}
			}
			if (keyEquals) {
				if (!Objects.equal(value1, value2)) {
					return false;
				}
			} else {
				return false;
			}
		}
		return true;
	}

	/**
	 * returns a collection with all the intersecting elements from these two collections
	 */
	public static <T> Collection<T> getIntersectingElements(final Collection<T> col1, final Collection<T> col2) {
		final Collection<T> result = Sets.newHashSet();
		for (final T item1 : col1) {
			for (final T item2 : col2) {
				if (item1.equals(item2)) {
					result.add(item1);
				}
			}
		}
		return result;
	}

	/**
	 * returns a collection with all the intersecting elements from these two collections
	 */
	public static Collection<? extends Nameable> getIntersectingNameableElements(
			final Collection<? extends Nameable> col1, final Collection<? extends Nameable> col2) {
		final Collection<Nameable> result = Sets.newHashSet();
		for (final Nameable item1 : col1) {
			for (final Nameable item2 : col2) {
				if (item1.equals(item2)) {
					result.add(item1);
				}
			}
		}
		return result;
	}

	/**
	 * returns a list with all the names of the nameables in the collection
	 */
	public static Collection<String> getNamesOfNameables(final Collection<? extends Nameable> nameables) {
		final List<String> names = Lists.newArrayList();
		for (final Nameable nameable : nameables) {
			names.add(nameable.getName());
		}
		return names;
	}

}
