package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.combat.BonusSequence;
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
public final class BonusSequenceDeserializer extends ApprenticeParsingComponent implements
		JsonDeserializer<BonusSequence> {

	public BonusSequenceDeserializer() {
		super(BonusSequence.class);
	}

	@Override
	public BonusSequence deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {
		return new BonusSequence(json.getAsString());
	}

}
