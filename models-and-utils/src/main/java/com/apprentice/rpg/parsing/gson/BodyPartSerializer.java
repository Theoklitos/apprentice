package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.playerCharacter.IPlayerCharacter;
import com.apprentice.rpg.parsing.JsonParser;
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
public final class BodyPartSerializer extends ApprenticeParsingComponent implements JsonSerializer<BodyPart> {

	public BodyPartSerializer() {
		super(BodyPart.class);
	}

	@Override
	public JsonElement serialize(final BodyPart src, final Type typeOfSrc, final JsonSerializationContext context) {
		if (isNameLookupEnabled()) {
			return new JsonPrimitive(src.getName());
		} else {
			return JsonParser.createFreshGson().toJsonTree(src);
		}
	}

}
