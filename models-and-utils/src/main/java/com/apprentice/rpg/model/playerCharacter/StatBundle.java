package com.apprentice.rpg.model.playerCharacter;

import java.util.Collection;
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

	private final Map<String, Stat> statMapping;

	public StatBundle(final int initialStrength, final int initialDexterity, final int initialConstitution,
			final int initialIntelligence, final int initialWisdom, final int initialCharisma) {
		this(new Stat(StatType.STRENGTH, initialStrength), new Stat(StatType.DEXTERITY, initialDexterity), new Stat(
				StatType.CONSTITUTION, initialConstitution), new Stat(StatType.INTELLIGENCE, initialIntelligence),
				new Stat(StatType.WISDOM, initialWisdom), new Stat(StatType.CHARISMA, initialCharisma));
	}
	
	public StatBundle(final Stat strength, final Stat dexterity, final Stat constitution, final Stat intelligence,
			final Stat wisdom, final Stat charisma) {
		statMapping = Maps.newLinkedHashMap();
		statMapping.put(StatType.STRENGTH.toString(), strength);
		statMapping.put(StatType.DEXTERITY.toString(), dexterity);
		statMapping.put(StatType.CONSTITUTION.toString(), constitution);
		statMapping.put(StatType.INTELLIGENCE.toString(), intelligence);
		statMapping.put(StatType.WISDOM.toString(), wisdom);
		statMapping.put(StatType.CHARISMA.toString(), charisma);
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof StatBundle) {
			final StatBundle stats = (StatBundle) other;
			return Objects.equal(statMapping, stats.statMapping);
		} else {
			return false;
		}
	}

	/**
	 * returns all the (6) stats	 
	 */
	public Collection<Stat> getAll() {
		return statMapping.values();
	}

	/**
	 * return the stat based on the requested type. There exists one stat for each {@link StatType}
	 */
	public Stat getStat(final StatType type) {
		return statMapping.get(type.toString());
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(statMapping);
	}

	@Override
	public String toString() {
		return Joiner.on(",").withKeyValueSeparator(":").join(statMapping);
	}
}
