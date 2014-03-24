package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.armor.IArmorPiece;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helps with {@link IWeaponPrototype} json conversion
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceSerializer extends ApprenticeParsingComponent implements
		JsonSerializer<IArmorPiece> {

	public ArmorPieceSerializer() {
		super(IArmorPiece.class);
	}

	@Override
	public JsonElement serialize(final IArmorPiece src, final Type typeOfSrc,
			final JsonSerializationContext context) {		
		return new JsonPrimitive(src.getName());
	}

}
