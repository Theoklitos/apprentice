package com.apprentice.rpg.gui.vault.type;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.body.BodyPartToRangeMapping;

/**
 * tests for the {@link TypeAndBodyPartFrameControl}
 * 
 * @author theoklitos
 * 
 */
public final class TestTypeAndBodyPartFrameState {

	private TypeAndBodyPartFrameState state;

	@Test
	public void addParts() {
		final String typeName = "testType";
		state.createType(typeName);
		final BodyPart part1 = new BodyPart("part1");
		final BodyPart part2 = new BodyPart("part2");
		state.addNewPartToType(typeName, part1);
		state.addNewPartToType(typeName, part2);

		final BodyPartToRangeMapping result = state.getBodyPartsForTypeName(typeName).getContent();
		assertEquals(part1, result.getPartsForNumber(1).get(0));
		assertEquals(part2, result.getPartsForNumber(2).get(0));

	}

	@Before
	public void setup() {
		state = new TypeAndBodyPartFrameState();
	}

}
