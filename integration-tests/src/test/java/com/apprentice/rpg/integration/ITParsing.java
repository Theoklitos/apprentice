package com.apprentice.rpg.integration;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.parsing.ApprenticeParser;
import com.apprentice.rpg.parsing.JsonParser;

/**
 * Integration tests featuring the {@link ApprenticeParser}
 * 
 * @author theoklitos
 * 
 */
public final class ITParsing {

	private static Logger LOG = Logger.getLogger(ITParsing.class);

	private JsonParser parser;
	private DataFactory factory;

	@Test
	public void canParserPlayerCharacter() {
		final IPlayerCharacter playerCharacter = factory.getPlayerCharacter();
		final String playerAsJson = parser.getAsJsonString(playerCharacter);
		LOG.info("Parsed player \"" + playerCharacter + "\" to:\n" + playerAsJson + "\n\n");

		final IPlayerCharacter parsed = parser.parsePlayerCharacter(playerAsJson);
		LOG.info("Got back: " + parsed);

		assertEquals(playerCharacter, parsed);
		LOG.info("Success!");
	}

	@Test
	public void canParseTypesAndBodyParts() {
		final List<IType> originalTypes = factory.getTypes();
		final List<BodyPart> originalBodyParts = factory.getBodyParts();
		final String partsAndBodyTypesAsJson = parser.getBodyPartsAndTypesAsJsonString(originalTypes, originalBodyParts);
		LOG.info("Parsed types and body parts to:\n" + partsAndBodyTypesAsJson);
	}

	@Before
	public void initialize() {
		parser = new JsonParser();
		factory = new DataFactory();
	}

}
