package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.parsing.JsonParser;
import com.apprentice.rpg.strike.StrikeType;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Deserializes a {@link IPlayerCharacter}
 * 
 * @author theoklitos
 * 
 */
public final class StrikeTypeSerializer extends ApprenticeParsingComponent implements JsonSerializer<StrikeType> {

	public StrikeTypeSerializer() {
		super(StrikeType.class);
	}

	@Override
	public JsonElement serialize(final StrikeType src, final Type typeOfSrc, final JsonSerializationContext context) {
		if (isNameLookupEnabled()) {
			return new JsonPrimitive(src.getName());
		} else {
			return JsonParser.createFreshGson().toJsonTree(src);
		}
	}

}
