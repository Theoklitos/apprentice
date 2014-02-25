package com.apprentice.rpg.gui.character.player.creation;

import java.util.Collection;

import com.apprentice.rpg.dao.ItemAlreadyExistsEx;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ControlForDescriptionInView;
import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.model.body.IType;

/**
 * Control for the {@link NewPlayerCharacterFrame}
 * 
 * @author theoklitos
 * 
 */
public interface INewPlayerCharacterFrameControl extends ControlForView<INewPlayerCharacterFrame>,
		ControlForDescriptionInView {

	/**
	 * Persists the character in the backend
	 */
	void createCharacter(PlayerCharacter character) throws NameAlreadyExistsEx, ItemAlreadyExistsEx;

	/**
	 * returns all the ITypes that exist in the repository
	 */
	Collection<IType> getAllTypes();

}
