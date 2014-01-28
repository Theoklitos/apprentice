package com.apprentice.rpg.model.body;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link BodyPartToRangeMap}
 * 
 * @author theoklitos
 * 
 */
public final class TestBodyPartToRangeMap {

	private BodyPartToRangeMap mapping;
	private BodyPart head;
	private BodyPart arms;
	private BodyPart legs;
	private BodyPart balls;

	@Test
	public void getNonExistingPart() {		
		assertEquals(0,mapping.getPartsForNumber(60).size());
		assertEquals(0,mapping.getPartsForNumber(78).size());
		assertEquals(0,mapping.getPartsForNumber(99).size());
	}

	@Test
	public void getSetPart() {
		assertEquals(head, mapping.getPartsForNumber(2).get(0));
		assertEquals(head, mapping.getPartsForNumber(10).get(0));
		assertEquals(arms, mapping.getPartsForNumber(15).get(0));
		assertEquals(legs, mapping.getPartsForNumber(30).get(0));
		assertEquals(balls, mapping.getPartsForNumber(31).get(0));

		final BodyPart anotherArm = new BodyPart("another arm");
		mapping.setPartForRange(11, 15, anotherArm);
		final List<BodyPart> foundArms = mapping.getPartsForNumber(14);
		assertEquals(2, foundArms.size());		
		assertEquals(anotherArm, foundArms.get(0));
		assertEquals(arms, foundArms.get(1));
	}

	@Test(expected = BodyPartMappingEx.class)
	public void numberOutOfRange() {
		mapping.getPartsForNumber(101);
	}

	@Test
	public void removePart() {
		assertFalse(mapping.removePart(new BodyPart("non existing1")));
		assertFalse(mapping.removePart(new BodyPart("non existing2")));
		assertTrue(mapping.removePart(legs));
		assertFalse(mapping.removePart(legs));
		assertTrue(mapping.removePart(arms));
	}

	@Before
	public void setup() {
		head = new BodyPart("head");
		arms = new BodyPart("arms");
		legs = new BodyPart("legs");
		balls = new BodyPart("balls");
		mapping = new BodyPartToRangeMap();
		mapping.setPartForRange(1, 10, head);
		mapping.setPartForRange(11, 20, arms);
		mapping.setPartForRange(21, 30, legs);
		mapping.setPartForRange(31, 40, balls);
	}
}
