package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import com.apprentice.rpg.model.playerCharacter.SavingThrows;
import com.apprentice.rpg.parsing.ParsingEx;

/**
 * tests for the {@link SavingThrows}
 * 
 * @author theoklitos
 * 
 */
public final class TestSavingThrows {

	@Test
	public void correctInitialization() {
		final SavingThrows saves = new SavingThrows(2, 10, -2);
		assertEquals(2, saves.getFortitude());
		assertEquals(10, saves.getReflex());
		assertEquals(-2, saves.getWill());
	}

	@Test
	public void correctParsing() {
		final SavingThrows saves = new SavingThrows("+21/-10/0");
		assertEquals(21, saves.getFortitude());
		assertEquals(-10, saves.getReflex());
		assertEquals(0, saves.getWill());
	}

	@Test
	public void equality() {
		assertEquals(new SavingThrows("+1/+10/-5"), new SavingThrows("+1/+10/-5"));
		assertFalse(new SavingThrows("+1/+10/-5").equals(new SavingThrows("+2/+10/-5")));
	}

	@Test(expected = ParsingEx.class)
	public void parsingNAN() {
		new SavingThrows("+3/+1/a");
	}

}
