package com.apprentice.rpg.parsing.gson;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.model.weapon.Weapon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link Weapon} json conversion
 * 
 * @author theoklitos
 * 
 */
public class WeaponDeserializer implements JsonDeserializer<Weapon> {

	private NameableVault nameableVault;

	@Override
	public Weapon deserialize(final JsonElement json, final java.lang.reflect.Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject topLevelObject = json.getAsJsonObject();
		return null;
	}

	public void setNameableVault(final NameableVault nameableVault) {
		this.nameableVault = nameableVault;
	}

}
