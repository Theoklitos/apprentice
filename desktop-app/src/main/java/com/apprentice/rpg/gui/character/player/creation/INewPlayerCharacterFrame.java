package com.apprentice.rpg.gui.character.player.creation;

import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.model.body.IType;

/**
 * Window that is used to create new PCs
 * 
 * @author theoklitos
 * 
 */
public interface INewPlayerCharacterFrame extends ControllableView {

	/**
	 * Reads all the available {@link IType} from the control and puts them as options in the appropriate combobox
	 */
	void refreshTypeDropdown();
 
}
