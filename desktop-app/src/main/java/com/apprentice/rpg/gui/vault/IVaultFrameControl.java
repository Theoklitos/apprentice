package com.apprentice.rpg.gui.vault;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.gui.ControlWithVault;
import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Control for the {@link AbstractVaultFrame}
 * 
 * @author theoklitos
 * 
 */
public interface IVaultFrameControl extends ControlForView<AbstractVaultFrame>, ControlWithVault {

	/**
	 * deletes the nameable that has the given name
	 */
	void deleteNameable(String name, ItemType itemType);

	/**
	 * Finds the {@link Nameable} that has the oldName, and changes its name to the newName
	 */
	void renameNamebale(String oldName, String newName, final ItemType itemType) throws NameAlreadyExistsEx;
}
