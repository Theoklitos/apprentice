package com.apprentice.rpg.strike;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * Exceptions related to the strikes and their application
 * 
 * @author theoklitos
 * 
 */
public final class StrikeEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public StrikeEx(final String reason) {
		super(reason);
	}

	public StrikeEx(final Throwable e) {
		super(e);
	}

}
