package com.apprentice.rpg.gui.vault.armorPiece;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Used to create armors and armor pieces
 * 
 * @author theoklitos
 * 
 */
public class ArmorPieceVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public ArmorPieceVaultFrame(final IServiceLayer control) {
		super(control, ItemType.ARMOR_PIECE, true, true);
	}

}
