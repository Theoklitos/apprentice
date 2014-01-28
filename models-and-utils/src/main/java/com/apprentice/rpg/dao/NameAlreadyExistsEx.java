package com.apprentice.rpg.dao;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;
import com.apprentice.rpg.model.Nameable;

/**
 * thrown when a {@link Nameable} has an already used name 
 * @author theoklitos
 *
 */
public class NameAlreadyExistsEx extends ApprenticeDatabaseEx {

	private static final long serialVersionUID = 1L;

	public NameAlreadyExistsEx(final String message) {
		super(message);
	}

}
