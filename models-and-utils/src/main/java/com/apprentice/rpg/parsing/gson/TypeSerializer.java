package com.apprentice.rpg.parsing.gson;

import java.util.Map.Entry;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.apprentice.rpg.model.body.Type;
import com.apprentice.rpg.util.IntegerRange;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

/**
 * Helps with {@link Type} json conversion
 * 
 * @author theoklitos
 * 
 */
public class TypeSerializer extends ApprenticeParsingComponent implements JsonSerializer<IType> {

	public TypeSerializer() {
		super(IType.class);
	}

	@Override
	public JsonElement serialize(final IType src, final java.lang.reflect.Type typeOfSrc,
			final JsonSerializationContext context) {
		final JsonObject result = new JsonObject();		
		result.addProperty("name", src.getName());
		result.addProperty("description", src.getDescription());
		final JsonObject parts = new JsonObject();
		final JsonObject partMapping = new JsonObject();
		for (final Entry<IntegerRange, BodyPart> mapping : src.getPartMapping().getSequentialMapping()) {
			partMapping.addProperty(mapping.getKey().toString(), mapping.getValue().getName());
		}
		parts.add("partMapping", partMapping);
		result.add("parts", parts);
		return result;
	}

}
