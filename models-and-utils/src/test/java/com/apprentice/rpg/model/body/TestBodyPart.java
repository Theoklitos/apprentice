package com.apprentice.rpg.model.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

/**
 * tests for the {@link BodyPart}
 * 
 * @author theoklitos
 * 
 */
public final class TestBodyPart {

	private final static String NAME = "head";
	private BodyPart part;

	@Test
	public void equality() {
		final BodyPart identical = new BodyPart(NAME);
		assertEquals(part, identical);
		part.setDescription("bla bla bla");
		assertFalse(part.equals(identical));
		
		final BodyPart identicalLowerCase = new BodyPart("HEAD");
		part.setDescription("");		
		assertEquals(part, identicalLowerCase);
	}

	@Before
	public void setup() {
		part = new BodyPart(NAME);
	}

}
