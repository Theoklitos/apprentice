package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link CurrentMaximumPair}
 * 
 * @author theoklitos
 * 
 */
public final class TestCurrentMaximumPair {

	private CurrentMaximumPair currentMaximumPair;

	@Test
	public void cannotGoBelowZero() {
		currentMaximumPair.setMaximum(50);
		currentMaximumPair.setCurrent(-20);
		assertEquals(0, currentMaximumPair.getCurrent());
		currentMaximumPair.setMaximum(-100);
		assertEquals(0, currentMaximumPair.getMaximum());
	}

	@Before
	public void initialize() {
		currentMaximumPair = new CurrentMaximumPair(0);
	}

	@Test
	public void initialValueIsCorrect() {
		assertEquals(0, currentMaximumPair.getCurrent());
		assertEquals(0, currentMaximumPair.getMaximum());
	}
}
