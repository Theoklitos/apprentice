package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerCharacter;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Deserializes a {@link IPlayerCharacter}
 * 
 * @author theoklitos
 * 
 */
public final class PlayerCharacterDeserializer extends ApprenticeParsingComponent implements
		JsonDeserializer<IPlayerCharacter> {

	public PlayerCharacterDeserializer() {
		super(IPlayerCharacter.class);
	}

	@Override
	public IPlayerCharacter deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {		
		return context.deserialize(json, PlayerCharacter.class);
	}

}
