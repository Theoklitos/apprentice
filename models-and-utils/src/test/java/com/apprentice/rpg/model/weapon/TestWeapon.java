package com.apprentice.rpg.model.weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.CurrentMaximumPair;
import com.apprentice.rpg.model.damage.Damage;
import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.damage.Penetration;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.random.dice.Roll;
import com.apprentice.rpg.rules.D20BasedRuleset;
import com.apprentice.rpg.strike.StrikeType;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * tests for the {@link AbstractWeapon}
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
	public void createInstance() {
		final IWeapon weapon = factory.getWeaponPrototypes().get(1);
		final IWeapon instance = weapon.clone();
		assertNotSame(weapon, instance);
		instance.setPrototype(true);
		assertEquals(weapon, instance);
		instance.addMeleeDamage(new DamageRoll("D8+1", factory.getStrikeTypes().get(2)));
		assertEquals(2, instance.getMeleeDamages().size());
		assertEquals(1, weapon.getMeleeDamages().size());
	}

	@Test
	public void damageRolling() {
		final IWeapon weapon = factory.getWeaponPrototypes().get(1).clone();
		// add one alternate damage, should not affect
		factory.getWeaponPrototypes().get(1).getMeleeDamages()
				.add(new DamageRoll("d6", factory.getStrikeTypes().get(0)));
		final Penetration firstDamagePenetration = weapon.getMeleeDamages().get(0).getPenetration();

		final List<DamageRoll> extraDamages = Lists.newArrayList(weapon.getExtraDamages());
		mockery.checking(new Expectations() {
			{
				allowing(random).roll(weapon.getMeleeDamages().get(0));
				will(returnValue(new Damage(5, firstDamagePenetration, weapon.getMeleeDamages().get(0).getType())));
				allowing(random).roll(extraDamages.get(0));
				will(returnValue(new Damage(3, extraDamages.get(0).getType())));
				allowing(random).roll(extraDamages.get(1));
				will(returnValue(new Damage(2, extraDamages.get(1).getType())));
			}
		});
		final Collection<Damage> result = weapon.rollMeleeDamage(0, random);
		assertEquals(3, result.size());
		assertTrue(result.contains(new Damage(5, weapon.getMeleeDamages().get(0).getPenetration(), factory
				.getStrikeTypes().get(1))));
		assertTrue(result.contains(new Damage(3, factory.getStrikeTypes().get(5))));
		assertTrue(result.contains(new Damage(2, factory.getStrikeTypes().get(4))));
	}

	@Test
	public void deteriorateAndRepair() {
		weapon.setRangeAndThrownDamage("5/10/15", new DamageRoll("D4-1", new StrikeType("Piercing")));
		weapon.setHitPoints(10);
		assertEquals(new Roll("D3-1"), weapon.getThrownDamage().getContent().getRoll());
		weapon.setHitPoints(7);
		assertEquals(new Roll("D2-1"), weapon.getThrownDamage().getContent().getRoll());
		weapon.setHitPoints(20000);
		assertEquals(new Roll("D4-1"), weapon.getThrownDamage().getContent().getRoll());
	}

	@Test
	public void equality() {
		final IWeapon greatsword = factory.getWeaponPrototypes().get(1);
		final Weapon identical =
			new Weapon("Magical Greatsword", 30, new DamageRoll("2D6+1", new Penetration(2), factory.getStrikeTypes()
					.get(1)));
		identical.setPrototype(true);
		identical.setDescription(greatsword.getDescription());

		final DamageRoll coldDamage = new DamageRoll("1D10", factory.getStrikeTypes().get(4));
		final Set<DamageRoll> extraDamages =
			Sets.newHashSet(new DamageRoll("1D6", factory.getStrikeTypes().get(5)), coldDamage);
		identical.setExtraDamages(extraDamages);

		assertEquals(greatsword, identical);
		final Collection<DamageRoll> extraDamagesWithoutCold = identical.getExtraDamages();
		extraDamagesWithoutCold.remove(coldDamage);
		identical.setExtraDamages(extraDamagesWithoutCold);
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
		weapon.setRangeAndThrownDamage(range, rangedDamage);
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

	@Test
	public void setExtraDamage() {
		final IWeapon longsword = factory.getWeaponPrototypes().get(0);
		assertEquals(0, longsword.getExtraDamages().size());
		final DamageRoll extraDamage = new DamageRoll("D8", factory.getStrikeTypes().get(4));
		longsword.setExtraDamages(Sets.newHashSet(extraDamage));
		assertEquals(1, longsword.getExtraDamages().size());
		assertEquals(extraDamage, longsword.getExtraDamages().iterator().next());
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
