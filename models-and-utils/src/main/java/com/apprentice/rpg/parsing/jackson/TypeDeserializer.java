package com.apprentice.rpg.parsing.jackson;

import java.io.IOException;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import com.apprentice.rpg.model.body.IType;

/**
 * used to deserialie {@link IType}s
 * 
 * @author theoklitos
 * 
 */
public class TypeDeserializer extends JsonDeserializer<IType> {

	@Override
	public IType deserialize(final JsonParser jp, final DeserializationContext ctxt) throws IOException,
			JsonProcessingException {
		
		// TODO Auto-generated method stub
		return null;
	}
}
