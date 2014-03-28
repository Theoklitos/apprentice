package com.apprentice.rpg.gui;

import org.apache.log4j.Logger;

import com.apprentice.rpg.backend.IServiceLayer;
import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.playerCharacter.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Superclass for all controls in the gui
 * 
 * @param T
 *            the frame that this control is supposed to be controlling
 * @author theoklitos
 * 
 */
public abstract class AbstractControlForView<T extends ControllableView> implements ControlForView<T> {

	@SuppressWarnings("unused")
	private static Logger LOG = Logger.getLogger(AbstractControlForView.class);

	private T view;
	private final IServiceLayer serviceLayer;

	public AbstractControlForView(final IServiceLayer serviceLayer) {
		this.serviceLayer = serviceLayer;
	}

	@Override
	public void createOrUpdate(final Nameable item) {
		serviceLayer.createOrUpdate(item);
	}

	@Override
	public void createOrUpdateUniqueName(final Nameable item) throws NameAlreadyExistsEx {
		serviceLayer.createOrUpdateUniqueName(item);
	}

	@Override
	public boolean deleteNameable(final String name, final ItemType itemType) {
		return serviceLayer.deleteNameable(name, itemType);
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return serviceLayer.getEventBus();
	}

	@Override
	public IGlobalWindowState getGlobalWindowState() {
		return serviceLayer.getGlobalWindowState();
	}

	@Override
	public String getLastUpdateTime(final String typeName, final ItemType type) {
		return serviceLayer.getLastUpdateTime(typeName, type);
	}

	@Override
	public Vault getVault() {
		return serviceLayer.getVault();
	}

	@Override
	public T getView() {
		return view;
	}

	@Override
	public void renameNamebale(final String oldName, final String newName, final ItemType itemType)
			throws NameAlreadyExistsEx {
		serviceLayer.renameNamebale(oldName, newName, itemType);
	}

	@Override
	public void setView(final T view) {
		this.view = view;
	}

}
