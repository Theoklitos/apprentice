package com.apprentice.rpg.parsing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.apache.log4j.Logger;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.weapon.Weapon;

public final class TestJsonParser {

	private static Logger LOG = Logger.getLogger(TestJsonParser.class);

	private Mockery mockery;
	private DataFactory factory;
	private JsonParser parser;

	@Test
	public void canParseBodyPart() {
		final BodyPart part = new BodyPart("head");
		final String json = parser.getAsJsonString(part);
		LOG.info("Converted body part to:\n" + json);
		final BodyPart parsed = parser.parseBodyPart(json);
		assertEquals(part, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}
	
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
	
	@Test
	public void canParserPlayerCharacter() {
		final IPlayerCharacter playerCharacter = factory.getPlayerCharacter();
		final String playerAsJson = parser.getAsJsonString(playerCharacter);
		LOG.info("Converted player \"" + playerCharacter + "\" to:\n" + playerAsJson + "\n\n");

		final IPlayerCharacter parsed = parser.parsePlayerCharacter(playerAsJson, factory);
		LOG.info("Got back: " + parsed);

		assertEquals(playerCharacter, parsed);
		LOG.info("Success.");
	}

	@Test
	public void canParseType() {
		final IType human = factory.getTypes().get(0);
		final String json = parser.getAsJsonString(human);
		LOG.info("Converted type to:\n" + json);
		final IType parsed = parser.parseType(json, factory);
		assertEquals(human, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParseWeaponPrototype() {
		final Weapon weaponPrototype = factory.getWeapons().get(0);
		final String json = parser.getAsJsonString(weaponPrototype);
		LOG.info("Converted weapon to:\n" + json);
		//final W parsed = parser.parseWeapon(json);
		//assertEquals(part, parsed);
		//LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Before
	public void setup() {
		factory = new DataFactory();
		mockery = new Mockery();
		parser = new JsonParser();
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

	@Test
	public void testEquality() {
		final PlayerLevels pl1 = new PlayerLevels();
		final PlayerLevels pl2 = new PlayerLevels();
		assertEquals(pl1, pl2);

		pl1.addExperiencePoints(666);
		assertNotSame(pl1, pl2);
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
