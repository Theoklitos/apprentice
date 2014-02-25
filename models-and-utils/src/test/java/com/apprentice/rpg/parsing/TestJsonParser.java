package com.apprentice.rpg.parsing;

import static org.junit.Assert.assertEquals;

import org.apache.log4j.Logger;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.ArmorPieceInstance;
import com.apprentice.rpg.model.armor.IArmorPieceInstance;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.weapon.IWeaponInstance;
import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.model.weapon.WeaponInstance;
import com.apprentice.rpg.strike.StrikeType;

public final class TestJsonParser {

	private static Logger LOG = Logger.getLogger(TestJsonParser.class);

	private Mockery mockery;
	private DataFactory factory;
	private JsonParser parser;

	@Test
	public void canParseArmorPiece() {
		final ArmorPiece piece = factory.getArmorPieces().get(0);
		final String json = parser.getAsJsonString(piece);
		LOG.info("Converted armor piece to:\n" + json);
		final ArmorPiece parsed = parser.parse(json, ArmorPiece.class);
		assertEquals(piece, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParseArmorPieceInstance() {
		final IArmorPieceInstance piece = new ArmorPieceInstance(factory.getArmorPieces().get(0));
		final String json = parser.getAsJsonString(piece);
		LOG.info("Converted armor piece instance to:\n" + json);
		final IArmorPieceInstance parsed = parser.parse(json, factory, IArmorPieceInstance.class);
		assertEquals(piece, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParseBodyPart() {
		final BodyPart part = new BodyPart("head");
		part.setDescription("We all know what a head is.");
		final String json = parser.getAsJsonString(part);
		LOG.info("Converted body part to:\n" + json);
		final BodyPart parsed = parser.parse(json, BodyPart.class);
		assertEquals(part, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParsePlayerCharacter() {
		final IPlayerCharacter playerCharacter = factory.getPlayerCharacter();
		final String playerAsJson = parser.getAsJsonString(playerCharacter);
		LOG.info("Converted player \"" + playerCharacter + "\" to:\n" + playerAsJson + "\n\n");

		final IPlayerCharacter parsed = parser.parse(playerAsJson, factory, IPlayerCharacter.class);
		LOG.info("Got back: " + parsed);

		assertEquals(playerCharacter, parsed);
		LOG.info("Success.");
	}

	@Test
	public void canParseStrike() {
		final StrikeType slashing = factory.getStrikeTypes().get(1);
		final String json = parser.getAsJsonString(slashing);
		LOG.info("Converted slashing type to:\n" + json);
		final StrikeType parsed = parser.parse(json, StrikeType.class);
		assertEquals(slashing, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParseType() {
		final IType human = factory.getTypes().get(0);
		final String json = parser.getAsJsonString(human);
		LOG.info("Converted type to:\n" + json);
		final IType parsed = parser.parse(json, factory, IType.class);
		assertEquals(human, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParseWeaponInstance() {
		final IWeaponInstance weaponInstance = new WeaponInstance(factory.getWeapons().get(1));
		weaponInstance.removeHitPoints(10);
		final String json = parser.getAsJsonString(weaponInstance);
		LOG.info("Converted weapon instance to:\n" + json);
		final IWeaponInstance parsed = parser.parse(json, factory, IWeaponInstance.class);
		assertEquals(weaponInstance, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
	}

	@Test
	public void canParseWeaponPrototype() {
		final Weapon weaponPrototype = factory.getWeapons().get(1);
		final String json = parser.getAsJsonString(weaponPrototype);
		LOG.info("Converted weapon prototype to:\n" + json);
		final Weapon parsed = parser.parse(json, factory, Weapon.class);
		assertEquals(weaponPrototype, parsed);
		LOG.info("Parsed back: " + parsed + ". Success.");
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

}
