package com.apprentice.rpg.model.playerCharacter;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Compares {@link Skill}s, used in ordering
 * 
 * @author theoklitos
 * 
 */
public class SkillComparator implements Comparator<Skill> {

	@Override
	public int compare(final Skill skill1, final Skill skill2) {
		final String name1 = skill1.getName();
		final String name2 = skill2.getName();
		final String[] names = new String[] { name1, name2 };
		Arrays.sort(names);
		if (names[0].equals(name1)) {
			return -1;
		} else if (names[0].equals(name2)) {
			return 1;
		} else {
			return 0;
		}
	}
}
