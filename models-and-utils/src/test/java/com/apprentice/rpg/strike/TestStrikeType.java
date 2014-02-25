package com.apprentice.rpg.strike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * tests for the {@link StrikeType}
 * 
 * @author theoklitos
 * 
 */
public final class TestStrikeType {

	private StrikeType type;

	@Test
	public void equality() {
		assertEquals(type, new StrikeType("Fire"));
		type.setDescription("new description");
		assertFalse(type.equals(new StrikeType("fire")));
		assertFalse(type.equals(new StrikeType("ice")));
	}

	@Before
	public void setup() {
		type = new StrikeType("fire");
	}
}
