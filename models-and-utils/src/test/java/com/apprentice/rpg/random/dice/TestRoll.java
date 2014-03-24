package com.apprentice.rpg.random.dice;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.apprentice.rpg.model.ApprenticeEx;

public final class TestRoll {

	private static Logger LOG = Logger.getLogger(TestRoll.class);

	@Test
	public void addDice() throws RollException {
		final Roll roll = new Roll("2D6+7");
		roll.addDice(5, 1);

		Roll expected = new Roll("D5+2D6+7");
		assertTrue(roll.match(expected));

		roll.addDice(3, 3);

		expected = new Roll("D5+2D6+D3+7");
		assertTrue(roll.match(expected));

		roll.addDice(3);

		expected = new Roll("D5+2D6+2D3+7");
		assertTrue(roll.match(expected));
	}

	@Test
	public void addRoll() throws RollException {
		final Roll roll = new Roll("D6+1");
		roll.addRoll(new Roll("D4-1"));
		final Roll expected = new Roll("D6+D4");

		assertTrue(roll.match(expected));

		roll.addRoll(new Roll("D4+10-1"));
		final Roll expected2 = new Roll("D6+2D4+9");

		assertTrue(roll.match(expected2));
	}

	@Test
	public void equality() {
		final Roll roll1 = new Roll("D4-1+1+10-10");
		final Roll roll2 = new Roll("D4");
		assertEquals(roll1, roll2);
		assertEquals(new Roll("2d6+15"), new Roll("15+2D6"));
		assertFalse(new Roll("D6+15").equals(new Roll("2D6+1")));
		assertFalse(new Roll("d10").equals(new Roll("d9+1")));
	}

	@Test
	public void getRollFromDiceAndOccurrences() throws RollException {
		final Roll roll = Roll.getRollFromDiceAndOccurrences(7, 2);
		final Roll expected = new Roll("2D7");
		assertTrue(roll.match(expected));

		final Roll roll2 = Roll.getRollFromDiceAndOccurrences(-4, 5);
		final Roll expected2 = new Roll("-5D4");
		assertTrue(roll2.match(expected2));
	}

	@Test
	public void isZeroRoll() throws RollException {
		assertFalse(new Roll("D1+0").isZeroRoll());
		assertFalse(new Roll("D0+11").isZeroRoll());
		assertTrue(new Roll("D0+5-5").isZeroRoll());
		assertTrue(new Roll("12D0").isZeroRoll());
	}

	@Test
	public void match() throws RollException {
		Roll roll1 = new Roll("D3+15+D3-5");
		Roll roll2 = new Roll("2D3+10");

		assertTrue(roll1.match(roll2));

		roll1 = new Roll("D10");
		roll2 = new Roll("D10");

		assertTrue(roll1.match(roll2));

		roll1 = new Roll("D10");
		roll2 = new Roll("1+D10");

		assertFalse(roll1.match(roll2));
	}

	@Test(expected = RollException.class)
	public void noDices() throws RollException {
		final Roll roll = new Roll("1+11");
		roll.toString();
	}

	@Test
	public void parsing() throws RollException {
		final String rollString = "D6-7-4+2+D8+2D3+11-D4+D8+d8";
		final Roll roll = new Roll(rollString);

		LOG.info("Roll string '" + rollString + "' is parsed to: " + roll.toString());

		final String rollString2 = "D1+1+0-0+2D1-D1";
		final Roll roll2 = new Roll(rollString2);

		LOG.info("Roll string '" + rollString2 + "' is parsed to: " + roll2.toString());

		final String rollString3 = "1+5+2D4+3";
		final Roll roll3 = new Roll(rollString3);

		LOG.info("Roll string '" + rollString3 + "' is parsed to: " + roll3.toString());
	}

	@Test(expected = ApprenticeEx.class)
	public void parsingError() throws RollException {
		new Roll("D6+DZ1+1");;
	}

	@Test(expected = ApprenticeEx.class)
	public void parsingErrorNoDiceNumber() throws RollException {
		new Roll("2D");
	}

	@Test
	public void removeDice() throws RollException {
		final Roll roll = new Roll("2D6+7");
		roll.removeOneOccurenceOfDice(6);

		final Roll expected = new Roll("D6+7");
		assertTrue(roll.match(expected));

		roll.removeOneOccurenceOfDice(6);

		assertEquals("7", roll.toString());
	}
}
