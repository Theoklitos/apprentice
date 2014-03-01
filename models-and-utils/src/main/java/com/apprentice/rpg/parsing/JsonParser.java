package com.apprentice.rpg.parsing;

import java.lang.reflect.Type;
import java.util.Collection;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.gson.BonusSequenceDeserializer;
import com.apprentice.rpg.parsing.gson.BonusSequenceSerializer;
import com.apprentice.rpg.parsing.gson.RangeDeserializer;
import com.apprentice.rpg.parsing.gson.RangeSerializer;
import com.apprentice.rpg.parsing.gson.RollDeserializer;
import com.apprentice.rpg.parsing.gson.RollSerializer;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.strike.StrikeType;
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
		gsonBuilder.registerTypeAdapter(Roll.class, new RangeSerializer());
		gsonBuilder.registerTypeAdapter(Roll.class, new RangeDeserializer());
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
	public <T> JsonArray getAsJsonArrayTest(final Collection<? extends Nameable> nameables, final Class<T> type)
			throws ParsingEx {
		try {
			serializersWithVault.setNameLookupForType(type, true, false);
			final java.lang.reflect.Type typeType = new TypeToken<Collection<T>>() {
			}.getType();
			final JsonArray result = gson.toJsonTree(nameables, typeType).getAsJsonArray();
			serializersWithVault.setNameLookupForType(type, true, true);
			return result;
		} catch (final Exception e) {
			throw new ParsingEx(e);
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
	 * due to type erasure and the way gson is, we have to map {@link ItemType} to specific {@link Type}s here
	 */
	private Type getCollectionTypeForType(final ItemType itemType) {
		switch (itemType) {
		case BODY_PART:
			return new TypeToken<Collection<BodyPart>>() {
			}.getType();
		case TYPE:
			return new TypeToken<Collection<IType>>() {
			}.getType();
		case STRIKE_TYPE:
			return new TypeToken<Collection<StrikeType>>() {
			}.getType();
		case ARMOR_PIECE:
			return new TypeToken<Collection<IArmorPiece>>() {
			}.getType();
		case WEAPON:
			return new TypeToken<Collection<WeaponPrototype>>() {
			}.getType();
		case PLAYER_CHARACTER:
			return new TypeToken<Collection<PlayerCharacter>>() {
			}.getType();
		default:
			throw new ApprenticeEx("Collection TokenType not implemented for type: " + itemType);
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
	public <T> Collection<T> parseCollection(final String jsonArrayString, final NameableVault vault,
			final ItemType itemType) throws ParsingEx {
		try {
			serializersWithVault.setSimpleVault(vault);
			serializersWithVault.setNameLookupForType(itemType.type, false, false);
			final java.lang.reflect.Type collectionType = getCollectionTypeForType(itemType);
			final Collection<T> result = gson.fromJson(jsonArrayString, collectionType);
			serializersWithVault.setNameLookupForType(itemType.type, false, true);
			return result;
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
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

}
