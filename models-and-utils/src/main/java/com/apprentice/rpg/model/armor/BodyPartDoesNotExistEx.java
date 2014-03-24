package com.apprentice.rpg.model.armor;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When a bodypart does not exist in an armor's type
 * 
 * @author theoklitos
 * 
 */
public final class BodyPartDoesNotExistEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public BodyPartDoesNotExistEx(final String reason) {
		super(reason);
	}

}
