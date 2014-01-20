package com.apprentice.rpg.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public final class TestPlayerLevels {

	private PlayerLevels levels;

	@Test
	public void canExtendLevels() {
		// does not exist initially
		assertEquals(0, levels.getLevels("WARRIOR"));
						
		// now we increment it to 2
		levels.addLevel("Warrior");
		levels.addLevel("Warrior");
		assertEquals(2, levels.getLevels("warrior"));
		
		// now to 20
		levels.addLevels("WarrioR", 18);
		assertEquals(20, levels.getLevels("WARRior"));
	}

	@Test
	public void canGetAndSetExperienceCorrectly() {
		assertEquals(0,levels.getExperiencePoints());
		
		levels.addExperiencePoints(100);
		assertEquals(100,levels.getExperiencePoints());
		
		levels.addExperiencePoints(900);
		levels.addExperiencePoints(1500);
		assertEquals(2500,levels.getExperiencePoints());
	}

	@Before
	public void setup() {
		levels = new PlayerLevels();
	}
}
