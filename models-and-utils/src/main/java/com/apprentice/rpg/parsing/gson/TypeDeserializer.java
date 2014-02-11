package com.apprentice.rpg.parsing.gson;

import java.util.Iterator;
import java.util.Map.Entry;

import com.apprentice.rpg.dao.simple.NameableVault;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMap;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.util.IntegerRange;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link Type} json conversion
 * 
 * @author theoklitos
 * 
 */
public class TypeDeserializer implements JsonDeserializer<Type> {

	private NameableVault nameableVault;

	@Override
	public Type deserialize(final JsonElement json, final java.lang.reflect.Type typeOfT,
			final JsonDeserializationContext context) throws JsonParseException {
		final JsonObject topLevelObject = json.getAsJsonObject();
		final BodyPartToRangeMap mapping = new BodyPartToRangeMap();
		final JsonObject partMapping =
			topLevelObject.get("parts").getAsJsonObject().get("partMapping").getAsJsonObject();
		final Iterator<Entry<String, JsonElement>> iterator = partMapping.entrySet().iterator();
		while (iterator.hasNext()) {
			final Entry<String, JsonElement> entry = iterator.next();
			final IntegerRange range = new IntegerRange(entry.getKey());
			final String bodyPartName = entry.getValue().getAsString();
			final BodyPart part = nameableVault.getUniqueNamedResult(bodyPartName, BodyPart.class);
			mapping.setPartForRange(range, part);
		}
		final Type result = new Type(topLevelObject.get("name").getAsString(), mapping);
		result.setDescription(topLevelObject.get("description").getAsString());
		return result;
	}

	public void setNameableVault(final NameableVault nameableVault) {
		this.nameableVault = nameableVault;
	}

}
