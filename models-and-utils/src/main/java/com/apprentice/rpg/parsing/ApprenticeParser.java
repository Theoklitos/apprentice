package com.apprentice.rpg.parsing;

import java.util.Collection;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.model.playerCharacter.Nameable;
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
	 * Converts the list of nameables into a json array (as a string), based on the type specified
	 * 
	 * @throws ParsingEx
	 */
	<T> JsonArray getAsJsonArrayTest(Collection<? extends Nameable> nameables, Class<T> type) throws ParsingEx;

	/**
	 * Converts the given object to a json object (in string form)
	 */
	String getAsJsonString(Object object);

	/**
	 * Parses the given json string to the specified class. Its usually better to provide a
	 * {@link NameableVault} though.
	 * 
	 * @throws ParsingEx
	 *             if any error occur during parsing
	 */
	<T> T parse(String jsonString, Class<T> classToParseInto) throws ParsingEx;

	/**
	 * Parses the given json string to the specified class, will use objects from the given
	 * {@link NameableVault} if needed.
	 * 
	 * @throws ParsingEx
	 *             if any error occur during parsing
	 */
	<T> T parse(String jsonString, NameableVault simpleVault, Class<T> classToParseInto) throws ParsingEx;

	/**
	 * Tries to parse a collection of the given type from the json
	 */
	<T> Collection<T> parseCollection(String jsonArrayString, final NameableVault vault, final ItemType itemType)
			throws ParsingEx;

}
