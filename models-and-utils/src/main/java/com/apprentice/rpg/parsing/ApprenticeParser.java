package com.apprentice.rpg.parsing;

import java.util.Collection;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.model.Nameable;
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
	 * parsses the JsonArray string to a list of {@link BodyPart}s
	 */
	Collection<BodyPart> parseBodyParts(String jsonArrayString) throws ParsingEx;

	/**
	 * parsses the JsonArray string to a list of {@link IType}s
	 * 
	 * @param simpleVault
	 *            The types to be created will attempt to get references from the body parts in this vault
	 */
	Collection<IType> parseTypes(String jsonArrayString, final NameableVault simpleVault) throws ParsingEx;

}
