package com.apprentice.rpg.model.armor;

import static org.junit.Assert.assertEquals;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.D20BasedRuleset;
import com.apprentice.rpg.rules.Ruleset;

/**
 * tests for the {@link ArmorPieceInstance}
 * 
 * @author theoklitos
 * 
 */
public final class TestArmorInstance {

	private ArmorPiece prototype;
	private ArmorPieceInstance instance;
	private DataFactory factory;
	private Mockery mockery;
	private Ruleset ruleset;
	private ApprenticeRandom random;

	@Test
	public void changeTheBaseRoll() {
		final Roll newRoll = new Roll("2D10+2");
		prototype.setBaseRoll(newRoll);
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(newRoll);
				will(returnValue(10));
			}
		});
		assertEquals(10, instance.rollDamageReduction(random));
	}
	
	@Test
	public void changeTheBaseRollAndDeteriorationIsConsistent() {		
		instance.removeHitPoints(10);
		assertEquals(new Roll("D4+1"), instance.getCurrentRoll());		
		final Roll newRoll = new Roll("2D10+2");
		prototype.setBaseRoll(newRoll);
		assertEquals(new Roll("2D8+2"), instance.getCurrentRoll());
	}

	@Test
	public void drLessensWithDamage() {
		instance.removeHitPoints(20);
		final Roll updatedRoll = new Roll("D3+1");
		assertEquals(updatedRoll, instance.getCurrentRoll());
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(updatedRoll);
				will(returnValue(2));
			}
		});
		assertEquals(2, instance.rollDamageReduction(random));
	}

	@Test
	public void durabilityChangeInPrototype() {
		prototype.setMaximumDurability(1);
		assertEquals(1, instance.getDurability().getMaximum());
		assertEquals(1, instance.getHitPoints());
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		ruleset = new D20BasedRuleset();
		random = mockery.mock(ApprenticeRandom.class);		
		factory = new DataFactory();	
		prototype = factory.getArmorPieces().get(0);
		instance = new ArmorPieceInstance(prototype);
		instance.setRuleset(ruleset);		
	}

}
