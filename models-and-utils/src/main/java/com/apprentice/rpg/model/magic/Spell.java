package com.apprentice.rpg.model.magic;

import com.apprentice.rpg.util.Checker;

/**
 * A vancian-type, one-time, magical spell that a player can cast
 * 
 * @author theoklitos
 * 
 */
public final class Spell {

	protected static final String NO_DESCRIPTION_VALUE = "No description provided";
	private final String name;
	private int cost;
	private String description;

	/**
	 * when we don't care about the dscription
	 */
	public Spell(final String name, final int cost) {
		this(name,cost,"");
	}
	
	public Spell(final String name, final int cost, final String description) {
		Checker.checkNonNull("Null or emtpy value while initializing Spell", true, name);
		this.name = name;
		setCost(cost);
		setDescription(description);
	}

	/**
	 * How much does this spell cost, in spell points?
	 */
	public int getCost() {
		return cost;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

	/**
	 * can never go below zero
	 */
	public void setCost(final int cost) {
		if (cost < 0) {
			this.cost = 0;
		} else {
			this.cost = cost;
		}
	}

	public void setDescription(final String description) {
		if (org.apache.commons.lang3.StringUtils.isEmpty(description)) {
			this.description = NO_DESCRIPTION_VALUE;
		} else {
			this.description = description;
		}
	}
}
