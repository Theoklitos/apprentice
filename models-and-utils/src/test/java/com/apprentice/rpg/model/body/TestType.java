package com.apprentice.rpg.model.body;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * Tests for the {@link Type}
 * 
 * @author theoklitos
 * 
 */
public final class TestType {

	private static final String NAME = "type";
	private Type type;
	private BodyPartToRangeMap validParts;
	private BodyPart part1;
	private BodyPart part2;
	
	@Test(expected = ApprenticeEx.class)
	public void cannotGivePartOutsideRange() {
		type.getPartForNumber(101);
	}
	
	@Before
	public void initialize() {
		part1 = new BodyPart("part1 name");
		part2 = new BodyPart("part2 name");
		validParts = new BodyPartToRangeMap();
		validParts.setPartForRange(1, 50, part1);
		validParts.setPartForRange(51, 100, part2);
		type = new Type(NAME, validParts);
	}
	
	@Test(expected = BodyPartMappingEx.class)
	public void initializedWithWrongParts() {
		final BodyPartToRangeMap wrongMapping = new BodyPartToRangeMap();
		wrongMapping.setPartForRange(1, 70, part1);
		new Type(NAME, wrongMapping);
	}
	
	@Test
	public void isInitializedCorrectly() {
		assertEquals(NAME, type.getName());		
		assertEquals(validParts, type.getParts()); 
	}
	
	@Test
	public void partsAreRetrivedCorreclty() {
		assertEquals(part1,type.getPartForNumber(5)); 
		assertEquals(part1,type.getPartForNumber(50));
		assertEquals(part1,type.getPartForNumber(1));
		
		assertEquals(part2,type.getPartForNumber(51)); 
		assertEquals(part2,type.getPartForNumber(77));
		assertEquals(part2,type.getPartForNumber(100));
	}
}
