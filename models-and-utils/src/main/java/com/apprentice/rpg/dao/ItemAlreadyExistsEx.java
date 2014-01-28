package com.apprentice.rpg.dao;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;

/**
 * If a unique item is attempted to be created but already exists
 * 
 * @author theoklitos
 *
 */
public final class ItemAlreadyExistsEx extends ApprenticeDatabaseEx {

	private static final long serialVersionUID = 1L;

	public ItemAlreadyExistsEx(final String message) {
		super(message);
	}

}
