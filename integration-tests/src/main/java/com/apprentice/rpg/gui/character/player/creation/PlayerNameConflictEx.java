package com.apprentice.rpg.gui.character.player.creation;

import com.apprentice.rpg.ApprenticeEx;

/**
 * A player of the same name was to be created. Names must be unique
 * 
 * @author theoklitos
 * 
 */
public class PlayerNameConflictEx extends ApprenticeEx {

	private static final long serialVersionUID = 1L;

	public PlayerNameConflictEx(final String reason) {
		super(reason);		
	}

}
