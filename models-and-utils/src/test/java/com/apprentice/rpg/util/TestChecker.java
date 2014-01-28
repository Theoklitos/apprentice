package com.apprentice.rpg.util;

import org.junit.Test;

import com.apprentice.rpg.dice.Roll;
import com.apprentice.rpg.model.ApprenticeEx;
import com.apprentice.rpg.model.magic.Spell;

/**
 * Tests for the {@link Checker}, using any class that uses it
 * 
 * @author theoklitos
 * 
 */
public final class TestChecker {

	@Test(expected = ApprenticeEx.class)
	public void initializationEmptyString() {
		new Roll("");
	}

	@Test(expected = ApprenticeEx.class)
	public void initializationNull() {
		new Spell(null, 20);
	}
	
	@Test(expected = ApprenticeEx.class)
	public void shouldCheckEmptyString() {
		Checker.checkNonNull("message", true, "");		
	}
		
	public void shouldNotCheckEmptyString() {
		// no excpetions
		Checker.checkNonNull("message", false, "");		
	}

}
