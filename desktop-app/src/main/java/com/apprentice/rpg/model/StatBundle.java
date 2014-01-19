package com.apprentice.rpg.model;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * A character's 6 {@link Stat}istics in one
 * 
 * @author theoklitos
 * 
 */
public class StatBundle {

	public enum StatType {
		STRENGTH,
		DEXTERITY,
		CONSTITUTION,
		INTELLIGENCE,
		WISDOM,
		CHARISMA
	}

	private final Map<StatType, Stat> statistics;

	public StatBundle(final int initialStrength, final int initialDexterity, final int initialConstitution,
			final int initialIntelligence, final int initialWisdom, final int initialCharisma) {
		this(new Stat(StatType.STRENGTH, initialStrength), new Stat(StatType.DEXTERITY, initialDexterity), new Stat(
				StatType.CONSTITUTION, initialConstitution), new Stat(StatType.INTELLIGENCE, initialIntelligence),
				new Stat(StatType.WISDOM, initialWisdom), new Stat(StatType.CHARISMA, initialCharisma));
	}

	public StatBundle(final Stat strength, final Stat dexterity, final Stat constitution, final Stat intelligence,
			final Stat wisdom, final Stat charisma) {
		statistics = Maps.newEnumMap(StatType.class);
		statistics.put(StatType.STRENGTH, strength);
		statistics.put(StatType.DEXTERITY, dexterity);
		statistics.put(StatType.CONSTITUTION, constitution);
		statistics.put(StatType.INTELLIGENCE, intelligence);
		statistics.put(StatType.WISDOM, wisdom);
		statistics.put(StatType.CHARISMA, charisma);
	}
}
