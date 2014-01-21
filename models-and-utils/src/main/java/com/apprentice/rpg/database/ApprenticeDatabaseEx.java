package com.apprentice.rpg.dao;

import com.apprentice.rpg.ApprenticeEx;

/**
 * When the database is corrupt and/or cannot be loaded
 * 
 * @author theoklitos
 * 
 */
public final class CouldNotLoadDatabaseEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public CouldNotLoadDatabaseEx(final Throwable e) {
		super(e);
	}

}
