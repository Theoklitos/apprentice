package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.armor.ArmorPieceInstance;
import com.apprentice.rpg.model.armor.IArmorPieceInstance;
import com.apprentice.rpg.model.weapon.Weapon;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helps with {@link Weapon} json conversion
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceInstanceSerializer extends ApprenticeParsingComponent implements
		JsonSerializer<IArmorPieceInstance> {

	public ArmorPieceInstanceSerializer() {
		super(IArmorPieceInstance.class);
	}

	@Override
	public JsonElement serialize(final IArmorPieceInstance src, final Type typeOfSrc,
			final JsonSerializationContext context) {
		return context.serialize(src, ArmorPieceInstance.class);
	}

}
