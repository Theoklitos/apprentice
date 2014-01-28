package com.apprentice.rpg.parsing;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When the {@link JsonParser} has a problem
 * 
 * @author theoklitos
 * 
 */
public class ParsingEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public ParsingEx(final String reason) {
		super(reason);
	}
	
	public ParsingEx(final Throwable e) {
		super(e);
	}

}
