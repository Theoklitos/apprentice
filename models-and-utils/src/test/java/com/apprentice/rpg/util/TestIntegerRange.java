package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class TestIntegerRange {

	@Test(expected = IllegalArgumentException.class)
	public void initializationWasWrong() {
		new IntegerRange(20, 4);
	}

	@Test
	public void testCorrectParsing() {
		final String parsableString = "33-67";
		final String text = parsableString;
		final IntegerRange range = new IntegerRange(text);

		assertEquals(33, range.getMin());
		assertEquals(67, range.getMax());
		assertEquals(parsableString, range.toParsingString());
	}

	@Test
	public void testCorrectParsing2() {
		final String text = " 100 -  200 ";
		final IntegerRange range = new IntegerRange(text);

		assertEquals(100, range.getMin());
		assertEquals(200, range.getMax());
	}

	@Test
	public void testIsInRange() {
		final IntegerRange range = new IntegerRange(-1, 10);

		assertTrue(range.contains(4));
		assertTrue(range.contains(-1));
		assertTrue(range.contains(8));

		assertFalse(range.contains(-3));
		assertFalse(range.contains(-8));
		assertFalse(range.contains(11));
	}

	@Test
	public void testSize() {
		IntegerRange range = new IntegerRange(1, 2);
		assertEquals(2, range.size());

		range = new IntegerRange(-1, 10);
		assertEquals(12, range.size());

		range = new IntegerRange(-5, 5);
		assertEquals(11, range.size());

		range = new IntegerRange(0, 15);
		assertEquals(16, range.size());
	}

}
