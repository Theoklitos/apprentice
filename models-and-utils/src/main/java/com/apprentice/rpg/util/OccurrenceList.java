package com.apprentice.rpg.util;

import java.util.List;

import com.apprentice.rpg.ApprenticeEx;
import com.google.common.collect.Lists;

/**
 * A List that stores the occurrences of the given element in a connected list.
 */
public final class OccurrenceList<E> {

	private List<E> content;
	private List<Integer> occurrences;

	public OccurrenceList() {
		content = Lists.newArrayList();
		occurrences = Lists.newArrayList();
	}

	/**
	 * Copy constructor
	 */
	public OccurrenceList(final OccurrenceList<E> other) {
		content = Lists.newArrayList(other.content);
		occurrences = Lists.newArrayList(other.occurrences);
	}

	public void add(final E element) {
		if (content.contains(element)) {
			final int index = content.indexOf(element);
			occurrences.set(index, occurrences.get(index) + 1);
		} else {
			content.add(element);
			occurrences.add(1);
		}
	}

	/**
	 * @throws ApprenticeEx
	 */
	public void add(final int index, final E element) {
		try {
			content.add(index, element);
			occurrences.add(index, 1);
		} catch (final IndexOutOfBoundsException e) {
			throw new ApprenticeEx("Error while adding dice to a roll. " + e.getMessage());
		}
		recalibrate();
	}

	public void clear() {
		content.clear();
		occurrences.clear();
	}

	public E get(final int index) {
		return content.get(index);
	}

	/**
	 * Returns all elements multiplied by the number of their occurrences, as a list
	 */
	public List<E> getAllElements() {
		final List<E> result = Lists.newArrayList();
		for (final E element : content) {
			result.addAll(getAllOccurrencesOfElement(element));
		}
		return result;
	}

	/**
	 * Returns all the occurrences of the given elements as a list Returns empty list of the element
	 * does not exist
	 */
	public List<E> getAllOccurrencesOfElement(final E element) {
		final List<E> result = Lists.newArrayList();
		if (!content.contains(element)) {
			return result;
		}
		for (int i = 0; i < getOccurencesOf(element); i++) {
			result.add(element);
		}
		return result;
	}

	protected List<E> getContent() {
		return content;
	}

	protected List<Integer> getOccurences() {
		return occurrences;
	}

	/**
	 * @throws ApprenticeEx
	 *             if element does not exist, beware!
	 */
	public int getOccurencesOf(final E element) {
		int index;
		try {
			index = content.indexOf(element);
		} catch (final IndexOutOfBoundsException e) {
			throw new ApprenticeEx(e);
		}
		return occurrences.get(index);
	}

	/**
	 * Uses 'equals' on internal objects. Beware.
	 */
	@SuppressWarnings("unchecked")
	public boolean match(final Object o) {
		if (o instanceof OccurrenceList) {
			final OccurrenceList<E> other = (OccurrenceList<E>) o;
			if (this.size() != other.size() || this.totalSize() != other.totalSize()) {
				return false;
			}
			for (int i = 0; i < size(); i++) {
				final E thisElement = get(i);
				final E otherElement = other.get(i);
				if (!(thisElement.equals(otherElement) && getOccurencesOf(thisElement) == other
						.getOccurencesOf(otherElement))) {
					return false;
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Goes through all the elements and re-sets the occurrences numbers.
	 */
	protected void recalibrate() {
		final OccurrenceList<E> newList = new OccurrenceList<E>();
		for (int i = 0; i < content.size(); i++) {
			final E currentElement = content.get(i);
			for (int z = 0; z < getOccurencesOf(currentElement); z++) {
				newList.add(currentElement);
			}
			content.set(i, null);
			occurrences.set(i, null);
		}
		this.content = Lists.newArrayList(newList.getContent());
		this.occurrences = Lists.newArrayList(newList.getOccurences());
	}

	/**
	 * Removes the given element and returns its index. Returns -1 if the element did not exist.
	 */
	public int remove(final E toRemove) {
		for (int i = 0; i < content.size(); i++) {
			if (content.get(i).equals(toRemove)) {
				content.remove(i);
				occurrences.remove(i);
				return i;
			}
		}
		return -1;
	}

	/**
	 * Removes one occurrence of the given element. If it has only one, it is removed completely,
	 */
	public int removeOnce(final E toDecrease) {
		for (int i = 0; i < content.size(); i++) {
			if (content.get(i).equals(toDecrease)) {
				final int noOccurrences = getOccurencesOf(toDecrease);
				if (noOccurrences == 1) {
					content.remove(i);
					occurrences.remove(i);
				} else {
					occurrences.set(i, noOccurrences - 1);
				}
				return i;
			}
		}
		return -1;
	}

	public void set(final int index, final E element) {
		content.set(index, element);
		recalibrate();
	}

	/**
	 * Returns the number of different elements, regardless of the number of times they occur
	 */
	public int size() {
		return content.size();
	}

	@Override
	public String toString() {
		final StringBuffer result = new StringBuffer();
		for (int i = 0; i < content.size(); i++) {
			final E element = content.get(i);
			result.append(element + ": " + occurrences.get(i) + " times");
			if (i != content.size()) {
				result.append("\n");
			}
		}
		return result.toString();
	}

	/**
	 * Returns the number of all the elements multiplied by the number of their occurrences
	 */
	public int totalSize() {
		return getAllElements().size();
	}

}
