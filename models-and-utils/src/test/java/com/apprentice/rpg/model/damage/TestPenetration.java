package com.apprentice.rpg.model.damage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.apprentice.rpg.model.damage.Penetration.PenetrationType;

/**
 * tests for the {@link Penetration}
 * 
 * @author theoklitos
 * 
 */
public class TestPenetration {

	@Test
	public void equality() {
		final Penetration p1 = new Penetration(10);
		final Penetration p2 = new Penetration(10);
		assertEquals(p1, p2);
	}

	@Test
	public void getType() {
		final Penetration penetration = new Penetration(10);
		assertTrue(penetration.getPenetrationType().isEmpty());
		final Penetration penetration2 = new Penetration(PenetrationType.FULL);
		assertEquals(PenetrationType.FULL, penetration2.getPenetrationType().getContent());
	}
}
