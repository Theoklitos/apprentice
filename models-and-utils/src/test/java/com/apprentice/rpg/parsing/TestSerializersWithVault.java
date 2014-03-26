package com.apprentice.rpg.parsing;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link SerializersWithVault}
 * 
 * @author theoklitos
 * 
 */
public final class TestSerializersWithVault {

	private SerializersWithVault swv;

	@Test
	public void numberOfParsers() {
		assertEquals("Number of parsing components changed! Write more tests.", 22, swv.getParsers().size());
	}

	@Before
	public void setup() {
		swv = new SerializersWithVault();
	}
}
