package com.apprentice.rpg.strike;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.apprentice.rpg.strike.Effect.EffectType;

/**
 * Tests for the {@link Effect}
 * 
 * @author theoklitos
 * 
 */
public final class TestEffect {

	@Test
	public void testEquality() {
		final Effect eff1 = new Effect(EffectType.ACTION_LOSS, 4);
		final Effect eff2 = new Effect(EffectType.ACTION_LOSS, 4);
		final Effect eff3 = new Effect(EffectType.BLEEDING, 4);

		assertEquals(eff1, eff2);
		assertThat(eff3, is(not(equalTo(eff1))));
		assertThat(eff3, is(not(equalTo(eff2))));
	}
}
