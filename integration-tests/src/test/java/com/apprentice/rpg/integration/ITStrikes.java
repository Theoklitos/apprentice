package com.apprentice.rpg.integration;

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.factories.DataFactory;

/**
 * Tests strikes that are applied to players and then removed (healed)
 * 
 * @author theoklitos
 * 
 */
public class ITStrikes {

	private DataFactory factory;
	@SuppressWarnings("unused")
	private IPlayerCharacter pc;

	@Test
	public void applyRemoveSomeStrikes() {
		fail("WIP");
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		pc = factory.getPlayerCharacter1();
	}
}
