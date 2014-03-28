package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.playerCharacter.StatBundle;
import com.apprentice.rpg.model.playerCharacter.StatBundle.StatType;

/**
 * Tests for the {@link StatBundle}
 * 
 * @author theoklitos
 * 
 */
public final class TestStatBundle {

	private static final int INITIAL_STRENGTH = 10;
	private static final int INITIAL_DEXTERITY = 11;
	private static final int INITIAL_CONSTITUTION = 12;
	private static final int INITIAL_INTELLIGENCE = 13;
	private static final int INITIAL_WISDOM = 14;
	private static final int INITIAL_CHARISMA = 15;

	private StatBundle statBundle;

	@Test
	public void correctInitialization() {
		assertEquals(INITIAL_STRENGTH, statBundle.getStat(StatType.STRENGTH).getValue());
		assertEquals(INITIAL_DEXTERITY, statBundle.getStat(StatType.DEXTERITY).getValue());
		assertEquals(INITIAL_CONSTITUTION, statBundle.getStat(StatType.CONSTITUTION).getValue());
		assertEquals(INITIAL_INTELLIGENCE, statBundle.getStat(StatType.INTELLIGENCE).getValue());
		assertEquals(INITIAL_WISDOM, statBundle.getStat(StatType.WISDOM).getValue());
		assertEquals(INITIAL_CHARISMA, statBundle.getStat(StatType.CHARISMA).getValue());
	}

	@Before
	public void initialize() {
		statBundle =
			new StatBundle(INITIAL_STRENGTH, INITIAL_DEXTERITY, INITIAL_CONSTITUTION, INITIAL_INTELLIGENCE,
					INITIAL_WISDOM, INITIAL_CHARISMA);
	}

	@Test
	public void statModification() {
		statBundle.getStat(StatType.STRENGTH).setValue(1);
		assertEquals(1, statBundle.getStat(StatType.STRENGTH).getValue());
	}
}
