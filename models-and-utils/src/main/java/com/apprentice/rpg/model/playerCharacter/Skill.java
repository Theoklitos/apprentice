package com.apprentice.rpg.model.playerCharacter;

import org.apache.commons.lang3.StringUtils;

import com.apprentice.rpg.util.ApprenticeStringUtils;
import com.google.common.base.Objects;

/**
 * Represents a skill of the player character
 * 
 * @author theoklitos
 * 
 */
public class Skill {

	private String name;
	private Stat stat;
	private int rank;

	public Skill(final String name, final Stat stat, final int rank) {
		setName(name);
		this.stat = stat;
		this.rank = rank;
	}

	@Override
	public boolean equals(final Object other) {
		if (other instanceof Skill) {
			final Skill otherSkill = (Skill) other;
			return Objects.equal(name, otherSkill.name) && rank == otherSkill.rank
				&& Objects.equal(stat, otherSkill.stat);
		} else {
			return false;
		}
	}

	/**
	 * returns the name of this skill
	 */
	public String getName() {
		return name;
	}

	/**
	 * how many ranks does the player have in this skill
	 */
	public int getRank() {
		return rank;
	}

	/**
	 * What stat is bound to this skill (and thus modifies it)
	 */
	public Stat getStat() {
		return stat;
	}

	/**
	 * Returns the bonus + stat modifier
	 */
	public int getTotalBonus() {
		return rank + stat.getBonus();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(name, stat, rank);
	}

	/**
	 * sets this skill's name
	 */
	public void setName(final String name) {
		this.name = StringUtils.capitalize(name.toLowerCase().trim());
	}

	/**
	 * how many + points does the character have with this skill
	 */
	public void setRank(final int rank) {
		this.rank = rank;
	}

	/**
	 * Sets which stat is bound to this skill and thus will modify it
	 */
	public void setStat(final Stat stat) {
		this.stat = stat;
	}

	@Override
	public String toString() {
		return getName() + " " + rank + ApprenticeStringUtils.getNumberWithOperator(stat.getBonus()) + "("
			+ stat.getStatType().toString().substring(0, 3) + "): " + getTotalBonus();
	}
}
