package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link IArmorPiece} json conversion
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceInstanceDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<IArmorPiece> {

	public ArmorPieceInstanceDeserializer() {
		super(IArmorPiece.class);
	}

	@Override
	public IArmorPiece deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {		
		return context.deserialize(json, IArmorPiece.class);
	}

}
