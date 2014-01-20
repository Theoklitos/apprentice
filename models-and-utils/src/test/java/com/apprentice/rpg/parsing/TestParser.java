package com.apprentice.rpg.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.PlayerLevels;

public final class TestParser {

	private JsonParser parser;

	@Test
	public void canParsePlayerLevels() {
		final PlayerLevels expected1 = new PlayerLevels();
		expected1.addLevels("monk", 10);
		expected1.addLevels("ninja", 2);
		assertEquals(expected1, parser.parseWithoutExperience("ninja2/monk10"));

		final PlayerLevels expected2 = new PlayerLevels();
		expected2.addLevels("fighter", 1);
		assertEquals(expected2, parser.parseWithoutExperience("FIGHTER1"));

		final PlayerLevels expected3 = new PlayerLevels();
		expected3.addLevels("warrior", 99);
		expected3.addLevels("mage", 5);
		expected3.addLevels("thief", 11);
		assertEquals(expected3, parser.parseWithoutExperience("warrior99/mage5/thief11"));
	}

	@Before
	public void setup() {
		parser = new JsonParser();
	}

	@Test
	public void testEquality() {
		final PlayerLevels pl1 = new PlayerLevels();
		final PlayerLevels pl2 = new PlayerLevels();
		assertEquals(pl1, pl2);

		pl1.addExperiencePoints(666);
		assertNotEquals(pl1, pl2);
	}

	@Test(expected = ParsingEx.class)
	public void throwsPlayerLevelParsingExWhen3DigitLevels() {
		parser.parseWithoutExperience("Wizard1/fighter5/scion102");
	}

	@Test(expected = ParsingEx.class)
	public void throwsPlayerLevelParsingExWhenNoLevelNumber() {
		parser.parseWithoutExperience("Wizard/fighter5");
	}

}
