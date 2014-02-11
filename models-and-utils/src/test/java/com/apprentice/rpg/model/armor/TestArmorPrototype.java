package com.apprentice.rpg.model.armor;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.random.dice.Roll;

/**
 * tests for the {@link ArmorPiecePrototype}
 * 
 * @author theoklitos
 * 
 */
public final class TestArmorPrototype {

	private ArmorPiecePrototype prototype;

	@Test
	public void fitsInBodyPart() {
		final BodyPart head = new BodyPart("head");
		assertTrue(prototype.fits(head));
		prototype.setBodyPartDesignator("HEAD");
		assertTrue(prototype.fits(head));
		assertTrue(prototype.fits(new BodyPart("giant head")));
		assertFalse(prototype.fits(new BodyPart("leg")));
		assertFalse(prototype.fits(new BodyPart("arm")));
	}
		
	@Before
	public void setup() {
		prototype = new ArmorPiecePrototype("great helmet", 30, new Roll("2D4+1"), "head");
	}
}
