package com.apprentice.rpg.gui.character.player.creation;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.gui.ControlForView;
import com.apprentice.rpg.gui.ControllableView;
import com.apprentice.rpg.model.PlayerCharacter;
import com.google.inject.Inject;

/**
 * Control for the {@link NewPlayerCharacterFrame}
 * 
 * @author theoklitos
 * 
 */
public final class NewPlayerCharacterFrameControl implements INewPlayerCharacterFrameControl, ControlForView {

	private NewPlayerCharacterFrame view;
	private final Vault dao;

	@Inject
	public NewPlayerCharacterFrameControl(final Vault dao) {
		this.dao = dao;
	}

	@Override
	public void createCharacter(final PlayerCharacter character) {
		// vault.saveCharacter(character);/
	}

	@Override
	public void setView(final ControllableView view) {
		this.view = (NewPlayerCharacterFrame) view;
	}
}
