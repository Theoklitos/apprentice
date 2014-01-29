package com.apprentice.rpg.model;

import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.base.Objects;
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

	private final Map<String, Stat> statistics;

	public StatBundle(final int initialStrength, final int initialDexterity, final int initialConstitution,
			final int initialIntelligence, final int initialWisdom, final int initialCharisma) {
		this(new Stat(StatType.STRENGTH, initialStrength), new Stat(StatType.DEXTERITY, initialDexterity), new Stat(
				StatType.CONSTITUTION, initialConstitution), new Stat(StatType.INTELLIGENCE, initialIntelligence),
				new Stat(StatType.WISDOM, initialWisdom), new Stat(StatType.CHARISMA, initialCharisma));
	}

	public StatBundle(final Stat strength, final Stat dexterity, final Stat constitution, final Stat intelligence,
			final Stat wisdom, final Stat charisma) {
		statistics = Maps.newHashMap();
		statistics.put(StatType.STRENGTH.toString(), strength);
		statistics.put(StatType.DEXTERITY.toString(), dexterity);
		statistics.put(StatType.CONSTITUTION.toString(), constitution);
		statistics.put(StatType.INTELLIGENCE.toString(), intelligence);
		statistics.put(StatType.WISDOM.toString(), wisdom);
		statistics.put(StatType.CHARISMA.toString(), charisma);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof StatBundle) {
			final StatBundle stats = (StatBundle) other;
			return Objects.equal(statistics, stats.statistics);
		} else {
			return false;
		}
	}

	/**
	 * return the stat based on the requested type. There exists one stat for each {@link StatType}
	 */
	public Stat getStat(final StatType type) {
		return statistics.get(type.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(statistics);
	}

	@Override
	public String toString() {
		return Joiner.on(",").withKeyValueSeparator(":").join(statistics);
	}
}
