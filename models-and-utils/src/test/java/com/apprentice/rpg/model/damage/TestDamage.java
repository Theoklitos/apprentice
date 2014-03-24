package com.apprentice.rpg.model.damage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.apprentice.rpg.strike.StrikeType;

/**
 * Tests for the {@link Damage}
 * 
 * @author theoklitos
 * 
 */
public class TestDamage {

	@Test
	public void equality() {
		final Damage damage1 = new Damage(10, new Penetration(5), new StrikeType("test"));
		final Damage damage2 = new Damage(10, new Penetration(5), new StrikeType("test"));
		assertEquals(damage1, damage2);
		final Damage damage3 = new Damage(10, new StrikeType("test"));
		assertFalse(damage1.equals(damage3));
	}

}
