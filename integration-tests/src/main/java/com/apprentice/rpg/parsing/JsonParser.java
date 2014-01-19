package com.apprentice.rpg.parsing;

import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.PlayerLevels;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Is responsible for parsing/converting all classes to/from JSON
 * 
 * @author theoklitos
 * 
 */
public final class JsonParser implements ApprenticeParser {

	private final Gson gson;

	public JsonParser() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	private void emptynessCheck(final String text) {
		if (StringUtils.isEmpty(text)) {
			throw new ParsingEx("Nothing to parse!");
		}
	}

	/**
	 * Converts the given {@link PlayerCharacter} to a nice JSON string
	 */
	public String getAsJsonString(final PlayerCharacter playerCharacater) {
		return gson.toJson(playerCharacater);
	}

	@Override
	public PlayerLevels parseWithoutExperience(final String text) throws ParsingEx {
		emptynessCheck(text);
		final String rawText = text.toLowerCase().trim();
		final PlayerLevels result = new PlayerLevels();
		final StringTokenizer tokenizer = new StringTokenizer(rawText, "/");
		try {
			while (tokenizer.hasMoreElements()) {
				String className;
				int level;
				final String withLevels = tokenizer.nextElement().toString();
				if (withLevels.length() > 3 && StringUtils.isNumeric(withLevels.substring(withLevels.length() - 3))) {
					// level too big
					throw new ParsingEx("Levels up to 99 supported only.");
				} else if (withLevels.length() > 2
					&& StringUtils.isNumeric(withLevels.substring(withLevels.length() - 2))) {
					// 2 digit levels
					className = withLevels.substring(0, withLevels.length() - 2);
					level = Integer.valueOf(withLevels.substring(withLevels.length() - 2));
				} else if (withLevels.length() > 1
					&& StringUtils.isNumeric(withLevels.substring(withLevels.length() - 1))) {
					// 1 digit
					className = withLevels.substring(0, withLevels.length() - 1);
					level = Integer.valueOf(withLevels.substring(withLevels.length() - 1));
				} else {
					// no level
					throw new ParsingEx("No level was given for class \"" + withLevels + "\"");
				}
				result.addLevels(className, level);
			}

		} catch (final NumberFormatException e) {
			throw new ParsingEx("Could not understand level number: " + e.getMessage());
		}
		return result;
	}
}
