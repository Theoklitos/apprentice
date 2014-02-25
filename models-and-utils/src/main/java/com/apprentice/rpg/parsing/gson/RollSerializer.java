package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.random.dice.Roll;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * serializes {@link Roll}s
 * 
 * @author theoklitos
 * 
 */
public class RollSerializer extends ApprenticeParsingComponent implements JsonSerializer<Roll> {

	public RollSerializer() {
		super(Roll.class);
	}

	@Override
	public JsonElement serialize(final Roll src, final Type typeOfSrc, final JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}

}
