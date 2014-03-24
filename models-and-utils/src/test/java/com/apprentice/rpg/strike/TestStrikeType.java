package com.apprentice.rpg.strike;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.body.BodyPart;
import com.apprentice.rpg.strike.Effect.EffectType;
import com.apprentice.rpg.util.ApprenticeCollectionUtils;
import com.google.common.collect.Lists;

/**
 * tests for the {@link StrikeType}
 * 
 * @author theoklitos
 * 
 */
public final class TestStrikeType {

	private StrikeType type;

	@Test
	public void equality() {
		assertEquals(type, new StrikeType("Fire"));
		type.setDescription("new description");
		assertFalse(type.equals(new StrikeType("fire")));
		assertFalse(type.equals(new StrikeType("ice")));
	}

	@Test
	public void getSetEffects() {
		final BodyPart bodyPart = new BodyPart("tail");
		assertTrue(type.getEffectsForBodyPart(bodyPart, 0).isEmpty());
		assertEquals(0, type.getMappingForBodyPart(bodyPart).size());

		final Effect bleedingEffect = new Effect(EffectType.BLEEDING, 3);
		final Effect penaltyEffect = new Effect(EffectType.PENALTY, 1);
		final Effect breakEffect = new Effect(EffectType.DEBILITATION, 2);
		type.setEffects(bodyPart, 2, Lists.newArrayList(bleedingEffect, penaltyEffect));
		type.setEffects(bodyPart, 1, breakEffect);

		assertTrue(ApprenticeCollectionUtils.areAllElementsEqual(Lists.newArrayList(bleedingEffect, penaltyEffect),
				type.getEffectsForBodyPart(bodyPart, 2).getContent()));
		assertTrue(ApprenticeCollectionUtils.areAllElementsEqual(Lists.newArrayList(breakEffect), type
				.getEffectsForBodyPart(bodyPart, 1).getContent()));
	}

	@Before
	public void setup() {
		type = new StrikeType("fire");
	}
}
