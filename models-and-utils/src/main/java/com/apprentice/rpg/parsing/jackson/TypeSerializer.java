package com.apprentice.rpg.parsing.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;

import com.apprentice.rpg.model.body.IType;

/**
 * Used to serialize {@link IType}
 * 
 * @author theoklitos
 * 
 */
public class TypeSerializer extends JsonSerializer<IType> {

	@Override
	public void serialize(final IType value, final JsonGenerator jgen, final SerializerProvider provider)
			throws IOException, JsonProcessingException {
		jgen.writeStartObject();
		jgen.writeFieldName("name");
		jgen.writeString(value.getName());
		
		jgen.writeFieldName("parts");
		jgen.writeStartObject();
		//for(final BodyPart part : value.getParts()) {
		//	jgen.writeFieldName("name");
		//	jgen.writeString(part.getName());
		//}		
		jgen.writeEndObject();		
		jgen.writeEndObject();
	}

}
