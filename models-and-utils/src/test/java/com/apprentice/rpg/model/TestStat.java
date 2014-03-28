package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.apprentice.rpg.model.playerCharacter.Stat;
import com.apprentice.rpg.model.playerCharacter.StatBundle.StatType;

public final class TestStat {

	@Test
	public void changingTheStatBonusIsAccurate() {
		final StatType type = StatType.STRENGTH;
		final Stat stat = new Stat(type, 12);

		stat.setValue(15);
		assertEquals(2, stat.getBonus());
		stat.setValue(5);
		assertEquals(-3, stat.getBonus());
		stat.setValue(22);
		assertEquals(6, stat.getBonus());
		stat.setValue(1);
		assertEquals(-5, stat.getBonus());
	}

	@Test
	public void statBonusisCorrect() {
		final StatType type = StatType.CHARISMA;
		final Stat stat = new Stat(type, 12);

		assertEquals(1, stat.getBonus());		
		assertEquals(-3, new Stat(type, 5).getBonus());
		assertEquals(-5, new Stat(type, 1).getBonus());
		assertEquals(+2, new Stat(type, 15).getBonus());
		assertEquals(+3, new Stat(type, 17).getBonus());
		assertEquals(+4, new Stat(type, 18).getBonus());
		assertEquals(+6, new Stat(type, 22).getBonus());
	}
}
