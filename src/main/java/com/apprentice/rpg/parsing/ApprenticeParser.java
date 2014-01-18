package com.apprentice.rpg.parsing;

import com.apprentice.rpg.model.PlayerCharacter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Is responsible for parsing/converting all classes to/from JSON
 * 
 * @author theoklitos
 *
 */
public final class JsonParser {

	private final Gson gson;
	
	public JsonParser() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}
	
	/**
	 * Converts the given {@link PlayerCharacter} to a nice JSON string
	 */
	public String getAsJsonString(final PlayerCharacter playerCharacater) {
		return gson.toJson(playerCharacater);
	}
}
