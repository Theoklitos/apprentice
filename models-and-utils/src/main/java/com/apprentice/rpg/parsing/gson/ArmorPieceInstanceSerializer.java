package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.armor.IArmorPiece;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helps with {@link WeaponPrototype} json conversion
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceInstanceSerializer extends ApprenticeParsingComponent implements
		JsonSerializer<IArmorPiece> {

	public ArmorPieceInstanceSerializer() {
		super(IArmorPiece.class);
	}

	@Override
	public JsonElement serialize(final IArmorPiece src, final Type typeOfSrc,
			final JsonSerializationContext context) {
		return context.serialize(src, IArmorPiece.class);
	}

}
