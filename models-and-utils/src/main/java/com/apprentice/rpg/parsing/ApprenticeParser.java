package com.apprentice.rpg.parsing;

import java.util.List;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * Converts and parses classes to other types of formats
 * 
 * @author theoklitos
 * 
 */
public interface ApprenticeParser {

	/**
	 * Converts the given {@link IPlayerCharacter} to a json object in string format
	 */
	String getAsJsonString(IPlayerCharacter playerCharacter);

	/**
	 * used to export all the {@link IType}s and the {@link BodyPart} as json object(s)
	 */
	String getBodyPartsAndTypesAsJsonString(final List<IType> types, final List<BodyPart> bodyParts);	
	
	/**
	 * parses a "warrior1/fighter2" text to a {@link PlayerLevels} object. Does not parse experience.
	 * 
	 * @throws ParsingEx
	 */
	PlayerLevels parseWithoutExperience(String text) throws ParsingEx;

}
