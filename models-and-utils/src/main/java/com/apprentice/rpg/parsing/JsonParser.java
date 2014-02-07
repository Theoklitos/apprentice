package com.apprentice.rpg.parsing;

import java.util.Collection;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.dao.NameableVault;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.apprentice.rpg.parsing.gson.TypeDeserializer;
import com.apprentice.rpg.parsing.gson.TypeSerializer;
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

	private final Gson gson;	
	private final GsonBuilder gsonBuilder;
	
	private  final TypeDeserializer typeDeserializer;
		
	@Inject
	public JsonParser() {		
		typeDeserializer = new TypeDeserializer();		
		gsonBuilder = new GsonBuilder();		
		gsonBuilder.registerTypeAdapter(Type.class, new TypeSerializer());
		gsonBuilder.registerTypeAdapter(IType.class, typeDeserializer);
		gson = gsonBuilder.setPrettyPrinting().create();
	}

	private void emptynessCheck(final String text) {
		if (StringUtils.isEmpty(text)) {
			throw new ParsingEx("Nothing to parse!");
		}
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
	public String getAsJsonString(final BodyPart bodyPart) {
		return gson.toJson(bodyPart);
	}

	@Override
	public String getAsJsonString(final IPlayerCharacter playerCharacter) {
		return "gay"; // TODO
	}

	@Override
	public String getAsJsonString(final IType type) throws ParsingEx {
		if (!type.getClass().isAssignableFrom(Type.class)) {
			throw new ParsingEx("Cannot convert " + type.getClass().getSimpleName() + " to json!");
		}
		final Type typeImpl = (Type) type;
		return gson.toJson(typeImpl);
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
	public BodyPart parseBodyPart(final String json) throws ParsingEx {
		try {
			return gson.fromJson(json, BodyPart.class);
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
	public IPlayerCharacter parsePlayerCharacter(final String playerAsJson) throws ParsingEx {
		try {
			return null; // TODO
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public IType parseType(final String json) throws ParsingEx {
		try {
			return gson.fromJson(json, IType.class);
		} catch (final JsonSyntaxException e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public Collection<IType> parseTypes(final String jsonArrayString, final NameableVault simpleVault) throws ParsingEx {
		final java.lang.reflect.Type typeType = new TypeToken<Collection<IType>>() {
		}.getType();
		typeDeserializer.setNameableVault(simpleVault);
		return gson.fromJson(jsonArrayString, typeType);
	}

	@Override
	public PlayerLevels parseWithoutExperience(final String text) throws ParsingEx {
		emptynessCheck(text);
		final String rawText = text.toLowerCase().trim();
		final PlayerLevels result = new PlayerLevels();
		final StringTokenizer tokenizer = new StringTokenizer(rawText, "/");
		try {
			while (tokenizer.hasMoreElements()) {
				String className;
				int level;
				final String withLevels = tokenizer.nextElement().toString();
				if (withLevels.length() > 3 && StringUtils.isNumeric(withLevels.substring(withLevels.length() - 3))) {
					// level too big
					throw new ParsingEx("Levels up to 99 supported only.");
				} else if (withLevels.length() > 2
					&& StringUtils.isNumeric(withLevels.substring(withLevels.length() - 2))) {
					// 2 digit levels
					className = withLevels.substring(0, withLevels.length() - 2);
					level = Integer.valueOf(withLevels.substring(withLevels.length() - 2));
				} else if (withLevels.length() > 1
					&& StringUtils.isNumeric(withLevels.substring(withLevels.length() - 1))) {
					// 1 digit
					className = withLevels.substring(0, withLevels.length() - 1);
					level = Integer.valueOf(withLevels.substring(withLevels.length() - 1));
				} else {
					// no level
					throw new ParsingEx("No level was given for class \"" + withLevels + "\"");
				}
				result.addLevels(className, level);
			}

		} catch (final NumberFormatException e) {
			throw new ParsingEx("Could not understand level number: " + e.getMessage());
		}
		return result;
	}

}
