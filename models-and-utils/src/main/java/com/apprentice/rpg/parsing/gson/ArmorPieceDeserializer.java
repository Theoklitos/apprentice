package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.armor.ArmorPiecePrototype;
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
public final class ArmorPieceDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<ArmorPiece> {

	public ArmorPieceDeserializer() {
		super(ArmorPiece.class);
	}

	@Override
	public ArmorPiece deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		if (isNameLookupEnabled()) {
			return getNameableVault().getUniqueNamedResult(json.getAsString(), ArmorPiecePrototype.class);
		} else {
			return context.deserialize(json, ArmorPiecePrototype.class);
		}
	}

}
