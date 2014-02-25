package com.apprentice.rpg.gui.vault.player;

import com.apprentice.rpg.gui.character.player.creation.NewPlayerCharacterFrame;
import com.apprentice.rpg.gui.vault.AbstractVaultFrame;
import com.apprentice.rpg.gui.vault.IVaultFrameControl;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.IPlayerCharacter;

/**
 * Shows a list of {@link IPlayerCharacter}
 * 
 * @author theoklitos
 * 
 */
public final class PlayerVaultFrame extends AbstractVaultFrame implements IPlayerVaultFrame {

	private static final long serialVersionUID = 1L;

	public PlayerVaultFrame(final IGlobalWindowState globalWindowState, final IVaultFrameControl vaultFrameControl) {
		super(globalWindowState, "Player Character Vault", IPlayerCharacter.class, vaultFrameControl,
				NewPlayerCharacterFrame.PLAYER_DESCRIPTION_PANEL_TITLE, false);
	}

}
