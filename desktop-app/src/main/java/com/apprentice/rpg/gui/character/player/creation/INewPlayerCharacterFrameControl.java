package com.apprentice.rpg.gui.character.player.creation;

import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.model.PlayerCharacter;

/**
 * Control for the {@link NewPlayerCharacterFrame}
 * 
 * @author theoklitos
 * 
 */
public interface INewPlayerCharacterFrameControl extends ControlForView {
	
	/**
	 * Persists the character in the backend
	 */
	void createCharacter(PlayerCharacter character);
	
}
