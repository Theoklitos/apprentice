package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.combat.BonusSequence;
import com.apprentice.rpg.model.combat.CombatCapabilities;
import com.apprentice.rpg.model.weapon.IWeapon;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Deserializes a {@link CombatCapabilities} object
 * 
 * @author theoklitos
 * 
 */
public final class CombatCapabilitiesDeserializer extends ApprenticeParsingComponent implements
		JsonDeserializer<CombatCapabilities> {

	public CombatCapabilitiesDeserializer() {
		super(CombatCapabilities.class);
	}

	@Override
	public CombatCapabilities deserialize(final JsonElement json, final Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {
		final CombatCapabilities result = new CombatCapabilities();
		final JsonObject parent = json.getAsJsonObject();
		final int modifier = parent.get("modifier").getAsInt();
		result.addToModifier(modifier);
		final JsonArray weaponSkills = parent.getAsJsonArray("weaponSkills");
		for (final JsonElement element : weaponSkills) {
			final IWeapon instance =
				context.deserialize(element.getAsJsonObject().getAsJsonObject("weapon"), IWeapon.class);
			final BonusSequence sequence =
				new BonusSequence(element.getAsJsonObject().getAsJsonPrimitive("sequence").getAsString());
			result.setWeaponForSequence(instance, sequence);
		}
		return result;
	}

}
