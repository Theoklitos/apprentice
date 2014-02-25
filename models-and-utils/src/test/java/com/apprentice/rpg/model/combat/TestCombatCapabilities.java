package com.apprentice.rpg.model.combat;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.factories.DataFactory;
import com.apprentice.rpg.model.weapon.IWeaponInstance;
import com.apprentice.rpg.model.weapon.WeaponInstance;

/**
 * tests for the {@link CombatCapabilities}
 * 
 * @author theoklitos
 * 
 */
public final class TestCombatCapabilities {

	private CombatCapabilities combat;
	private DataFactory factory;
	private IWeaponInstance weapon;

	@Test(expected = ApprenticeEx.class)
	public void doesNotHaveWeapon() {
		combat.getBonusSequenceForWeapon(weapon);
	}

	@Test
	public void modifierAddsAndSubtracts() {
		assertEquals(0, combat.getModifier());
		combat.addToModifier(10);
		assertEquals(10, combat.getModifier());
		combat.addToModifier(-7);
		assertEquals(3, combat.getModifier());
		combat.addToModifier(-18);
		assertEquals(-15, combat.getModifier());
	}

	@Test
	public void modifyWeaponSkill() {
		final BonusSequence sequence = new BonusSequence(7);
		combat.setWeaponForSequence(weapon, sequence);
		assertEquals(sequence, combat.getBonusSequenceForWeapon(weapon));
		combat.addToModifier(-4);
		assertEquals(new BonusSequence(3), combat.getBonusSequenceForWeapon(weapon));
	}

	@Before
	public void setup() {
		combat = new CombatCapabilities();
		factory = new DataFactory();
		weapon = new WeaponInstance(factory.getWeapons().get(0));
	}

}
