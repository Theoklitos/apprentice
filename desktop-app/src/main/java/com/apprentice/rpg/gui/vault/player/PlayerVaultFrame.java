package com.apprentice.rpg.gui.vault.player;

import javax.swing.JPanel;

import com.apprentice.rpg.gui.IWindowManager;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * List of {@link PlayerCharacter}s along with various controls
 * 
 * @author theoklitos
 * 
 */
public final class PlayerVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public PlayerVaultFrame(final IGlobalWindowState globalWindowState, final IVaultFrameControl vaultFrameControl,
			final IWindowManager windowManager) {
		super(globalWindowState, windowManager, ItemType.PLAYER_CHARACTER, vaultFrameControl, false,
				NewPlayerCharacterFrame.PLAYER_DESCRIPTION_PANEL_TITLE, false);
	}

	@Override
	public void initComponents(final JPanel vaultTablePanel) {
		getContentPane().add(vaultTablePanel);
	}

	@Override
	public void refreshFromModelSubclass() {
		// nothing
	}

}
