package com.apprentice.rpg.gui.vault;

import org.apache.log4j.Logger;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.AbstractControlForView;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;
import com.google.inject.Inject;

/**
 * Control for the {@link AbstractVaultFrame}
 * 
 * @author theoklitos
 * 
 */
public final class VaultFrameControl extends AbstractControlForView implements IVaultFrameControl {

	private static Logger LOG = Logger.getLogger(VaultFrameControl.class);

	private AbstractVaultFrame view;

	@Inject
	public VaultFrameControl(final Vault vault, final ApprenticeEventBus eventBus) {
		super(vault, eventBus);
	}

	@Override
	public void deleteNameable(final String name, final ItemType itemType) {
		final Nameable itemAtHand = getVault().getUniqueNamedResult(name, itemType.type);
		getVault().delete(itemAtHand);
		getEventBus().postDeleteEvent(itemAtHand);
		LOG.info("Deleted " + name);
		view.refreshFromModel();
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
		} finally {
			view.refreshFromModel();
		}
	}

	@Override
	public void setView(final AbstractVaultFrame view) {
		this.view = view;
	}

}
