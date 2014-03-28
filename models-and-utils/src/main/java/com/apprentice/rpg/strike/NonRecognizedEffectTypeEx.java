package com.apprentice.rpg.strike;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When an {@link Effect} is used that is not managed by the rules
 * 
 */
public class NonRecognizedEffectTypeEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public NonRecognizedEffectTypeEx() {
		super("");
	}

	public NonRecognizedEffectTypeEx(final String reason) {
		super(reason);
	}

	public NonRecognizedEffectTypeEx(final Throwable e) {
		super(e);
	}

}
