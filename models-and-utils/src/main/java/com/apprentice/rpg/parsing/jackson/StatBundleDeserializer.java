package com.apprentice.rpg.parsing.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.apprentice.rpg.model.Stat;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;

/**
 * parses a {@link StatBundle}
 * 
 * @author theoklitos
 * 
 */
public final class StatBundleDeserializer extends JsonDeserializer<StatBundle> {

	@Override
	public StatBundle deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException,
			JsonProcessingException {		
		final Stat strength = readStat(StatType.STRENGTH, jp);
		final Stat dexterity = readStat(StatType.DEXTERITY, jp);
		final Stat constitution = readStat(StatType.CONSTITUTION, jp);
		final Stat intelligence = readStat(StatType.INTELLIGENCE, jp);
		final Stat wisdom = readStat(StatType.WISDOM, jp);
		final Stat charisma = readStat(StatType.CHARISMA, jp);
		jp.nextToken();
		return new StatBundle(strength, dexterity, constitution, intelligence, wisdom, charisma);
	}

	private Stat readStat(final StatType type, final JsonParser jp) throws JsonParseException, IOException {
		jp.nextToken();
		jp.nextToken();
		jp.nextValue(); // we move to original value
		final int original = jp.getIntValue();
		jp.nextToken();
		jp.nextValue(); // current
		final int current = jp.getIntValue();
		final Stat result = new Stat(type, original);
		result.setValue(current);
		jp.nextToken();	
		return result;
	}
}
