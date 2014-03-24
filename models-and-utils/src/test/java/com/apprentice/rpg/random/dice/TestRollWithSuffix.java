package com.apprentice.rpg.random.dice;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * Tests for the {@link RollWithSuffix} class
 * 
 * @author theoklitos
 * 
 */
public class TestRollWithSuffix {

	@Test
	public void parsing() {
		final RollWithSuffix result = new RollWithSuffix(" 2d6+10  (10)  ");
		final RollWithSuffix expected = new RollWithSuffix("2D6+10", 10);
		assertEquals(expected, result);
	}

}
