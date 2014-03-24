package com.apprentice.rpg.gui.vault.weapon;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * List of {@link IWeaponPrototype}s along with various controls
 * 
 * @author theoklitos
 * 
 */
public final class WeaponVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public WeaponVaultFrame(final IServiceLayer control) {
		super(control, ItemType.WEAPON, true, true);
	}

}
