package com.apprentice.rpg.model.magic;

/**
 * A vancian-type, one-time, magical spell that a player can cast
 * 
 * @author theoklitos
 * 
 */
public final class Spell {

	private final String name;
	private final int cost;
	private final String description;

	public Spell(final String name, final int cost, final String description) {
		this.name = name;
		this.cost = cost;
		this.description = description;
	}
}
