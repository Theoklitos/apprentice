package com.apprentice.rpg.gui;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When an internal frame that should have only 1 instance is attempted to be opened again
 *  
 * @author theoklitos
 *
 */
public class FrameAlreadyOpenEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public FrameAlreadyOpenEx(final String reason) {
		super(reason);
	}

}
