package com.apprentice.rpg.parsing;

import java.util.Collection;

import com.apprentice.rpg.dao.NameableVault;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.google.gson.JsonArray;

/**
 * Converts and parses classes to other types of formats
 * 
 * @author theoklitos
 * 
 */
public interface ApprenticeParser {

	/**
	 * Converts the list of nameables into a json array (as a string), based on the {@link ItemType} specified
	 * 
	 * @throws ParsingEx
	 */
	JsonArray getAsJsonArray(Collection<? extends Nameable> nameables, ItemType type) throws ParsingEx;

	/**
	 * Converts the given {@link BodyPart} to a json object
	 */
	String getAsJsonString(BodyPart bodyPart);

	/**
	 * Converts the given {@link IPlayerCharacter} to a json object
	 */
	String getAsJsonString(IPlayerCharacter playerCharacter);

	/**
	 * Converts the given {@link IType} to a json object (as a string)
	 * 
	 * @throws ParsingEx
	 *             if the implementation of {@link IType} is not supported
	 */
	String getAsJsonString(IType type) throws ParsingEx;

	/**
	 * Tries to parse the given json string to a {@link BodyPart}
	 * 
	 * @throws ParsingEx
	 */
	BodyPart parseBodyPart(String json) throws ParsingEx;

	/**
	 * parsses the JsonArray string to a list of {@link BodyPart}s
	 */
	Collection<BodyPart> parseBodyParts(String jsonArrayString) throws ParsingEx;

	/**
	 * Tries to parse the given json string to a {@link IType}
	 * 
	 * @throws ParsingEx
	 */
	IType parseType(String json) throws ParsingEx;

	/**
	 * parsses the JsonArray string to a list of {@link IType}s
	 * 
	 * @param simpleVault
	 *            The types to be created will attempt to get references from the body parts in this vault
	 */
	Collection<IType> parseTypes(String jsonArrayString, final NameableVault simpleVault) throws ParsingEx;

	/**
	 * parses a "warrior1/fighter2" text to a {@link PlayerLevels} object. Does not parse experience.
	 * 
	 * @throws ParsingEx
	 */
	PlayerLevels parseWithoutExperience(String text) throws ParsingEx;

}
