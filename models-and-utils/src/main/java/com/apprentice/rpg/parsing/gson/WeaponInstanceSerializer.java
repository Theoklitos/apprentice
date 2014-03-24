package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.weapon.IWeapon;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helps with {@link IWeaponInstance} json conversion
 * 
 * @author theoklitos
 * 
 */
public class WeaponInstanceSerializer extends ApprenticeParsingComponent implements JsonSerializer<IWeapon> {

	public WeaponInstanceSerializer() {
		super(IWeapon.class);
	}

	@Override
	public JsonElement serialize(final IWeapon src, final Type typeOfSrc, final JsonSerializationContext context) {
		final JsonObject result = new JsonObject();
		// result.addProperty("prototype", src.getPrototype().getName());
		// result.add("hitPoints", context.serialize(src.getDurability())); TODO
		return result;
	}

}
