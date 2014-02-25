package com.apprentice.rpg.gui.vault.type;

import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.model.body.BodyPart;

/**
 * A kind of {@link AbstractVaultFrame} which is meant to update {@link Types} and {@link BodyPart}s
 * 
 * @author theoklitos
 * 
 */
public interface ITypeAndBodyPartFrame extends ControllableView {

	/**
	 * calls the backend (control) and updates the data in the tables
	 */
	void refreshFromModel();

	/**
	 * removes this body part from the temporary types of the view, if exists
	 */
	void removeBodyPartsInView(BodyPart deletedBodyPart);

}
