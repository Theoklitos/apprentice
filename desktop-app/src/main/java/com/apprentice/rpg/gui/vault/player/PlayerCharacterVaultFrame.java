package com.apprentice.rpg.gui.vault.player;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.model.PlayerCharacter;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * List of {@link PlayerCharacter}s along with various controls
 * 
 * @author theoklitos
 * 
 */
public final class PlayerCharacterVaultFrame extends AbstractVaultFrame {

	private static final long serialVersionUID = 1L;

	public PlayerCharacterVaultFrame(final IServiceLayer control) {
		super(control, ItemType.PLAYER_CHARACTER, NewPlayerCharacterFrame.PLAYER_DESCRIPTION_PANEL_TITLE, false, false);
		getEditButton().setText("Summon");
	}

}
