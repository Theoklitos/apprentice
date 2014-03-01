package com.apprentice.rpg.model.armor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.D20BasedRuleset;
import com.apprentice.rpg.rules.Ruleset;

/**
 * tests for the {@link ArmorPiece}
 * 
 * @author theoklitos
 * 
 */
public final class TestArmor {

	private ArmorPiece armorPiece;
	private DataFactory factory;
	private Mockery mockery;
	private Ruleset ruleset;
	private ApprenticeRandom random;

	@Test
	public void changeTheBaseRoll() {
		final Roll newRoll = new Roll("2D10+2");
		armorPiece.setDamageReductionRoll(newRoll);
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(newRoll);
				will(returnValue(10));
			}
		});
		assertEquals(10, armorPiece.rollDamageReduction(random));
	}

	@Test
	public void changeTheBaseRollAndDeteriorationIsConsistent() {
		armorPiece.removeHitPoints(10);
		assertEquals(new Roll("D4+1"), armorPiece.getDamageReductionRoll());
		final Roll newRoll = new Roll("2D10+2");
		armorPiece.setDamageReductionRoll(newRoll);
		assertEquals(new Roll("2D8+2"), armorPiece.getDamageReductionRoll());
	}

	@Test
	public void drLessensWithDamage() {
		armorPiece.removeHitPoints(20);
		final Roll updatedRoll = new Roll("D3+1");
		assertEquals(updatedRoll, armorPiece.getDamageReductionRoll());
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(updatedRoll);
				will(returnValue(2));
			}
		});
		assertEquals(2, armorPiece.rollDamageReduction(random));
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
		mockery = new Mockery();
		ruleset = new D20BasedRuleset();
		random = mockery.mock(ApprenticeRandom.class);
		factory = new DataFactory();
		armorPiece = new ArmorPiece("great helmet", 30, new Roll("2D4+1"), "head");
		armorPiece.setRuleset(ruleset);
	}

}
