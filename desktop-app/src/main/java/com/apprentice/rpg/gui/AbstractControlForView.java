package com.apprentice.rpg.gui;

import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;

/**
 * Superclass for all controls in the gui
 * @author theoklitos
 *
 */
public abstract class AbstractControlForView implements ControlForDescriptionInView {
	
	private final Vault vault;
	private final ApprenticeEventBus eventBus;

	public AbstractControlForView(final Vault vault, final ApprenticeEventBus eventBus) {
		this.vault = vault;
		this.eventBus = eventBus;
		eventBus.register(this);
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return eventBus;
	}

	@Override
	public Vault getVault() {
		return vault;
	}

}
