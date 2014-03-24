package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.combat.CombatCapabilities;
import com.apprentice.rpg.model.weapon.IWeapon;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Deserializes a {@link CombatCapabilities} object
 * 
 * @author theoklitos
 * 
 */
public final class CombatCapabilitiesSerializer extends ApprenticeParsingComponent implements
		JsonSerializer<CombatCapabilities> {

	public CombatCapabilitiesSerializer() {
		super(CombatCapabilities.class);
	}

	@Override
	public JsonElement serialize(final CombatCapabilities src, final Type typeOfSrc,
			final JsonSerializationContext context) {
		final JsonObject result = new JsonObject();
		result.addProperty("modifier", src.getModifier());
		final JsonArray weaponSkills = new JsonArray();
		for (final IWeapon instance : src.getWeapons()) {
			final JsonObject pair = new JsonObject();
			final JsonElement jsonInstance = context.serialize(instance, IWeapon.class);
			pair.add("weapon", jsonInstance);
			pair.addProperty("sequence", src.getBonusSequenceForWeapon(instance).toString());
			weaponSkills.add(pair);
		}
		result.add("weaponSkills", weaponSkills);
		return result;
	}

}
