package com.apprentice.rpg.model.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.util.IntegerRange;

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
	private IntegerRange range2;
	private IntegerRange range1;

	@Test(expected = ApprenticeEx.class)
	public void cannotGivePartOutsideRange() {
		type.getPartForNumber(101);
	}
	
	@Test
	public void equality() {
		final IType type2 = new Type(NAME, validParts);
		assertEquals(type,type2);		
		type.setDescription("different description");
		assertFalse(type.equals(type2));
	}

	@Test
	public void getRangeForName() {
		assertTrue(type.getRangeForPartName("part1 name").hasContent());
		assertEquals(range1, type.getRangeForPartName("part1 name").getContent());
		assertTrue(type.getRangeForPartName("part2 name").hasContent());
		assertEquals(range2, type.getRangeForPartName("part2 name").getContent());
	}

	@Before
	public void initialize() {
		part1 = new BodyPart("part1 name");
		part2 = new BodyPart("part2 name");
		range1 = new IntegerRange(1, 50);
		range2 = new IntegerRange(51, 100);
		validParts = new BodyPartToRangeMap();
		validParts.setPartForRange(range1, part1);
		validParts.setPartForRange(range2, part2);
		type = new Type(NAME, validParts);
	}

	@Test(expected = BodyPartMappingEx.class)
	public void initializedWithNonConsecutive() {
		final BodyPartToRangeMap wrongMapping = new BodyPartToRangeMap();		
		wrongMapping.setPartForRange(1, 50, part1);
		wrongMapping.setPartForRange(52, 100, part2);
		new Type(NAME, wrongMapping);
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
		assertEquals(validParts, type.getPartMapping());
	}

	@Test
	public void partsAreRetrivedCorreclty() {
		assertEquals(part1, type.getPartForNumber(5));
		assertEquals(part1, type.getPartForNumber(50));
		assertEquals(part1, type.getPartForNumber(1));

		assertEquals(part2, type.getPartForNumber(51));
		assertEquals(part2, type.getPartForNumber(77));
		assertEquals(part2, type.getPartForNumber(100));
	}
}
