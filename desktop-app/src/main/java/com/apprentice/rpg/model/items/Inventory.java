package com.apprentice.rpg.model.items;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * Contains everything the character is carryin, along with information about his capacity etc
 * 
 * @author theoklitos
 * 
 */
public final class Inventory {

	private final List<Weapon> weapons;
	private final List<Armor> armors;
	private final List<WeightableItem> miscItems;

	public Inventory() {
		this.weapons = Lists.newArrayList();
		this.armors = Lists.newArrayList();
		this.miscItems = Lists.newArrayList();
	}
}
