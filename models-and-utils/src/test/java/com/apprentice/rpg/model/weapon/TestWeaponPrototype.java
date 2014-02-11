package com.apprentice.rpg.model.weapon;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.strike.StrikeType;

/**
 * tests for the {@link WeaponPrototype}
 * 
 * @author theoklitos
 * 
 */
public final class TestWeaponPrototype {

	private WeaponPrototype weapon;
	private StrikeType type;
	private int durability;
	private String name;
	private DamageRoll baseDamage;

	@Test
	public void cannotSetDurabilityBelowZero() {
		weapon.setMaximumDurability(0);
		assertEquals(0,weapon.getMaximumDurability());
		weapon.setMaximumDurability(-10);
		assertEquals(0,weapon.getMaximumDurability());
	}

	@Test
	public void constructorSettingAndGetting() {
		assertEquals(durability, weapon.getMaximumDurability());
		assertEquals(name, weapon.getName());
	}

	@Before
	public void setup() {
		durability = 20;
		type = new StrikeType("Slashing");
		name = "sword";
		baseDamage = new DamageRoll("2D6+4", type);
		weapon = new WeaponPrototype(name, durability, baseDamage);
	}
}
