package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.apprentice.rpg.model.StatBundle.StatType;
import com.apprentice.rpg.util.SortedList;

/**
 * Tests for the {@link SkillComparator}
 * 
 * @author theoklitos
 * 
 */
public final class TestSkillComparator {

	@Test
	public void ordering() {
		final SortedList<Skill> skills = new SortedList<Skill>(new SkillComparator());
		final Stat wisdom = new Stat(StatType.WISDOM, 12);
		final Skill listen = new Skill("Listen", wisdom, 3);
		final Skill spot = new Skill("Spot", wisdom, 5);
		final Skill jump = new Skill("jump", new Stat(StatType.STRENGTH, 8), 1);
		final Skill argh = new Skill("ARGH", new Stat(StatType.DEXTERITY, 18), 10);
		
		skills.add(spot);
		skills.add(listen);
		assertEquals(listen,skills.get(0));
		assertEquals(spot,skills.get(1));
		
		skills.add(argh);		
		assertEquals(argh,skills.get(0));
		assertEquals(listen,skills.get(1));
		assertEquals(spot,skills.get(2));
		
		skills.add(jump);		
		assertEquals(argh,skills.get(0));
		assertEquals(jump,skills.get(1));		
		assertEquals(listen,skills.get(2));
		assertEquals(spot,skills.get(3));
	}
}
