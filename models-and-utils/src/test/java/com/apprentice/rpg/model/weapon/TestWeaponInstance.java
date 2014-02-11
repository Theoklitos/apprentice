package com.apprentice.rpg.model.weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.Ruleset;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.collect.Lists;

/**
 * tests for the {@link WeaponInstance}
 * 
 * @author theoklitos
 * 
 */
public final class TestWeaponInstance {

	private Weapon prototype;
	private WeaponInstance instance;
	private DataFactory factory;
	private Mockery mockery;
	private ApprenticeRandom random;
	private Ruleset ruleset;

	@Test
	public void damageRolling() {
		final WeaponInstance instance = new WeaponInstance(factory.getWeapons().get(1));
		final List<DamageRoll> extraDamages = Lists.newArrayList(instance.getExtraDamages());		
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(instance.getBaseDamage());
				will(returnValue(new Damage(5, instance.getBaseDamage().getType())));
				allowing(random).roll(extraDamages.get(0));
				will(returnValue(new Damage(3, extraDamages.get(0).getType())));
				allowing(random).roll(extraDamages.get(1));
				will(returnValue(new Damage(2, extraDamages.get(1).getType())));
			}
		});
		final Collection<Damage> result = instance.rollDamage(random);
		
		assertEquals(3,result.size());
		assertTrue(result.contains(new Damage(5, new StrikeType("Slashing"))));
		assertTrue(result.contains(new Damage(3, new StrikeType("Cold"))));
		assertTrue(result.contains(new Damage(2, new StrikeType("Fire"))));
	}

	@Test
	public void durabilityChangeInPrototype() {
		prototype.setMaximumDurability(1);
		assertEquals(1, instance.getDurability().getMaximum());
		assertEquals(1, instance.getHitPoints());
	}

	private void expectDecreasedRollAddHP(final int hitPointsToAdd, final int positionsToDrop, final Roll whatToReturn) {
		expectRollChange(hitPointsToAdd, positionsToDrop, whatToReturn, false);
	}

	private void expectDecreasedRollRemoveHP(final int hitPointsToRemove, final int positionsToDrop,
			final Roll whatToReturn) {
		expectRollChange(hitPointsToRemove, positionsToDrop, whatToReturn, true);
	}

	private void expectRollChange(final int hitPointsToChange, final int positionsToDrop, final Roll whatToReturn,
			final boolean shouldDecrease) {
		mockery.checking(new Expectations() {
			{
				allowing(ruleset).getDecreasedRoll(prototype.getBaseRoll(), positionsToDrop);
				will(returnValue(whatToReturn));
			}
		});
		if (shouldDecrease) {
			instance.removeHitPoints(hitPointsToChange);
		} else {
			instance.addHitPoints(hitPointsToChange);
		}
		assertEquals(whatToReturn, instance.getBaseDamage().getRoll());
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		ruleset = mockery.mock(Ruleset.class);
		random = mockery.mock(ApprenticeRandom.class);
		factory = new DataFactory();
		prototype = factory.getWeapons().get(0);
		mockery.checking(new Expectations() {
			{
				allowing(ruleset).getDeteriorationIncrementForType(WeaponInstance.class);
				will(returnValue(3));
			}
		});
		instance = new WeaponInstance(prototype);
		expectDecreasedRollRemoveHP(0, 0, instance.getCurrentRoll());
		instance.setRuleset(ruleset);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

	@Test
	public void weaponDeteriorates() {
		final DamageRoll originalRoll = prototype.getOriginalBaseDamage();
		instance.removeHitPoints(5);
		assertEquals(originalRoll, instance.getBaseDamage());
		expectDecreasedRollRemoveHP(1, 1, new Roll("D6"));
		expectDecreasedRollRemoveHP(6, 2, new Roll("D4"));
		expectDecreasedRollRemoveHP(1, 2, new Roll("D4"));
		expectDecreasedRollRemoveHP(5, 3, new Roll("D3"));
		expectDecreasedRollRemoveHP(10000, 4, new Roll("D0"));
	}

	@Test
	public void weaponRepairs() {
		expectDecreasedRollRemoveHP(10000, 4, new Roll("D0"));
		expectDecreasedRollAddHP(1, 3, new Roll("D3"));
		expectDecreasedRollAddHP(5, 2, new Roll("D4"));
		expectDecreasedRollAddHP(6, 1, new Roll("D6"));
		expectDecreasedRollAddHP(2, 1, new Roll("D6"));
		expectDecreasedRollAddHP(4, 0, new Roll("D8"));
		expectDecreasedRollAddHP(20000, 0, new Roll("D8"));
	}
}
