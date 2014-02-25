package com.apprentice.rpg.model.weapon;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.model.factories.DataFactory;
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
	private DataFactory factory;

	@Test
	public void cannotSetDurabilityBelowZero() {
		weapon.setMaximumDurability(0);
		assertEquals(0, weapon.getMaximumDurability());
		weapon.setMaximumDurability(-10);
		assertEquals(0, weapon.getMaximumDurability());
	}

	@Test
	public void constructorSettingAndGetting() {
		assertEquals(durability, weapon.getMaximumDurability());
		assertEquals(name, weapon.getName());
	}

	@Test
	public void equality() {
		final Weapon greatsword = factory.getWeapons().get(1);
		final Weapon identical =
			new WeaponPrototype("Magical Greatsword", 30, new DamageRoll("2D6+1", factory.getStrikeTypes().get(1)));
		identical.setDescription("Awesome magical greatsword");
		identical.getExtraDamages().add(new DamageRoll("1D6", factory.getStrikeTypes().get(4)));
		final DamageRoll coldDamage = new DamageRoll("1D10", factory.getStrikeTypes().get(3));
		identical.getExtraDamages().add(coldDamage);

		assertEquals(greatsword, identical);
		identical.getExtraDamages().remove(coldDamage);
		assertFalse(greatsword.equals(identical));
	}

	@Before
	public void setup() {
		durability = 20;
		type = new StrikeType("Slashing");
		name = "sword";
		baseDamage = new DamageRoll("2D6+4", type);
		factory = new DataFactory();
		weapon = new WeaponPrototype(name, durability, baseDamage);
	}
}
