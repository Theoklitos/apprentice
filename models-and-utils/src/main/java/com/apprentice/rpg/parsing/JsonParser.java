package com.apprentice.rpg.parsing;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Is responsible for parsing/converting all classes to/from JSON
 * 
 * @author theoklitos
 * 
 */
public final class JsonParser implements ApprenticeParser {

	public enum JsonPrintingOptions {
		PLAYER_CHARACTERS,
		TYPES,
		BODY_PARTS,
		WEAPONS,
		ARMORS;
	}
	
	private final Gson gson;

	public JsonParser() {
		gson = new GsonBuilder().setPrettyPrinting().create();
	}

	private void emptynessCheck(final String text) {
		if (StringUtils.isEmpty(text)) {
			throw new ParsingEx("Nothing to parse!");
		}
	}

	@Override
	public String getAsJsonString(final IPlayerCharacter playerCharacter) {
		try {
			return "gay";
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public String getBodyPartsAndTypesAsJsonString(final List<IType> types, final List<BodyPart> bodyParts) {
		try {
			return "gay";
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	/**
	 * Attempts to parse a json object (in string form) to a fully fledged {@link IPlayerCharacter}
	 * 
	 * @throws ParsingEx
	 */
	public IPlayerCharacter parsePlayerCharacter(final String playerAsJson) throws ParsingEx {
		try {
			return null;
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
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
