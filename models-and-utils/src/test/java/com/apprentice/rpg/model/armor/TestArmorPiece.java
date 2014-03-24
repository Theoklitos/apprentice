package com.apprentice.rpg.model.armor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.random.dice.RollWithSuffix;
import com.apprentice.rpg.rules.D20BasedRuleset;
import com.apprentice.rpg.rules.Ruleset;

/**
 * tests for the {@link ArmorPiece}
 * 
 * @author theoklitos
 * 
 */
public final class TestArmorPiece {

	private ArmorPiece armorPiece;
	private Ruleset ruleset;

	@Test
	public void changeTheBaseRoll() {
		final RollWithSuffix newRoll = new RollWithSuffix("2D10+2");
		armorPiece.setDamageReductionRoll(newRoll);
		assertEquals(newRoll, armorPiece.getDamageReduction());
	}

	@Test
	public void changeTheBaseRollAndDeteriorationIsConsistent() {
		armorPiece.removeHitPoints(10);
		assertEquals(new Roll("D4+1"), armorPiece.getDamageReduction());
		final RollWithSuffix newRoll = new RollWithSuffix("2D10+2");
		armorPiece.setDamageReductionRoll(newRoll);
		assertEquals(new RollWithSuffix("2D8+2"), armorPiece.getDamageReduction());
	}

	@Test
	public void drLessensWithDamage() {
		armorPiece.removeHitPoints(20);
		final Roll updatedRoll = new Roll("D3+1");
		assertEquals(updatedRoll, armorPiece.getDamageReduction());
	}

	@Test
	public void durabilityChangeInPrototype() {
		armorPiece.getDurability().setMaximum(1);;
		assertEquals(1, armorPiece.getDurability().getMaximum());
		assertEquals(1, armorPiece.getDurability().getCurrent());
	}

	@Test
	public void fitsInBodyPart() {
		final BodyPart head = new BodyPart("head");
		assertTrue(armorPiece.fits(head));
		armorPiece.setBodyPartDesignator("HEAD");
		assertTrue(armorPiece.fits(head));
		assertTrue(armorPiece.fits(new BodyPart("giant head")));
		assertFalse(armorPiece.fits(new BodyPart("leg")));
		assertFalse(armorPiece.fits(new BodyPart("arm")));
	}

	@Before
	public void setup() {
		ruleset = new D20BasedRuleset();
		armorPiece = new ArmorPiece("great helmet +5", 30, new RollWithSuffix("2D4+1", 5), "head");
		armorPiece.setRuleset(ruleset);
	}

}
