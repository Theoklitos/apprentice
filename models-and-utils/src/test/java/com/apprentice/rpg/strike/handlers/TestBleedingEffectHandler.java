package com.apprentice.rpg.strike.handlers;

import static org.junit.Assert.assertEquals;

import org.jmock.Mockery;
import org.junit.Test;

import com.apprentice.rpg.random.ApprenticeRandom;
import com.apprentice.rpg.strike.Effect;

/**
 * tests for the {@link BleedingEffectHandler}
 * 
 * @author theoklitos
 * 
 */
public final class TestBleedingEffectHandler extends AbstractEffectHandlerTest {

	private BleedingEffectHandler handler;
	private Mockery mockery;
	private ApprenticeRandom random;

	@Test
	public void majorBleeding() {

	}

	@Test
	public void minorBleeding() {
		final Effect bleedingEffect = new Effect("[bleeding]0 rounds(9)");
		final int hitPointsBefore = getPlayerCharacter().getHitPoints().getCurrentHitPoints();
		getEffectManager().applyContinuousEffectToPlayer(bleedingEffect, getPlayerCharacter());
		assertEquals(hitPointsBefore, getPlayerCharacter().getHitPoints().getCurrentHitPoints());
		bleedingEffect.addRounds(1);
		getEffectManager().applyContinuousEffectToPlayer(bleedingEffect, getPlayerCharacter());
		assertEquals(hitPointsBefore - 1, getPlayerCharacter().getHitPoints().getCurrentHitPoints());
	}

	@Test
	public void severeBleeding() {

	}

}
