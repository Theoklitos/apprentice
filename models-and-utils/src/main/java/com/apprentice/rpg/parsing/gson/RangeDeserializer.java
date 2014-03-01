package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.weapon.Range;
import com.apprentice.rpg.random.dice.Roll;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserializes a {@link Roll}
 * 
 * @author theoklitos
 * 
 */
public final class RangeDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<Range> {

	public RangeDeserializer() {
		super(Range.class);
	}

	@Override
	public Range deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		return new Range(json.getAsString());
	}

}
