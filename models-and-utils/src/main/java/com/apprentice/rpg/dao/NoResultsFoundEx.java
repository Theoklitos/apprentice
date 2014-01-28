package com.apprentice.rpg.dao;

import com.apprentice.rpg.database.ApprenticeDatabaseEx;

/**
 * When a query failed to bring results
 * 
 * @author theoklitos
 *
 */
public class NoResultsFoundEx extends ApprenticeDatabaseEx {

	private static final long serialVersionUID = 1L;

	public NoResultsFoundEx(final String reason) {
		super(reason);
	}
	
}
