package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.apprentice.rpg.model.playerCharacter.HitPoints;

/**
 * Tests for the {@link HitPoints} classκαπ
 * @author theoklitos
 *
 */
public final class TestHitPoints {

	private HitPoints hitPoints;

	@Test
	public void cannotSetCurrentHigherThanMaximum() {
		hitPoints.setMaximumHitPoints(10);
		hitPoints.setCurrentHitPoints(900);
		assertEquals(10, hitPoints.getMaximumHitPoints());
		assertEquals(10, hitPoints.getCurrentHitPoints());		
	}
	
	@Test
	public void decreasingMaxBelowCurrentAlsoDecreasesCurrent() {
		hitPoints.setMaximumHitPoints(10);
		hitPoints.setCurrentHitPoints(9);
		hitPoints.setMaximumHitPoints(5);		
		assertEquals(5, hitPoints.getCurrentHitPoints());		
	}
	
	@Test
	public void isInitialCorrect() {
		assertEquals(1, hitPoints.getMaximumHitPoints());
	}
	
	@Before
	public void setup() {
		hitPoints = new HitPoints(1);
	}

	@Test
	public void testSetAndGet() {
		hitPoints.setMaximumHitPoints(40);
		hitPoints.setCurrentHitPoints(20);
		assertEquals(40, hitPoints.getMaximumHitPoints());
		assertEquals(20, hitPoints.getCurrentHitPoints());
	}
}
