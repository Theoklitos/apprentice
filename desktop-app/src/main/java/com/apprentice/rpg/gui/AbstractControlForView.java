package com.apprentice.rpg.gui;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Superclass for all controls in the gui
 * @author theoklitos
 *
 */
public abstract class AbstractControlForView implements ControlWithVault {
	
	private static Logger LOG = Logger.getLogger(AbstractControlForView.class);
	
	private final Vault vault;
	private final ApprenticeEventBus eventBus;

	public AbstractControlForView(final Vault vault, final ApprenticeEventBus eventBus) {
		this.vault = vault;
		this.eventBus = eventBus;
		eventBus.register(this);
	}

	@Override
	public void createOrUpdate(final Nameable nameable, final ItemType type) throws NameAlreadyExistsEx {
		final boolean isNew = !getVault().exists(nameable);
		getVault().update(nameable);
		if (isNew) {
			LOG.info("New " + type.toString() + " named " + nameable.getName() + " created");
			getEventBus().postCreationEvent(nameable);
		} else {
			LOG.info("Updated " + type.toString() + " " + nameable.getName());
			getEventBus().postUpdateEvent(nameable);
		}
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
