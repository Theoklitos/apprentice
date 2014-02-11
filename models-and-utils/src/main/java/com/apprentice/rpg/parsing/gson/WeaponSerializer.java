package com.apprentice.rpg.parsing.gson;

import com.apprentice.rpg.dao.simple.NameableVault;
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
public class WeaponSerializer implements JsonSerializer<Weapon> {

	private NameableVault nameableVault;

	@Override
	public JsonElement serialize(final Weapon src, final java.lang.reflect.Type typeOfSrc, final JsonSerializationContext context) {
		// TODO Auto-generated method stub
		return null;
	}

}
