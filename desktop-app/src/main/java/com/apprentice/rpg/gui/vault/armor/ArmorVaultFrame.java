package com.apprentice.rpg.gui.vault.armor;

import javax.swing.JPanel;

import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Used to create armors and armor pieces
 * 
 * @author theoklitos
 * 
 */
public class ArmorVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public ArmorVaultFrame(final IGlobalWindowState globalWindowState, final IVaultFrameControl vaultFrameControl,
			final IWindowManager windowManager) {
		super(globalWindowState, windowManager, ItemType.ARMOR_PIECE, vaultFrameControl, true);
	}

	@Override
	public void initComponents(final JPanel vaultTablePanel) {
		getContentPane().add(vaultTablePanel);
	}

	@Override
	public void refreshFromModelSubclass() {
		// TODO Auto-generated method stub		
	}

}
