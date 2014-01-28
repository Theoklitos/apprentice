package com.apprentice.rpg.model.magic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TestSpellPoints {
	
	private SpellPoints spellPoints;
	
	@Before
	public void initialize() {	
		spellPoints = new SpellPoints(0);
	}
	
	@Test
	public void isInitializedCorrectly() {
		assertEquals(0,spellPoints.getMaximumSpellPoints());
		assertEquals(0,spellPoints.getCurrentSpellPoints());
	}	
}
