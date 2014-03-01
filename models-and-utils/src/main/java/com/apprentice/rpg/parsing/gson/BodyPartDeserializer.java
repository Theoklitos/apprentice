package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.parsing.JsonParser;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class BodyPartDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<BodyPart> {

	public BodyPartDeserializer() {
		super(BodyPart.class);
	}

	@Override
	public BodyPart deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {		
		if (isNameLookupEnabled()) {			
			return getNameableVault().getUniqueNamedResult(json.getAsString(), BodyPart.class);
		} else {
			return JsonParser.createFreshGson().fromJson(json, BodyPart.class);
		}
	}

}
