package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.weapon.IWeapon;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link IWeaponInstance} json conversion
 * 
 * @author theoklitos
 * 
 */
public class WeaponInstanceDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<IWeapon> {

	public WeaponInstanceDeserializer() {
		super(IWeapon.class);
	}

	@Override
	public IWeapon deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		//final JsonObject topLevel = json.getAsJsonObject();
		//final String prototypeName = topLevel.get("prototype").getAsString();
		throw new ApprenticeEx("implement me!");
// final Weaponresult =
// new WeaponInstance(getNameableVault().getUniqueNamedResult(prototypeName, Weapon.class));
// final int curretHitPoints = topLevel.get("hitPoints").getAsJsonObject().get("current").getAsInt();
// result.setHitPoints(curretHitPoints); TODO
		// return result;
	}

}
