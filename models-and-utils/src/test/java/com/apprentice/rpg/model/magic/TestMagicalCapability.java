package com.apprentice.rpg.model.magic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests for the {@link MagicalCapability} class
 * 
 * @author theoklitos
 *
 */
public class TestMagicalCapability {
	
	private MagicalCapability magic;

	@Before
	public void initialize() {
		magic = new MagicalCapability();
	}
	
	@Test
	public void understandsWhenHasMagicalCapability() {
		magic.getSpellPoints().setMaximumSpellPoints(0);
		assertFalse(magic.hasMagicCapability());
		magic.getSpellPoints().setMaximumSpellPoints(1);
		assertTrue(magic.hasMagicCapability());
	}
}
