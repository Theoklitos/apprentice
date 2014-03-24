package com.apprentice.rpg.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * A list that can be sorted via a {@link Comparator}
 * 
 * @author theoklitos
 * 
 */
public class SortedList<T> extends ArrayList<T> {

	private static final long serialVersionUID = 1L;
	
	private final Comparator<T> comparator;

	public SortedList(final Comparator<T> comparator) {
		this.comparator = comparator;
	}

	@Override
	public boolean add(final T element) {
		final boolean result = super.add(element);
		Collections.sort(this, comparator);
		return remove(result);
	}
}
