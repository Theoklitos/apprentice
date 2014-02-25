package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.armor.ArmorPiece;
import com.apprentice.rpg.model.weapon.Weapon;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helps with {@link Weapon} json conversion
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceSerializer extends ApprenticeParsingComponent implements
		JsonSerializer<ArmorPiece> {

	public ArmorPieceSerializer() {
		super(ArmorPiece.class);
	}

	@Override
	public JsonElement serialize(final ArmorPiece src, final Type typeOfSrc,
			final JsonSerializationContext context) {		
		return new JsonPrimitive(src.getName());
	}

}
