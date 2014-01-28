package com.apprentice.rpg.parsing;

import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang3.StringUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectMapper.DefaultTyping;
import org.codehaus.jackson.node.JsonNodeFactory;
import org.codehaus.jackson.node.ObjectNode;

import com.apprentice.rpg.model.IPlayerCharacter;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.PlayerLevels;
import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.IType;

/**
 * Is responsible for parsing/converting all classes to/from JSON
 * 
 * @author theoklitos
 * 
 */
public final class JsonParser implements ApprenticeParser {

	private final ObjectMapper mapper;

	public JsonParser() {
		mapper = new ObjectMapper();
		// mapper.configure(Feature.FAIL_ON_EMPTY_BEANS, false);
		mapper.enableDefaultTyping(DefaultTyping.NON_CONCRETE_AND_ARRAYS);
	}

	private void emptynessCheck(final String text) {
		if (StringUtils.isEmpty(text)) {
			throw new ParsingEx("Nothing to parse!");
		}
	}

	@Override
	public String getAsJsonString(final IPlayerCharacter playerCharacter) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(playerCharacter);
		} catch (final Exception e) {
			throw new ParsingEx(e);
		}
	}

	@Override
	public String getBodyPartsAndTypesAsJsonString(final List<IType> types, final List<BodyPart> bodyParts) {
		try {			
			final JsonNodeFactory nodeFactory = JsonNodeFactory.instance;
			final ObjectNode parentNode = nodeFactory.objectNode();
			
			//final String oneTypeJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(types.get(0));						
			//final JsonNode typesNode = mapper.valueToTree(bodyParts.get(0));						
			//final ObjectNode bodyPartsNode = mapper.valueToTree(bodyParts);
			
			//final JsonNode
			//bodyPartsNode.put
			
			final ObjectNode typesNode = parentNode.putObject("types");
			
			
			//parentNode.put("bodyParts", bodyPartsNode);
			
			return parentNode.toString();
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
			return mapper.readValue(playerAsJson, PlayerCharacter.class);
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
