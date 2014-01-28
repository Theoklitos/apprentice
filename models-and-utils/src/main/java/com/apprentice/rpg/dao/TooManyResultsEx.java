package com.apprentice.rpg.dao;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;

/**
 * When a single object is queried and only 1 is expected to be found (ie a player with a specific name) but
 * many results are returned
 * 
 * @author theoklitos
 * 
 */
public final class TooManyResultsEx extends ApprenticeDatabaseEx {

	private static final long serialVersionUID = 1L;

	public TooManyResultsEx(final String reason) {
		super(reason);
	}

}
