package com.apprentice.rpg.model.magic;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link Spell} class
 * 
 * @author theoklitos
 * 
 */
public final class TestSpell {

	private static final int COST = 20;
	private static final String DESCRIPTION = "flaming ball 5d6";
	private static final String NAME = "fireball";
	private Spell spell;

	@Test
	public void costCannotFallBelowZero() {
		spell.setCost(-5);
		assertEquals(0,spell.getCost());
	}
	
	@Before
	public void initialize() {
		spell = new Spell(NAME, COST, DESCRIPTION);
	}
	
	@Test
	public void isInitializedCorrectly() {
		assertEquals(NAME,spell.getName());
		assertEquals(COST,spell.getCost());
		assertEquals(DESCRIPTION,spell.getDescription());
	}
	
	@Test
	public void settersAndGettersWork() {
		spell.setCost(10);
		assertEquals(10,spell.getCost());
		spell.setDescription("whatever");
		assertEquals("whatever",spell.getDescription());		
		spell.setDescription(null);
		assertEquals(Spell.NO_DESCRIPTION_VALUE,spell.getDescription());
	}
}
