package com.apprentice.rpg.rules;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.random.dice.Roll;

/**
 * tests for the {@link D20BasedRuleset}
 * 
 * @author theoklitos
 * 
 */
public final class TestD20BasedRuleset {

	private D20BasedRuleset rules;

	@Before
	public void setup() {
		rules = new D20BasedRuleset();
	}

	@Test
	public void testDiceDecrease() {
		final Roll originalRoll = new Roll("D9+15");
		assertEquals(new Roll("D8+15"), rules.getDecreasedRoll(originalRoll, 1));
		assertEquals(new Roll("D6+15"), rules.getDecreasedRoll(originalRoll, 2));
		assertEquals(new Roll("D4+15"), rules.getDecreasedRoll(originalRoll, 3));
		assertEquals(new Roll("D3+15"), rules.getDecreasedRoll(originalRoll, 4));
		assertEquals(new Roll("D2+15"), rules.getDecreasedRoll(originalRoll, 5));
		assertEquals(new Roll("D1+15"), rules.getDecreasedRoll(originalRoll, 6));
		assertEquals(new Roll("D0+15"), rules.getDecreasedRoll(originalRoll, 1000));
	}

	@Test
	public void testDiceIncrease() {
		final Roll originalRoll = new Roll("D0+2");
		assertEquals(new Roll("D1+2"), rules.getIncreasedRoll(originalRoll, 1));
		assertEquals(new Roll("D2+2"), rules.getIncreasedRoll(originalRoll, 2));
		assertEquals(new Roll("D3+2"), rules.getIncreasedRoll(originalRoll, 3));
		assertEquals(new Roll("D4+2"), rules.getIncreasedRoll(originalRoll, 4));
		assertEquals(new Roll("D6+2"), rules.getIncreasedRoll(originalRoll, 5));
		assertEquals(new Roll("D8+2"), rules.getIncreasedRoll(originalRoll, 6));
		assertEquals(new Roll("D10+2"), rules.getIncreasedRoll(originalRoll, 7));
	}
}
