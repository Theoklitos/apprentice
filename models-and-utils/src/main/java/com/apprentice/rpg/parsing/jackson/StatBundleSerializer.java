package com.apprentice.rpg.parsing.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.apprentice.rpg.model.Stat;
import com.apprentice.rpg.model.StatBundle;
import com.apprentice.rpg.model.StatBundle.StatType;

/**
 * converts a {@link StatBundle} to nice json
 * 
 * @author theoklitos
 * 
 */
public final class StatBundleSerializer extends JsonSerializer<StatBundle> {

	@Override
	public void serialize(final StatBundle value, final JsonGenerator jgen, final SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		writeStat(value, StatType.STRENGTH, jgen);		
		writeStat(value, StatType.DEXTERITY, jgen);
		writeStat(value, StatType.CONSTITUTION, jgen);
		writeStat(value, StatType.INTELLIGENCE, jgen);
		writeStat(value, StatType.WISDOM, jgen);
		writeStat(value, StatType.CHARISMA, jgen);
		jgen.writeEndObject();
	}

	private void writeStat(final StatBundle bundle, final StatType statType, final JsonGenerator jgen)
			throws IOException, JsonGenerationException {
		final Stat stat = bundle.getStat(statType);		
		jgen.writeFieldName(statType.toString());		
		jgen.writeStartObject();
		jgen.writeFieldName("originalValue");
		jgen.writeNumber(stat.getOriginalValue());
		jgen.writeFieldName("currentValue");
		jgen.writeNumber(stat.getValue());
		jgen.writeEndObject();
		
	}

}
