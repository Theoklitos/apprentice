package com.apprentice.rpg.model.weapon;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.apprentice.rpg.model.damage.DamageRoll;
import com.apprentice.rpg.strike.StrikeType;

/**
 * tests for the {@link AmmunitionType}
 * 
 * @author theoklitos
 * 
 */
public final class TestAmmunition {

	@Test
	public void equality() {
		final AmmunitionType ammo1 = new AmmunitionType("bullet", new DamageRoll("D10", new StrikeType("Projectile")));
		final AmmunitionType ammo2 = new AmmunitionType("bullet", new DamageRoll("D10", new StrikeType("Projectile")));
		assertTrue(ammo1.equals(ammo2));
		ammo2.setDamage(new DamageRoll("D10+1", new StrikeType("Projectile")));
		assertThat(ammo1, not(equalTo(ammo2)));
	}

	@Test
	public void initialization() {
		final String name = "arrow";
		final String description = "description";
		final DamageRoll damage = new DamageRoll("D6", new StrikeType("Projectile"));
		final AmmunitionType ammo = new AmmunitionType(name, damage);
		ammo.setDescription(description);
		assertEquals(name, ammo.getName());
		assertEquals(description, ammo.getDescription());
		assertEquals(damage, ammo.getDamage());
	}

	@Test
	public void setAndGetDamage() {
		final DamageRoll damage = new DamageRoll("D6", new StrikeType("Projectile"));
		final AmmunitionType ammo = new AmmunitionType("dagger", damage);
		assertEquals(damage, ammo.getDamage());
		final DamageRoll damage2 = new DamageRoll("D8", new StrikeType("Fire"));
		ammo.setDamage(damage2);
		assertEquals(damage2, ammo.getDamage());
	}
}
