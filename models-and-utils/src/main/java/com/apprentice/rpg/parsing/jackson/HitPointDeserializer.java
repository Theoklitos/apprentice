package com.apprentice.rpg.parsing.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.apprentice.rpg.model.HitPoints;

/**
 * Using to parse {@link HitPoints} objects
 * 
 * @author theoklitos
 * 
 */
public class HitPointDeserializer extends JsonDeserializer<HitPoints> {

	@Override
	public HitPoints deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		jp.nextToken(); // advance to the "current"
		jp.nextValue();
		final int current = jp.getIntValue();
		jp.nextToken(); // advance to the "maximum"
		jp.nextValue();
		final int maximum = jp.getValueAsInt();
		final HitPoints result = new HitPoints(maximum);
		result.setCurrentHitPoints(current);
		jp.nextToken();
		return result;
	}

}
