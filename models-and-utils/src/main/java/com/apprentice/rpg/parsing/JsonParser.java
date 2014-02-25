package com.apprentice.rpg.parsing;

import java.util.Collection;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.gson.BonusSequenceDeserializer;
import com.apprentice.rpg.parsing.gson.BonusSequenceSerializer;
import com.apprentice.rpg.parsing.gson.RollDeserializer;
import com.apprentice.rpg.parsing.gson.RollSerializer;
import com.apprentice.rpg.random.dice.Roll;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;

/**
 * Is responsible for parsing/converting all classes to/from JSON
 * 
 * @author theoklitos
 * 
 */
public final class JsonParser implements ApprenticeParser {

	/**
	 * creates a {@link Gson} instance
	 */
	public static Gson createFreshGson() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Roll.class, new RollSerializer());
		gsonBuilder.registerTypeAdapter(Roll.class, new RollDeserializer());
		gsonBuilder.registerTypeAdapter(Roll.class, new BonusSequenceSerializer());
		gsonBuilder.registerTypeAdapter(Roll.class, new BonusSequenceDeserializer());
		return gsonBuilder.setPrettyPrinting().create();
	}

	private final Gson gson;
	private final SerializersWithVault serializersWithVault;

	@Inject
	public JsonParser() {
		serializersWithVault = new SerializersWithVault();
		gson = serializersWithVault.getGson();
	}

	@Override
	public JsonArray getAsJsonArray(final Collection<? extends Nameable> nameables, final ItemType type)
			throws ParsingEx {
		switch (type) {
		case TYPE:
			return getTypesAsJsonArray(nameables);
		case BODY_PART:
			return getBodyPartsAsJsonArray(nameables);
		default:
			throw new ApprenticeEx("Unimplemented item type \"" + type + "\"");
		}
	}

	@Override
	public String getAsJsonString(final Object object) {
		serializersWithVault.setNameLookupForType(object.getClass(), true, false);
		final String result = gson.toJson(object);
		serializersWithVault.setNameLookupForType(object.getClass(), true, true);
		return result;
	}

	/**
	 * returns this collection of types as a {@link JsonArray}
	 * 
	 * @throws ParsingEx
	 */
	private JsonArray getBodyPartsAsJsonArray(final Collection<? extends Nameable> bodyParts) throws ParsingEx {
		try {
			final java.lang.reflect.Type type = new TypeToken<Collection<BodyPart>>() {
			}.getType();
			return gson.toJsonTree(bodyParts, type).getAsJsonArray();
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	/**
	 * returns this collection of types as a {@link JsonArray}
	 * 
	 * @throws ParsingEx
	 */
	private JsonArray getTypesAsJsonArray(final Collection<? extends Nameable> types) throws ParsingEx {
		try {
			final java.lang.reflect.Type typeType = new TypeToken<Collection<Type>>() {
			}.getType();
			return gson.toJsonTree(types, typeType).getAsJsonArray();
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public <T> T parse(final String jsonString, final Class<T> classToParseInto) throws ParsingEx {
		return parse(jsonString, null, classToParseInto);
	}

	@Override
	public <T> T parse(final String jsonString, final NameableVault simpleVault, final Class<T> classToParseInto)
			throws ParsingEx {
		try {
			serializersWithVault.setSimpleVault(simpleVault);
			serializersWithVault.setNameLookupForType(classToParseInto, false, false);
			final T result = gson.fromJson(jsonString, classToParseInto);
			serializersWithVault.setNameLookupForType(classToParseInto, false, true);
			return result;
		} catch (final JsonSyntaxException e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public Collection<BodyPart> parseBodyParts(final String jsonArrayString) throws ParsingEx {
		final java.lang.reflect.Type bodyPartType = new TypeToken<Collection<BodyPart>>() {
		}.getType();
		return gson.fromJson(jsonArrayString, bodyPartType);
	}

	/**
	 * Attempts to parse a json object (in string form) to a fully fledged {@link IPlayerCharacter}
	 * 
	 * @throws ParsingEx
	 */
	public IPlayerCharacter parsePlayerCharacter(final String playerAsJson, final NameableVault simpleVault)
			throws ParsingEx {
		try {
			serializersWithVault.setSimpleVault(simpleVault);
			return gson.fromJson(playerAsJson, PlayerCharacter.class);
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public Collection<IType> parseTypes(final String jsonArrayString, final NameableVault simpleVault) throws ParsingEx {
		final java.lang.reflect.Type typeType = new TypeToken<Collection<IType>>() {
		}.getType();
		serializersWithVault.setSimpleVault(simpleVault);
		return gson.fromJson(jsonArrayString, typeType);
	}

}
