package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.parsing.JsonParser;
import com.apprentice.rpg.strike.StrikeType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class StrikeTypeDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<StrikeType> {

	public StrikeTypeDeserializer() {
		super(StrikeType.class);
	}

	@Override
	public StrikeType deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		if (isNameLookupEnabled()) {			
			return getNameableVault().getUniqueNamedResult(json.getAsString(), StrikeType.class);
		} else {
			return JsonParser.createFreshGson().fromJson(json, StrikeType.class);
		}
	}

}
