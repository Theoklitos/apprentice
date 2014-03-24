package com.apprentice.rpg.random;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.random.dice.Roll;

/**
 * tests for the {@link ApprenticePseudoRandom}
 * 
 * @author theoklitos
 * 
 */
public final class TestApprenticePseudoRandom {

	private ApprenticePseudoRandom random;

	/**
	 * rolls the dice 1000 times and makes sure its within the range
	 */
	private void assertDiceIsWithinRange(final Roll roll, final int min, final int max) {
		for (int i = 0; i < 1000; i++) {
			final int result = random.roll(roll);
			assertTrue(result >= min && result <= max);
		}
	}

	@Test
	public void rangeIsCorrect() {
		assertDiceIsWithinRange(new Roll("D7"), 1, 7);
		assertDiceIsWithinRange(new Roll("D7+7"), 8, 14);
		assertDiceIsWithinRange(new Roll("D12"), 1, 12);
		assertDiceIsWithinRange(new Roll("2D6+1"), 3, 13);
	}

//	@Test
//	public void rollDamage() {
//		final Roll roll = new Roll("D8+5");
//		final StrikeType strikeType = new StrikeType("test");
//		final int penetration = 7;
//		final DamageRoll damageRoll = new DamageRoll(roll, penetration, strikeType);
//		final Damage damage = random.roll(damageRoll);
//		assertDiceIsWithinRange(roll, 6, 13);
//		assertEquals(penetration, damage.getPenetrationHP());
//		assertEquals(strikeType, damage.getType());
//	}

	@Before
	public void setup() {
		random = new ApprenticePseudoRandom();
	}
}
