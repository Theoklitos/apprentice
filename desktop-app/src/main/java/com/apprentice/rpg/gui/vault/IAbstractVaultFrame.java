package com.apprentice.rpg.gui.vault;

import com.apprentice.rpg.gui.ControllableView;

/**
 * Shows a table of nameable items that exist in the database, along with some controls
 * 
 * @author theoklitos
 * 
 */
public interface IAbstractVaultFrame extends ControllableView {

	/**
	 * refreshes the nameable table from the model
	 */
	void refreshFromModel();

}
