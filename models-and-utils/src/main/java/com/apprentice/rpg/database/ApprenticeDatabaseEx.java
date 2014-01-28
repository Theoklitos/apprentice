package com.apprentice.rpg.database;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * Any errors/problems during database control
 * 
 * @author theoklitos
 * 
 */
public class ApprenticeDatabaseEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public ApprenticeDatabaseEx(final String message) {
		super(message);
	}
	
	public ApprenticeDatabaseEx(final Throwable e) {
		super(e);
	}

}
