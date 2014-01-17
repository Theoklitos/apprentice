package com.apprentice.rpg.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public final class TestIntegerRange {

	@Test
	public void testCorrectParsing() {
		final String text = "33-67";
		final IntegerRange range = new IntegerRange(text);

		assertEquals(33, range.getMin());
		assertEquals(67, range.getMax());
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

//	@Test
//	public void testMatch() {
//		final IntegerRange range1 = new IntegerRange("10-20");
//		final IntegerRange range2 = new IntegerRange(10, 20);
//
//		assertTrue(range1.match(range2));
//		assertTrue(range1.match(new IntegerRange("10-20")));
//		assertFalse(range1.match(new IntegerRange(10, 21)));
//	}

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

//	@Test(expected = IntegerRangeException.class)
//	public void testWrongInitialization() {
//		new IntegerRange(5, 1);
//	}
//
//	@Test(expected = IntegerRangeException.class)
//	public void testWrongParsing() {
//		new IntegerRange("a33-67");
//	}
//
//	@Test(expected = IntegerRangeException.class)
//	public void testWrongParsing2() {
//		new IntegerRange("33--67");
//	}
}
