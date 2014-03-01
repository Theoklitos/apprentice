package com.apprentice.rpg.model.weapon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * tests for the {@link Range}
 * 
 * @author theoklitos
 * 
 */
public final class TestRange {

	@Test
	public void initialization() {
		final Range range = new Range(20, 100, 500);
		assertEquals(20, range.getShortRange());
		assertEquals(100, range.getMediumRange());
		assertEquals(500, range.getLongRange());
	}

	@Test
	public void initializationFromText() {
		final Range range = new Range("5/10/15");
		assertEquals(5, range.getShortRange());
		assertEquals(10, range.getMediumRange());
		assertEquals(15, range.getLongRange());
	}

	@Test(expected = ApprenticeEx.class)
	public void initializeWrongRanges() {
		new Range(20, 10, 100);
	}

	@Test(expected = ParsingEx.class)
	public void initializeWrongText() {
		new Range("10/20.30");
	}

	@Test
	public void setRanges() {
		final Range range = new Range(1, 2, 3);
		range.setLongRange(100);
		assertEquals(100, range.getLongRange());
		range.setMediumRange(99);
		assertEquals(99, range.getMediumRange());
		range.setShortRange(10);
		assertEquals(10, range.getShortRange());
	}
	
	@Test(expected = ApprenticeEx.class)
	public void setWrongLongRange() {
		final Range range = new Range(20, 100, 500);
		range.setLongRange(100);
	}

	@Test(expected = ApprenticeEx.class)
	public void setWrongMediumRange() {
		final Range range = new Range(10, 20, 30);
		range.setMediumRange(5);
	}

	@Test(expected = ApprenticeEx.class)
	public void setWrongShortRange() {
		final Range range = new Range(10, 20, 30);
		range.setShortRange(21);
	}
}
