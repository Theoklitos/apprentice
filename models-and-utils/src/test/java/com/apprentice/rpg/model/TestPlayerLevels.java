package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.playerCharacter.PlayerLevels;
import com.apprentice.rpg.parsing.ParsingEx;

public final class TestPlayerLevels {

	private PlayerLevels levels;

	@Test
	public void canExtendLevels() {
		// does not exist initially
		assertEquals(0, levels.getLevels("WARRIOR"));

		// now we increment it to 2
		levels.addLevel("Warrior");
		levels.addLevel("Warrior");
		assertEquals(2, levels.getLevels("warrior"));

		// now to 20
		levels.addLevels("WarrioR", 18);
		assertEquals(20, levels.getLevels("WARRior"));
	}

	@Test
	public void canGetAndSetExperienceCorrectly() {
		assertEquals(0, levels.getExperiencePoints());

		levels.addExperiencePoints(100);
		assertEquals(100, levels.getExperiencePoints());

		levels.addExperiencePoints(900);
		levels.addExperiencePoints(1500);
		assertEquals(2500, levels.getExperiencePoints());
	}

	@Test
	public void canParsePlayerLevels() {
		final PlayerLevels expected1 = new PlayerLevels();
		expected1.addLevels("monk", 10);
		expected1.addLevels("ninja", 2);
		assertEquals(expected1, new PlayerLevels("ninja2/monk10"));

		final PlayerLevels expected2 = new PlayerLevels();
		expected2.addLevels("fighter", 1);
		assertEquals(expected2, new PlayerLevels("FIGHTER1"));

		final PlayerLevels expected3 = new PlayerLevels();
		expected3.addLevels("warrior", 99);
		expected3.addLevels("mage", 5);
		expected3.addLevels("thief", 11);
		assertEquals(expected3, new PlayerLevels("warrior99/mage5/thief11"));
	}

	@Test
	public void equality() {
		final PlayerLevels pl1 = new PlayerLevels();
		final PlayerLevels pl2 = new PlayerLevels();
		assertEquals(pl1, pl2);

		pl1.addExperiencePoints(666);
		assertFalse(pl1.equals(pl2));
	}

	@Before
	public void setup() {
		levels = new PlayerLevels();
	}

	@Test(expected = ParsingEx.class)
	public void throwsPlayerLevelParsingExWhen3DigitLevels() {
		new PlayerLevels("Wizard1/fighter5/scion102");
	}

	@Test(expected = ParsingEx.class)
	public void throwsPlayerLevelParsingExWhenNoLevelNumber() {
		new PlayerLevels("Wizard/fighter5");
	}
}
