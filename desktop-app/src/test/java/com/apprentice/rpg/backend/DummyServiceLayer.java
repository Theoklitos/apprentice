package com.apprentice.rpg.backend;

import com.apprentice.rpg.dao.NameAlreadyExistsEx;
import com.apprentice.rpg.dao.Vault;
import com.apprentice.rpg.events.ApprenticeEventBus;
import com.apprentice.rpg.events.PublishSubscribeEventBus;
import com.apprentice.rpg.gui.util.WindowUtils;
import com.apprentice.rpg.gui.windowState.GlobalWindowState;
import com.apprentice.rpg.gui.windowState.IGlobalWindowState;
import com.apprentice.rpg.model.Nameable;
import com.apprentice.rpg.parsing.exportImport.DatabaseImporterExporter.ItemType;

/**
 * Used for testing and quick initialization, this {@link IServiceLayer} does nothing but log
 * 
 * @author theoklitos
 * 
 */
public class DummyServiceLayer implements IServiceLayer {

	@Override
	public void createOrUpdate(final Nameable item) throws NameAlreadyExistsEx {
		// TODO Auto-generated method stub
	}

	@Override
	public void createOrUpdateUniqueName(final Nameable item) throws NameAlreadyExistsEx {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean deleteNameable(final String name, final ItemType itemType) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ApprenticeEventBus getEventBus() {
		return new PublishSubscribeEventBus();
	}

	@Override
	public IGlobalWindowState getGlobalWindowState() {
		return new GlobalWindowState(new WindowUtils());
	}

	@Override
	public String getLastUpdateTime(final String typeName, final ItemType type) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vault getVault() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void renameNamebale(final String oldName, final String newName, final ItemType itemType)
			throws NameAlreadyExistsEx {
		// TODO Auto-generated method stub
	}

}
