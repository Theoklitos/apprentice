package com.apprentice.rpg.gui.vault;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.google.inject.Inject;

/**
 * Control for the {@link AbstractVaultFrame}
 * 
 * @author theoklitos
 * 
 */
public final class VaultFrameControl implements IVaultFrameControl {

	@SuppressWarnings("unused")
	private AbstractVaultFrame view;
	private final Vault vault;
	private final ApprenticeEventBus eventBus;

	@Inject
	public VaultFrameControl(final Vault vault, final ApprenticeEventBus eventBus) {
		this.vault = vault;
		this.eventBus = eventBus;
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return eventBus;
	}

	@Override
	public Vault getVault() {
		return vault;
	}

	@Override
	public void setView(final AbstractVaultFrame view) {
		this.view = view;
	}

}
