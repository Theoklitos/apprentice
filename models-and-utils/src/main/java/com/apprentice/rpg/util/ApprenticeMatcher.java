package com.apprentice.rpg.util;

import java.util.Collection;
import java.util.Iterator;

import com.google.common.collect.Lists;

/**
 * performs various equality operations for tests
 * 
 * @author theoklitos
 * 
 */
public final class ApprenticeMatcher {

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

}
