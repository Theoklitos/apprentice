package com.apprentice.rpg.model.body;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When there is an error in the numbering/ordering of {@link BodyPart}s to interger ranges
 */
public final class BodyPartMappingEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public BodyPartMappingEx(final String reason) {
		super(reason);
	}

}
