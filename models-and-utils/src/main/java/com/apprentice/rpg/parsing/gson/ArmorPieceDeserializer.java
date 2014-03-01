package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.armor.ArmorPiece;
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
public final class ArmorPieceDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<IArmorPiece> {

	public ArmorPieceDeserializer() {
		super(IArmorPiece.class);
	}

	@Override
	public IArmorPiece deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		if (isNameLookupEnabled()) {
			return getNameableVault().getUniqueNamedResult(json.getAsString(), ArmorPiece.class);
		} else {
			return context.deserialize(json, ArmorPiece.class);
		}
	}

}
