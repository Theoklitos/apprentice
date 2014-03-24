package com.apprentice.rpg.gui.vault.type;

import com.apprentice.rpg.gui.ControlForView;

/**
 * Controls the {@link exportItems}
 * 
 * @author theoklitos
 * 
 */
public interface ITypeAndBodyPartFrameControl extends ControlForView<ITypeAndBodyPartFrame> {

	/**
	 * Returns the {@link TypeAndBodyPartFrameState} which temporarily stores data from the view
	 */
	TypeAndBodyPartFrameState getState();

}
