package com.apprentice.rpg.strike;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Tests for the {@link Effect}
 * 
 * @author theoklitos
 * 
 */
public final class TestEffect {

	@Test
	public void correctInitialization() {
		final Effect effect = new Effect("ACTION_LOSS", "1 round");
		assertEquals(EffectType.ACTION_LOSS, effect.getEffectType());
	}

	@Test
	public void equality() {
		final Effect eff1 = new Effect(EffectType.ACTION_LOSS);
		final Effect eff2 = new Effect(EffectType.ACTION_LOSS);
		final Effect eff3 = new Effect(EffectType.BLEEDING);

		assertEquals(eff1, eff2);
		assertThat(eff3, is(not(equalTo(eff1))));
		assertThat(eff3, is(not(equalTo(eff2))));
	}

	@Test(expected = NonRecognizedEffectTypeEx.class)
	public void noEffectType() {
		new Effect("WEIRD_EFFECT_NAME", "var1");
	}

	@Test
	public void succesfullParsing() {
		final Effect effect = new Effect("[bleeding]2,10 rounds(7)");
		assertEquals(EffectType.BLEEDING, effect.getEffectType());
		assertEquals(7, effect.getRounds());
		assertEquals(2, effect.getVariables().size());
		assertTrue(effect.getVariables().contains("2"));
		assertTrue(effect.getVariables().contains("10"));
	}
}
