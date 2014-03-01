package com.apprentice.rpg.model.weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.rules.D20BasedRuleset;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.collect.Lists;

/**
 * tests for the {@link Weapon}
 * 
 * @author theoklitos
 * 
 */
public final class TestWeapon {

	private ApprenticeRandom random;
	private Mockery mockery;
	private D20BasedRuleset ruleset;
	private Weapon weapon;
	private int durability;
	private String name;
	private DamageRoll baseDamage;
	private DataFactory factory;

	@Test
	public void cannotSetDurabilityBelowZero() {
		weapon.getDurability().setMaximum(0);
		assertEquals(0, weapon.getDurability().getMaximum());
		weapon.getDurability().setMaximum(-10);
		assertEquals(0, weapon.getDurability().getMaximum());
	}

	@Test
	public void constructorSettingAndGetting() {
		assertEquals(new CurrentMaximumPair(durability), weapon.getDurability());
		assertEquals(name, weapon.getName());
		assertTrue(weapon.getRange().isEmpty());
		assertTrue(weapon.getThrownDamage().isEmpty());
	}

	@Test
	public void damageRolling() {
		final Weapon weapon = factory.getWeapons().get(1);
		// add one alternate damage, should not affect
		factory.getWeapons().get(1).getMeleeDamageRolls().add(new DamageRoll("d6", factory.getStrikeTypes().get(0)));

		final List<DamageRoll> extraDamages = Lists.newArrayList(weapon.getExtraDamages());
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(weapon.getMeleeDamageRolls().get(0));				
				will(returnValue(new Damage(5, weapon.getMeleeDamageRolls().get(0).getType())));
				allowing(random).roll(extraDamages.get(0));
				will(returnValue(new Damage(3, extraDamages.get(0).getType())));
				allowing(random).roll(extraDamages.get(1));
				will(returnValue(new Damage(2, extraDamages.get(1).getType())));
			}
		});
		final Collection<Damage> result = weapon.rollMeleeDamage(0, random);		
		assertEquals(3, result.size());
		assertTrue(result.contains(new Damage(5, factory.getStrikeTypes().get(1))));
		assertTrue(result.contains(new Damage(3, factory.getStrikeTypes().get(5))));
		assertTrue(result.contains(new Damage(2, factory.getStrikeTypes().get(4))));
	}

	@Test
	public void deteriorateAndRepairRanged() {
		weapon.setRangeAndOptimalThrownDamage("5/10/15", new DamageRoll("D4", new StrikeType("Piercing")));
		weapon.setHitPoints(10);

		System.out.println(weapon);
	}

	@Test
	public void equality() {
		final Weapon greatsword = factory.getWeapons().get(1);
		final Weapon identical =
			new Weapon("Magical Greatsword", 30, new DamageRoll("2D6+1", factory.getStrikeTypes().get(1)));
		identical.setDescription("Awesome magical greatsword");
		identical.getExtraDamages().add(new DamageRoll("1D6", factory.getStrikeTypes().get(5)));
		final DamageRoll coldDamage = new DamageRoll("1D10", factory.getStrikeTypes().get(4));
		identical.getExtraDamages().add(coldDamage);

		assertEquals(greatsword, identical);
		identical.getExtraDamages().remove(coldDamage);
		assertFalse(greatsword.equals(identical));
	}

	@Test
	public void getSetBlockModifier() {
		assertEquals(0, weapon.getBlockModifier());
		weapon.setBlockModifier(-5);
		assertEquals(-5, weapon.getBlockModifier());
		weapon.setBlockModifier(20);
		assertEquals(20, weapon.getBlockModifier());
	}

	@Test
	public void getSetRangeAndDamage() {
		assertTrue(weapon.getRange().isEmpty());
		assertTrue(weapon.getThrownDamage().isEmpty());
		weapon.setBlockModifier(5);
		final Range range = new Range("5/15/20");
		final DamageRoll rangedDamage = new DamageRoll("D8", factory.getStrikeTypes().get(3));
		weapon.setRangeAndOptimalThrownDamage(range, rangedDamage);
		assertEquals(range, weapon.getRange().getContent());
		assertEquals(rangedDamage, weapon.getThrownDamage().getContent());
	}

	@Test(expected = ApprenticeEx.class)
	public void getSetRangedDamageWithoutRange() {
		weapon.setThrownDamage(new DamageRoll("D8", factory.getStrikeTypes().get(3)));
	}

	@Test(expected = ApprenticeEx.class)
	public void getSetRangeWithoutDamage() {
		weapon.setRange(new Range("10/20/30"));
	}

	@Test
	public void setDurability() {
		final CurrentMaximumPair durability = weapon.getDurability();
		durability.setMaximum(3);
		assertEquals(3, durability.getCurrent());
		durability.setMaximum(40);
		durability.setCurrent(30);
		assertEquals(40, durability.getMaximum());
		assertEquals(30, durability.getCurrent());
	}

	@Before
	public void setup() {
		mockery = new Mockery();
		durability = 20;
		ruleset = new D20BasedRuleset();
		random = mockery.mock(ApprenticeRandom.class);
		factory = new DataFactory();
		name = "sword";
		baseDamage = new DamageRoll("2D6+4", factory.getStrikeTypes().get(1));
		weapon = new Weapon(name, durability, baseDamage);
		weapon.setRuleset(ruleset);
	}

	@After
	public void teardown() {
		mockery.assertIsSatisfied();
	}

}
