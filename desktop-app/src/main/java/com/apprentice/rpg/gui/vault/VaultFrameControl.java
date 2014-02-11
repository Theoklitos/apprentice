package com.apprentice.rpg.gui.vault;

import com.apprentice.rpg.dao.Vault;
import com.google.inject.Inject;

/**
 * Control for the {@link GenericVaultFrame}
 * 
 * @author theoklitos
 * 
 */
public final class VaultFrameControl implements IVaultFrameControl {

	private GenericVaultFrame view;
	private final Vault vault;

	@Inject
	public VaultFrameControl(final Vault vault) {
		this.vault = vault;
	}

	@Override
	public void setView(final GenericVaultFrame view) {
		this.view = view;
	}

}
