package com.apprentice.rpg.gui;

import com.apprentice.rpg.model.ApprenticeEx;

/**
 * When a new character could not be created
 * 
 * @author theoklitos
 * 
 */
public class GuiItemCreationEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public GuiItemCreationEx(final String reason) {
		super(reason);
	}

	public GuiItemCreationEx(final Throwable e) {
		super(e);
	}

}
