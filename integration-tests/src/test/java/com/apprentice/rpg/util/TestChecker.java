package com.apprentice.rpg.util;

import org.junit.Test;

import com.apprentice.rpg.ApprenticeEx;
import com.apprentice.rpg.dice.Roll;
import com.apprentice.rpg.dice.RollException;

public final class TestChecker {
	// we are testing with any class that uses the Checker.
	
	@Test(expected = ApprenticeEx.class)
	public void testInitializationEmptyString() throws RollException {
		new Roll("");
	}

	@Test(expected = ApprenticeEx.class)
	public void testInitializationNull() {
		//MeleeRange.getCreateFromString(null); TODO
	}

}
