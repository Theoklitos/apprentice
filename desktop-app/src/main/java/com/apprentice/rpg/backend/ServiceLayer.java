package com.apprentice.rpg.backend;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.NoResultsFoundEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.dao.time.ModificationTimeVault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.google.inject.Inject;

/**
 * Encapsulates access to the backend. Has {@link Vault} and {@link ApprenticeEventBus} references and related
 * methods.
 * 
 * @author theoklitos
 * 
 */
public final class ServiceLayer implements IServiceLayer {

	private static Logger LOG = Logger.getLogger(ServiceLayer.class);

	private final Vault vault;
	private final ApprenticeEventBus eventBus;
	private final IGlobalWindowState globalWindowState;

	@Inject
	public ServiceLayer(final Vault vault, final ApprenticeEventBus eventBus, final IGlobalWindowState globalWindowState) {
		this.vault = vault;
		this.eventBus = eventBus;
		this.globalWindowState = globalWindowState;
	}

	@Override
	public void createOrUpdate(final Nameable item) throws NameAlreadyExistsEx {
		final boolean isNew = !getVault().exists(item);
		final ItemType type = ItemType.getForClass(item.getClass());
		getVault().update(item);
		if (isNew) {
			LOG.info("New " + type.toString() + " named " + item.getName() + " created");
			getEventBus().postCreationEvent(item);
		} else {
			LOG.info("Updated " + type.toString() + " " + item.getName());
			getEventBus().postUpdateEvent(item);
		}
	}

	@Override
	public void createOrUpdateUniqueName(final Nameable item) throws NameAlreadyExistsEx {
		if (vault.doesNameExist(item.getName(), item.getClass())) {
			throw new NameAlreadyExistsEx();
		} else {
			createOrUpdate(item);
		}
	}

	@Override
	public boolean deleteNameable(final String name, final ItemType itemType) {
		final Nameable itemAtHand = getVault().getUniqueNamedResult(name, itemType.type);
		final boolean result = getVault().delete(itemAtHand);
		if (result) {
			getEventBus().postDeleteEvent(itemAtHand);
			LOG.info("Deleted " + name);
		}
		return result;
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return eventBus;
	}

	@Override
	public IGlobalWindowState getGlobalWindowState() {
		return globalWindowState;
	}

	@Override
	public String getLastUpdateTime(final String typeName, final ItemType type) {
		try {
			return vault.getPrettyUpdateTime(vault.getUniqueNamedResult(typeName, type.type));
		} catch (final NoResultsFoundEx e) {
			return ModificationTimeVault.NO_TIMING_DESCRIPTION;
		}
	}

	@Override
	public Vault getVault() {
		return vault;
	}

	@Override
	public void renameNamebale(final String oldName, final String newName, final ItemType itemType)
			throws NameAlreadyExistsEx {
		final Nameable itemAtHand = getVault().getUniqueNamedResult(oldName, itemType.type);
		itemAtHand.setName(newName);
		try {
			getVault().update(itemAtHand);
			LOG.info("Renamed " + oldName + " to " + newName);
			getEventBus().postUpdateEvent(itemAtHand);
		} catch (final NameAlreadyExistsEx e) {
			itemAtHand.setName(oldName);
			throw new NameAlreadyExistsEx();
		}
	}
}
