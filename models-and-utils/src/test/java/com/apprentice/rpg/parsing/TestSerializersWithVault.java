package com.apprentice.rpg.parsing;

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
	public void numberChanged() {
		System.out.println(swv.toString());
	}

	@Before
	public void setup() {
		swv = new SerializersWithVault();
	}
}
