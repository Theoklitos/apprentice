package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.weapon.IWeapon;
import com.apprentice.rpg.model.weapon.Weapon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link IWeaponPrototype} json conversion
 * 
 * @author theoklitos
 * 
 */
public class WeaponDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<IWeapon> {

	public WeaponDeserializer() {
		super(IWeapon.class);
	}

	@Override
	public IWeapon deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		return context.deserialize(json, Weapon.class);
	}

}
