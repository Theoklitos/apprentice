package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * whether an armor is assigned (to) something that doesn't fit or make sense
 */
public class ArmorDoesNotFitEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public ArmorDoesNotFitEx(final String reason) {
		super(reason);
	}

	public ArmorDoesNotFitEx(final Throwable e) {
		super(e);
	}

}
