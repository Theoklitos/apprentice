package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.apprentice.rpg.model.StatBundle.StatType;

/**
 * Tests for the skill
 * 
 * @author theoklitos
 * 
 */
public class TestSkill {

	@Test
	public void equality() {
		final Stat str = new Stat(StatType.STRENGTH, 19);
		final Skill jump1 = new Skill("jump", str, 4);
		final Skill jump2 = new Skill("jump", str, 4);
		assertEquals(jump1, jump2);
	}

	@Test
	public void getSetTotalModifier() {
		final Stat wis = new Stat(StatType.WISDOM, 16);
		final Skill listen = new Skill("listen", wis, 4);
		assertEquals(7, listen.getTotalBonus());
		wis.setValue(4);
		assertEquals(1, listen.getTotalBonus());
	}
}
