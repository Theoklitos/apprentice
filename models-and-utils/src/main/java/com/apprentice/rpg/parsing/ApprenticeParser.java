package com.apprentice.rpg.parsing;

import com.apprentice.rpg.model.PlayerLevels;

/**
 * Converts and parses classes to other types of formats
 * 
 * @author theoklitos
 * 
 */
public interface ApprenticeParser {

	/**
	 * parses a "warrior1/fighter2" text to a {@link PlayerLevels} object. Does not parse experience.
	 * 
	 * @throws ParsingEx
	 */
	PlayerLevels parseWithoutExperience(String text) throws ParsingEx;

}
