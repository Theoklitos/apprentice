package com.apprentice.rpg.dao;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;

/**
 * A query was made for an item that should exist, but didnt
 * 
 * @author theoklitos
 * 
 */
public final class ItemNotFoundEx extends ApprenticeDatabaseEx {

	private static final long serialVersionUID = 1L;

	public ItemNotFoundEx() {
		super("");
	}

	public ItemNotFoundEx(final String message) {
		super(message);
	}

}
