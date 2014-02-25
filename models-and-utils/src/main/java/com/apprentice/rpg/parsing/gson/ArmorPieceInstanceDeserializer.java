package com.apprentice.rpg.parsing.gson;

import java.lang.reflect.Type;

import com.apprentice.rpg.model.armor.ArmorPieceInstance;
import com.apprentice.rpg.model.armor.IArmorPieceInstance;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

/**
 * Helps with {@link IArmorPieceInstance} json conversion
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceInstanceDeserializer extends ApprenticeParsingComponent implements JsonDeserializer<IArmorPieceInstance> {

	public ArmorPieceInstanceDeserializer() {
		super(IArmorPieceInstance.class);
	}

	@Override
	public IArmorPieceInstance deserialize(final JsonElement json, final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {		
		return context.deserialize(json, ArmorPieceInstance.class);
	}

}
