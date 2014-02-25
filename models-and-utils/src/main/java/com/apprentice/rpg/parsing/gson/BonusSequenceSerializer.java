package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.combat.BonusSequence;
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
public class BonusSequenceSerializer extends ApprenticeParsingComponent implements JsonSerializer<BonusSequence> {

	public BonusSequenceSerializer() {
		super(BonusSequence.class);
	}

	@Override
	public JsonElement serialize(final BonusSequence src, final Type typeOfSrc, final JsonSerializationContext context) {
		return new JsonPrimitive(src.toString());
	}

}
