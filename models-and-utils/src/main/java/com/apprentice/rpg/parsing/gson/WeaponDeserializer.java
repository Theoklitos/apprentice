package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.weapon.Weapon;
import com.apprentice.rpg.model.weapon.WeaponPrototype;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link Weapon} json conversion
 * 
 * @author theoklitos
 * 
 */
public class WeaponDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<Weapon> {

	public WeaponDeserializer() {
		super(Weapon.class);
	}

	@Override
	public Weapon deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		return context.deserialize(json, WeaponPrototype.class);
	}

}
